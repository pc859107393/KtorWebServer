package acheng1314.cn.cache

import acheng1314.cn.data.AdminMenuDTO
import acheng1314.cn.data.AdminUserDTO
import acheng1314.cn.service.AdminMenuService
import acheng1314.cn.service.SiteConfigService
import acheng1314.cn.utils.JSONUtil

class AdminUserCacheExtend {

    private val siteConfigService = SiteConfigService()

    private val adminMenuService = AdminMenuService()

    private val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)

    /**
     * 获取用户权限，从缓存中获取
     */
    suspend fun getMenusFromCache(admin: AdminUserDTO): MutableList<AdminMenuDTO> {
        val menuIds: List<Long>?
        val menus = mutableListOf<AdminMenuDTO>()
        val duty = admin.duty
        menuIds = try {
            JSONUtil.toArray(duty, Long::class.java)
        } catch (e: Exception) {
            val rule = siteConfigService.getDefualtRuleByName(duty)
            logger.info("用户${admin.loginName}没有自定义权限，默认权限为：$duty")
            JSONUtil.toArray(rule, Long::class.java)
        }
        if (menuIds.isNullOrEmpty()) throw Exception("该用户暂无权限配置！")
        if (Cache.adminMenuIdCache.count() == 0) adminMenuService.notifyMenu()
        //获取权限
        return menus.apply {
            this.addAll(Cache.adminMenuIdCache.filter { entry -> entry.key in menuIds.orEmpty() }.map { entry -> entry.value })
        }
    }

}