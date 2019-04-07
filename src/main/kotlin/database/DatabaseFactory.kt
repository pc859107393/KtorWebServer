package database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

class DatabaseFactory {

    companion object {
        @Volatile
        private var instance: DatabaseFactory? = null

        @JvmStatic
        fun getInstance(jdbcStr: String, maximumPoolSize: Int, username: String, password: String, autoCommit: Boolean): DatabaseFactory? {
            instance ?: synchronized(DatabaseFactory::class.java) {
                instance ?: DatabaseFactory().also {
                    init(jdbcStr, maximumPoolSize, username, password, autoCommit)
                }
            }
            return instance
        }

        fun init(jdbcStr: String, maximumPoolSize: Int, username: String, password: String, autoCommit: Boolean) {
            // Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver")
            Database.connect(hikari(jdbcStr, maximumPoolSize, username, password, autoCommit))
            transaction {
                addLogger(StdOutSqlLogger)
//            create(Widgets)
//            Widgets.insert {
//                it[name] = "widget one"
//                it[quantity] = 27
//                it[dateUpdated] = System.currentTimeMillis()
//            }
//            Widgets.insert {
//                it[name] = "widget two"
//                it[quantity] = 14
//                it[dateUpdated] = System.currentTimeMillis()
//            }
            }
        }

        private fun hikari(jdbcStr: String, maximumPoolSize: Int, username: String, password: String, autoCommit: Boolean): HikariDataSource {
            val config = HikariConfig()
//        config.driverClassName = "com.mysql.jdbc.Driver"
            config.jdbcUrl = jdbcStr
            config.maximumPoolSize = maximumPoolSize
            config.username = username
            config.password = password
            config.isAutoCommit = autoCommit
//        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            config.validate()
            return HikariDataSource(config)
        }

        suspend fun <T> dbQuery(
                block: () -> T): T =
                withContext(Dispatchers.IO) {
                    transaction { block() }
                }
    }


}