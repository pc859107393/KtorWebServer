package web

import data.UserLogin
import io.ktor.application.call
import io.ktor.http.Parameters
import io.ktor.request.receive
import io.ktor.request.receiveParameters
import io.ktor.response.respondJson
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import service.AdminUserService

fun Route.admin() {

    val adminUserService = AdminUserService()

    route("/admin") {

        get("/") {
            call.respondJson(adminUserService.getTenAdmins())
        }

        /**
         * jsonPost接口
         */
        post(path = "/login") {
            val userLogin = call.receive(UserLogin::class)
            call.respondJson(userLogin)
        }

        /**
         * 普通的post，参数上传
         */
        post(path = "/postParams") {
            val parameters = call.receive<Parameters>()
            val userName = parameters["userName"]
            val pwd = parameters["pwd"]
            val result = HashMap<String, String?>()
            result["userName"] = userName
            result["pwd"] = pwd
            call.respondJson(result)
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