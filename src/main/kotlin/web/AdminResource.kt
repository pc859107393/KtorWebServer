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
import org.valiktor.functions.isNotBlank
import org.valiktor.validate
import service.AdminUserService
import utils.LoginValidate

fun Route.admin() {

    val adminUserService = AdminUserService()

    val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)

    route("/admin") {

        get("/") {
            LoginValidate.validateAdmin(call.request)
            call.respondJson(adminUserService.getTenAdmins())
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
            var pageNum = call.parameters["pageNum"]?.toInt()!!
            if (pageNum < 1) pageNum = 1
            call.respondJson(adminUserService.getAdminsByPage(pageNum)
                    , pageNum
                    , 20)
        }
    }
}