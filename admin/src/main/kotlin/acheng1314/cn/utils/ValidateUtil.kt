package acheng1314.cn.utils

import acheng1314.cn.cache.AdminUserCacheExtend
import acheng1314.cn.cache.Cache
import acheng1314.cn.data.AdminUserDTO
import acheng1314.cn.exception.UnauthorizedException
import io.ktor.request.ApplicationRequest
import io.ktor.request.uri
import org.apache.commons.lang3.ObjectUtils
import org.apache.commons.lang3.StringUtils

object ValidateUtil {

    /**
     * 根据请求头检查管理员用户是否存在
     * @param request 请求
     * @param validateAuth 是否需要验证，false不需要验证
     */
    @JvmOverloads
    suspend fun validateAdmin(request: ApplicationRequest, validateAuth: Boolean = false): AdminUserDTO =
            if (!request.headers.names().contains("token") || StringUtils.isBlank(request.headers["token"]))
                throw UnauthorizedException("管理员未登录！")
            else
                Cache.adminUserLoginCache.get(request.headers["token"]).also {
                    if (ObjectUtils.isEmpty(it)) throw throw UnauthorizedException("管理员未登录！")
                    //需要权限检察
                    if (validateAuth) {
                        val menus = AdminUserCacheExtend().getMenusFromCache(it)

                        assert(menus.run {
                            isEmpty() || none { dto ->
                                StringUtils.isNotBlank(dto.uri) && request.uri.contains(dto.uri)
                            }
                        }) { throw UnauthorizedException("暂无权限操作！请联系上层管理员") }

                    }
                }

}