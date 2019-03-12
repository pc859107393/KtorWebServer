package model

import org.jetbrains.exposed.sql.Table

/**
 * 用户表
 */
object User : Table() {
    val id = Widgets.integer("id").primaryKey().autoIncrement()
    val name = Widgets.varchar("name", 50)
    val loginName = Widgets.varchar("login_name", 25)
    val password = Widgets.varchar("password", 256)
    val duty = Widgets.varchar("duty", 50)
    val createDate = Widgets.integer("create_date")
    val used = Widgets.bool("used")
}

/**
 * 用户包装
 */
data class UserDTO(
        val id: Int,
        val name: String,
        val loginName: String,
        val password: String,
        val duty: String,
        val createDate: Int,
        val used: Boolean
)

/**
 * 创建用户
 */
data class NewUser(
        val id: Int?,
        val name: String,
        val loginName: String,
        val password: String,
        val duty: String,
        val createDate: Int,
        val used: Boolean
)