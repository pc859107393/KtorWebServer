package acheng1314.cn.web

import acheng1314.cn.cache.AdminUserCacheExtend
import acheng1314.cn.data.UserLoginDTO
import acheng1314.cn.model.NewAdminUser
import acheng1314.cn.response.respondJson
import acheng1314.cn.service.AdminMenuService
import acheng1314.cn.service.AdminUserService
import acheng1314.cn.utils.ValidateUtil
import com.alibaba.fastjson.JSONObject
import io.ktor.application.call
import io.ktor.http.Parameters
import io.ktor.request.receive
import io.ktor.routing.*
import org.valiktor.functions.isNotBlank
import org.valiktor.validate

fun Route.admin() {

    val adminUserService = AdminUserService()

    val adminMenuService = AdminMenuService()

    val adminUserCache = AdminUserCacheExtend()

    val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)

    route("/admin") {

        get("/") {

            val adminUserDTO = ValidateUtil.validateAdmin(call.request).also { it.password = "" }
            val menus = adminUserCache.getMenusFromCache(adminUserDTO)
            //父菜单
            val parent = menus.filter { dto -> dto.parentId == 0L }.sortedBy { dto -> dto.sort }

            val result = mutableListOf<Any>()

            //迭代父菜单
            parent.forEach {
                val menu = mutableMapOf<String, Any>()
                //菜单id
                menu[it::id.name] = it.id
                //菜单序号
                menu[it::sort.name] = it.sort
                //菜单名称
                menu[it::name.name] = it.name
                //菜单图标
                menu[it::icon.name] = it.icon
                //存放子菜单
                menu["son"] = menus.filter { dto -> dto.parentId == it.id }.sortedBy { dto -> dto.sort }
                result.add(menu)
            }

            val data = JSONObject()
            data["user"] = adminUserDTO
            data["roles"] = result
            call.respondJson(data)
        }

        /**
         * 权限菜单
         */
        get("/menus") {
            val adminUserDTO = ValidateUtil.validateAdmin(call.request)
            val menus = adminUserCache.getMenusFromCache(adminUserDTO)
            //父菜单
            val parent = menus.filter { dto -> dto.parentId == 0L }.sortedBy { dto -> dto.sort }

            val result = mutableListOf<Any>()

            //迭代父菜单
            parent.forEach {
                val menu = mutableMapOf<String, Any>()
                //菜单id
                menu[it::id.name] = it.id
                //菜单序号
                menu[it::sort.name] = it.sort
                //菜单名称
                menu[it::name.name] = it.name
                //菜单图标
                menu[it::icon.name] = it.icon
                //存放子菜单
                menu["son"] = menus.filter { dto -> dto.parentId == it.id }.sortedBy { dto -> dto.sort }
                result.add(menu)
            }
            call.respondJson(result)
        }

        get("") {
            logger.info("全局异常调试！")
            throw Exception("我是异常信息！")
        }

        /**
         * jsonPost接口,密码16位小写
         */
        post(path = "/login") {
            val userLogin = call.receive(UserLoginDTO::class)
            validate(userLogin, block = {
                validate(UserLoginDTO::userName).isNotBlank()
                validate(UserLoginDTO::pwd).isNotBlank()
            })
            call.respondJson(adminUserService.login(userLogin), "登录成功！")
        }

        /**
         * 普通的post，参数上传
         */
        post(path = "/loginDef") {
            val parameters = call.receive<Parameters>()
            val userLogin = UserLoginDTO(parameters[UserLoginDTO::userName.name]
                    ?: throw java.lang.Exception("用户账户不能为空！")
                    , parameters[UserLoginDTO::pwd.name] ?: throw java.lang.Exception("用户密码不能为空！"))
            call.respondJson(userLogin)
        }

        get("/emptyAdmins") {
            call.respondJson(emptyList<String>())
        }

        get("/page/{pageNum}") {
            ValidateUtil.validateAdmin(call.request)
            var pageNum = call.parameters["pageNum"]?.toInt()!!
            if (pageNum < 1) pageNum = 1
            call.respondJson(adminUserService.getAdminsByPage(pageNum)
                    , pageNum
                    , 20)
        }

        get("/getAllFromCache") {
            ValidateUtil.validateAdmin(call.request, true)
            call.respondJson(adminUserService.getAllAdminUserFromCache())
        }

        get("/notifyMenu") {
            ValidateUtil.validateAdmin(call.request, true)
            call.respondJson(adminMenuService.notifyMenu())
        }

        put("/add") {
            val adminUser = call.receive(NewAdminUser::class)
            adminUserService.addAdmin(adminUser)
        }
    }
}