package lab34.botbehaviors

import io.ktor.server.plugins.*
import lab34.SERVERNAME
import lab34.lastCharExclude
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class SQLBehavior : BotBehavior {
    private val connection: Connection

    init {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver")
        val connectionUrl =
            "jdbc:sqlserver://$SERVERNAME:1433;encrypt=true;database=CitiesName;trustServerCertificate=true;"
        connection = DriverManager.getConnection(connectionUrl, "bot", "citiesbot")
    }

    override fun chooseCity(text: String): String? {
        if (!connection.createStatement().executeQuery("""
            select CityName
            from CitiesName_Schema.CityNameBackUp
            where CityName = '$text'
        """.trimIndent()).next()) throw NotFoundException("No such city in database")

        addCity(text)

        val results = connection.createStatement().executeQuery("""
            select top 1 CityName
            from CitiesName_Schema.City
            where CityName like '${text.lastCharExclude().uppercaseChar()}%'
            order by newid()
        """.trimIndent()
        )

        return if (results.next()) {
            results.getString("CityName")
        } else {
            null
        }
    }

    override fun addCity(text: String) {
        try {
            connection.createStatement().executeQuery("""
            delete from CitiesName_Schema.City
            where CityName = '$text'
        """.trimIndent()
            )
        } catch (_: SQLException) {}
    }

    override fun clearNamedCities() {
        try {
            connection.createStatement().executeQuery(
                """
            truncate table CitiesName_Schema.City
            insert into CitiesName_Schema.City
            select *
            from CitiesName_Schema.CityNameBackUp
        """.trimIndent()
            )
        } catch (_: SQLException) {
            println("Restart")
        }
    }

    override fun checkCity(text: String): Boolean {
        val results = connection.createStatement().executeQuery(
            """
            select CityName
            from CitiesName_Schema.City
            where CityName = '$text'
        """.trimIndent()
        )
        return results.next()
    }
}
