package service

import database.DatabaseFactory.Companion.dbQuery
import model.AdminUser
import model.AdminUserDTO
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll

class AdminUserService {

    suspend fun getTenAdmins(): List<AdminUserDTO> = dbQuery {
        AdminUser.selectAll().limit(10).map { toAdminUser(it) }
    }

    suspend fun getAdminsByPage(pageNum: Int, pageSize: Int = 20): List<AdminUserDTO> = dbQuery {
        AdminUser.selectAll()
                .limit(pageSize, if (pageNum - 1 > 0) pageSize * (pageNum - 1) else 0)
                .map { toAdminUser(it) }
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