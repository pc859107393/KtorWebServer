package acheng1314.cn.service

import acheng1314.cn.cache.Cache
import acheng1314.cn.data.AdminMenuDTO
import acheng1314.cn.db.DbQuery.dbQuery
import acheng1314.cn.model.AdminMenu
import org.apache.commons.lang3.StringUtils
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll

/**
 * 管理员菜单
 */
class AdminMenuService {

    private val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)

    private val adminMenuCache = Cache.adminMenuIdCache


    /**
     * 刷新所有权限，并放入缓存中
     */
    suspend fun notifyMenu() = dbQuery {
        AdminMenu.selectAll().orderBy(AdminMenu.id).map {
            val toAdminMenu = toAdminMenu(it)
            adminMenuCache.put(toAdminMenu.id, toAdminMenu)
            return@map toAdminMenu
        }
    }

    private fun toAdminMenu(row: ResultRow) = AdminMenuDTO(
            id = row[AdminMenu.id].value,
            parentId = row[AdminMenu.parentId],
            sort = row[AdminMenu.sort],
            icon = if (StringUtils.isBlank(row[AdminMenu.icon])) "" else row[AdminMenu.icon]!!,
            name = row[AdminMenu.name],
            uri = if (StringUtils.isBlank(row[AdminMenu.uri])) "" else row[AdminMenu.uri]!!,
            createDate = row[AdminMenu.createDate].millis,
            updateDate = row[AdminMenu.updateDate].millis
    )
}