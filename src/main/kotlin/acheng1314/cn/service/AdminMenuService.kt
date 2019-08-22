package acheng1314.cn.service

import acheng1314.cn.cache.Cache
import acheng1314.cn.database.DatabaseFactory.Companion.dbQuery
import acheng1314.cn.model.AdminMenu
import acheng1314.cn.model.AdminMenuDTO
import acheng1314.cn.model.AdminUserDTO
import org.apache.commons.lang3.StringUtils
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll
import acheng1314.cn.utils.JSONUtil

/**
 * 管理员菜单
 */
class AdminMenuService {

    private val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)

    private val adminMenuCache = Cache.adminMenuIdCache

    private val siteConfigService = SiteConfigService()

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

    /**
     * 获取用户权限，从缓存中获取
     */
    suspend fun getMenusFromCache(admin: AdminUserDTO): MutableList<AdminMenuDTO> {
        val menuIds: List<Int>?
        val menus = mutableListOf<AdminMenuDTO>()
        val duty = admin.duty
        menuIds = try {
            JSONUtil.toArray(duty, Int::class.java)
        } catch (e: Exception) {
            val rule = siteConfigService.getDefualtRuleByName(duty)
            logger.info("用户${admin.loginName}没有自定义权限，默认权限为：$duty")
            JSONUtil.toArray(rule, Int::class.java)
        }
        if (menuIds.isNullOrEmpty()) throw Exception("该用户暂无权限配置！")
        if (adminMenuCache.count() == 0) notifyMenu()
        //获取权限
        return menus.apply {
            this.addAll(adminMenuCache.filter { entry -> entry.key in menuIds.orEmpty() }.map { entry -> entry.value })
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