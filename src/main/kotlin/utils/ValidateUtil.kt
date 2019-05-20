package utils

import cache.Cache
import exception.UnauthorizedException
import io.ktor.request.ApplicationRequest
import model.AdminUserDTO
import org.apache.commons.lang3.ObjectUtils
import org.apache.commons.lang3.StringUtils

object ValidateUtil {

    /**
     * 根据请求头检查管理员用户是否存在
     */
    fun validateAdmin(request: ApplicationRequest): AdminUserDTO =
            if (!request.headers.names().contains("token") || StringUtils.isBlank(request.headers["token"]))
                throw UnauthorizedException("管理员未登录！")
            else
                Cache.adminUserLoginCache.get(request.headers["token"]).also {
                    if (ObjectUtils.isEmpty(it)) throw throw UnauthorizedException("管理员未登录！")
                }

}