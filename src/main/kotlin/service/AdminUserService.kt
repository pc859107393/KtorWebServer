package service

import database.DatabaseFactory.Companion.dbQuery
import model.AdminUser
import model.AdminUserDTO
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import utils.CipherUtil

class AdminUserService {
//    val adminUserCache = Cache.adminUserCache

    suspend fun getTenAdmins(): List<AdminUserDTO> = dbQuery {
        AdminUser.selectAll().limit(10).map { toAdminUser(it) }
    }

    suspend fun getAdminsByPage(pageNum: Int, pageSize: Int = 20): List<AdminUserDTO> = dbQuery {
        AdminUser.selectAll()
                .limit(pageSize, if (pageNum - 1 > 0) pageSize * (pageNum - 1) else 0)
                .orderBy(AdminUser.id, false)
                .map { toAdminUser(it) }
    }

    /**
     * 登陆成功后返回用户token
     */
    suspend fun login(user: UserLogin): String {
        val query = dbQuery {
            AdminUser.select { AdminUser.loginName eq user.userName }
                    .mapNotNull { toAdminUser(it) }
                    .singleOrNull()
        }

        query ?: throw Exception("用户不存在！")

        if (!query.used) throw Exception("用户禁止登陆！")

        var lowerCase = user.pwd.toLowerCase()
        if (lowerCase.length == 32) lowerCase = lowerCase.substring(8, 24)
        val sha256 = CipherUtil.sha256(lowerCase + query.createDate.toString())
        if (query.password != sha256)
            throw Exception("用户名和密码不匹配！")

        return CipherUtil.small32md5(query.toString() + System.currentTimeMillis())
    }

    private fun toAdminUser(row: ResultRow): AdminUserDTO =
            AdminUserDTO(
                    id = row[AdminUser.id],
                    name = row[AdminUser.name],
                    loginName = row[AdminUser.loginName],
                    password = row[AdminUser.password],
                    duty = row[AdminUser.duty],
                    createDate = row[AdminUser.createDate].millis,
                    used = row[AdminUser.used]
            )
}