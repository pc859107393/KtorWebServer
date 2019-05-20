package web

import data.UserLogin
import io.ktor.application.call
import io.ktor.http.Parameters
import io.ktor.request.receive
import io.ktor.response.respondJson
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import org.json.simple.JSONObject
import org.valiktor.functions.isNotBlank
import org.valiktor.validate
import service.AdminMenuService
import service.AdminUserService
import utils.ValidateUtil

fun Route.admin() {

    val adminUserService = AdminUserService()

    val adminMenuService = AdminMenuService()

    val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)

    route("/admin") {

        get("/") {
            call.respondJson(ValidateUtil.validateAdmin(call.request).also { it.password = "" })
        }

        /**
         * 权限菜单
         */
        get("/menus") {
            val adminUserDTO = ValidateUtil.validateAdmin(call.request)
            val menus = adminMenuService.getMenusFromCache(adminUserDTO)
            //父菜单
            val parent = menus.filter { dto -> dto.parentId == 0 }.sortedBy { dto -> dto.sort }

            val result = mutableListOf<Any>()

            //迭代父菜单
            parent.forEach {
                val menu = JSONObject()
                //菜单id
                menu[it::id] = it.id
                //菜单序号
                menu[it::sort] = it.sort
                //菜单名称
                menu[it::name] = it.name
                //菜单图标
                menu[it::icon] = it.icon
                //存放子菜单
                menu["son"] = menus.filter { dto -> dto.parentId == it.id }.sortedBy { dto -> dto.sort }
                result.add(menu)
            }
            call.respondJson(result)
        }

        get("/exception") {
            logger.info("全局异常调试！")
            throw Exception("我是异常信息！")
        }

        /**
         * jsonPost接口,密码16位小写
         */
        post(path = "/login") {
            val userLogin = call.receive(UserLogin::class)
            validate(userLogin, block = {
                validate(UserLogin::userName).isNotBlank()
                validate(UserLogin::pwd).isNotBlank()
            })
            call.respondJson(adminUserService.login(userLogin), "登录成功！")
        }

        /**
         * 普通的post，参数上传
         */
        post(path = "/loginDef") {
            val parameters = call.receive<Parameters>()
            val userLogin = UserLogin(parameters[UserLogin::userName.name] ?: throw java.lang.Exception("用户账户不能为空！")
                    , parameters[UserLogin::pwd.name] ?: throw java.lang.Exception("用户密码不能为空！"))
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
            call.respondJson(adminUserService.getAllAdminUserFromCache())
        }
    }
}