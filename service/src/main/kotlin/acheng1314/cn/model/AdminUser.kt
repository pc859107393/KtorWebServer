package acheng1314.cn.model

import org.jetbrains.exposed.sql.Table
import java.io.Serializable

/**
 * 用户表
 */
object AdminUser : Table("sys_admin_user") {
    val id = integer("id").primaryKey().autoIncrement()
    val name = varchar("name", 50)
    val loginName = varchar("login_name", 25)
    val password = varchar("password", 256)
    val duty = varchar("duty", 4096)
    val createDate = date("create_date")
    val used = bool("used")
}

/**
 * 创建用户
 */
data class NewAdminUser(
        val id: Int?,
        val name: String,
        val loginName: String,
        val password: String,
        val duty: String,
        val createDate: Long,
        val used: Boolean
) : Serializable

/**
 * 后台登录实体
 */
data class LoginAdmin(
        val loginName: String,
        val password: String
) : Serializable