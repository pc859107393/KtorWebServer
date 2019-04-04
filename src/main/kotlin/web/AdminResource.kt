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
            call.respondJson(adminUserService.getAllAdminUsers())
        }

        post(path = "/login") {

        }

        get("/emptyAdmins") {
            call.respondJson(emptyList<String>())
        }

    }
}