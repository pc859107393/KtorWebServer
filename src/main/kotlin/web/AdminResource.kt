package web

import io.ktor.application.call
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

        post(path = "/login") {

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