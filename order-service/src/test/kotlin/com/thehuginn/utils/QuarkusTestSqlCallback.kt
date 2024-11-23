package com.thehuginn.utils

import com.influxdb.client.InfluxDBClient
import io.quarkus.arc.Arc
import io.quarkus.test.junit.callback.QuarkusTestAfterEachCallback
import io.quarkus.test.junit.callback.QuarkusTestBeforeEachCallback
import io.quarkus.test.junit.callback.QuarkusTestMethodContext
import java.io.FileNotFoundException
import java.nio.file.Path
import java.time.OffsetDateTime
import javax.sql.DataSource
import kotlin.io.path.readText

class QuarkusTestSqlCallback: QuarkusTestBeforeEachCallback, QuarkusTestAfterEachCallback {
    override fun beforeEach(context: QuarkusTestMethodContext?) {
        val testInstance = context?.testInstance ?: return
        getSqlAnnotation(testInstance, context)?.let { executeScripts(it.scripts) }
    }

    override fun afterEach(context: QuarkusTestMethodContext?) {
        executeScripts(arrayOf("sql/clean.sql"))
//        deleteInfluxDb()
    }

    private fun getSqlAnnotation(testInstance: Any, context: QuarkusTestMethodContext): Sql? {
        val methodAnnotation = context.testMethod.getAnnotation(Sql::class.java)
        val classAnnotation = testInstance.javaClass.getAnnotation(Sql::class.java)
        return methodAnnotation ?: classAnnotation
    }

    private fun deleteInfluxDb() {
        val influxDbClient = Arc.container().instance(InfluxDBClient::class.java).get()
        influxDbClient.deleteApi.delete(
            OffsetDateTime.now().minusDays(1),
            OffsetDateTime.now(),
            "",
            "quarkus",
            "quarkus"
        )
    }

    private fun executeScripts(scripts: Array<String>) {
        val dataSource = Arc.container().instance(DataSource::class.java).get()
        dataSource.connection.use { connection ->
            val statement = connection.createStatement()
            for (script in scripts) {
                val path: Path = Path.of(
                QuarkusTestSqlCallback::class.java.classLoader.getResource(script)?.toURI()
                    ?: throw FileNotFoundException("SQL script not found in resources: $script"))
                val sql = path.readText()
                statement.execute(sql)
            }
        }
    }
}