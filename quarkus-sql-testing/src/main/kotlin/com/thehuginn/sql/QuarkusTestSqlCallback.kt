package com.thehuginn.sql

import io.quarkus.arc.Arc
import io.quarkus.test.junit.callback.QuarkusTestAfterEachCallback
import io.quarkus.test.junit.callback.QuarkusTestBeforeEachCallback
import io.quarkus.test.junit.callback.QuarkusTestMethodContext
import java.io.FileNotFoundException
import java.lang.Thread
import java.nio.file.Path
import javax.sql.DataSource
import kotlin.io.path.readText

class QuarkusTestSqlCallback: QuarkusTestBeforeEachCallback, QuarkusTestAfterEachCallback {
    override fun beforeEach(context: QuarkusTestMethodContext?) {
        val testInstance = context?.testInstance ?: return
        getSqlAnnotation(testInstance, context)?.let { executeScripts(it.scripts) }
    }

    override fun afterEach(context: QuarkusTestMethodContext?) {
        executeScripts(arrayOf("sql/clean.sql"))
    }

    private fun getSqlAnnotation(testInstance: Any, context: QuarkusTestMethodContext): Sql? {
        val methodAnnotation = context.testMethod.getAnnotation(Sql::class.java)
        val classAnnotation = testInstance.javaClass.getAnnotation(Sql::class.java)
        return methodAnnotation ?: classAnnotation
    }

    private fun executeScripts(scripts: Array<String>) {
        val dataSource = Arc.container().instance(DataSource::class.java).get()
        dataSource.connection.use { connection ->
            val statement = connection.createStatement()
            for (script in scripts) {
                val path: Path = Path.of(
                Thread.currentThread().contextClassLoader.getResource(script)?.toURI()
                    ?: throw FileNotFoundException("SQL script not found in resources: $script"))
                val sql = path.readText()
                statement.execute(sql)
            }
        }
    }
}