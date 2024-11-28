package com.thehuginn.sql

import io.quarkus.arc.Arc
import io.quarkus.test.junit.callback.QuarkusTestAfterEachCallback
import io.quarkus.test.junit.callback.QuarkusTestBeforeEachCallback
import io.quarkus.test.junit.callback.QuarkusTestMethodContext
import io.vertx.mutiny.pgclient.PgPool
import java.io.FileNotFoundException
import java.nio.file.Path
import javax.sql.DataSource
import kotlin.io.path.readText

class QuarkusTestSqlCallback : QuarkusTestBeforeEachCallback, QuarkusTestAfterEachCallback {
    override fun beforeEach(context: QuarkusTestMethodContext?) {
        val testInstance = context?.testInstance ?: return
        getSqlAnnotation(testInstance, context)?.let {
            // FIXME make this extension and in static context check for at least some QuarkusTestWithSql
            testInstance.javaClass.getAnnotation(QuarkusTestWithSql::class.java)
                ?: throw IllegalStateException("Missing ${QuarkusTestWithSql::class.simpleName} annotation")
            executeScripts(it.scripts)
        }
    }

    override fun afterEach(context: QuarkusTestMethodContext?) {
        val testInstance = context?.testInstance ?: return
        testInstance.javaClass.getAnnotation(QuarkusTestWithSql::class.java) ?: return
        executeScripts(arrayOf("sql/clean.sql"))
    }

    private fun getSqlAnnotation(testInstance: Any, context: QuarkusTestMethodContext): Sql? {
        val methodAnnotation = context.testMethod.getAnnotation(Sql::class.java)
        val classAnnotation = testInstance.javaClass.getAnnotation(Sql::class.java)
        return methodAnnotation ?: classAnnotation
    }

    private fun executeScripts(scripts: Array<String>) {
        val sqlStatement = scripts.map { script ->
            val path: Path = Path.of(
                Thread.currentThread().contextClassLoader.getResource(script)?.toURI()
                    ?: throw FileNotFoundException("SQL script not found in resources: $script")
            )
            path.readText()
        }.joinToString(separator = "\n")

        val datasourceDetected = Arc.container().instance(DataSource::class.java).get() != null
        if (datasourceDetected) {
            executeWithDataSource(sqlStatement)
        } else {
            executeReactiveSqlClient(sqlStatement)
        }
    }

    private fun executeReactiveSqlClient(sqlStatement: String) {
        val sqlClient = Arc.container().instance(PgPool::class.java).get()
        sqlClient.query(sqlStatement).execute().await().indefinitely()
    }

    private fun executeWithDataSource(sqlStatement: String) {
        val dataSource = Arc.container().instance(DataSource::class.java).get()
        dataSource.connection.use { connection ->
            connection.createStatement().executeQuery(sqlStatement)
        }
    }
}