package acheng1314.cn.web

import acheng1314.cn.bean.HousePropertyBean
import acheng1314.cn.service.HousePropertyService
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.routing.Route
import io.ktor.routing.put
import io.ktor.routing.route

fun Route.house() {

    val housePropertyService = HousePropertyService()

    route("/house") {
        //        get("/{id}") {
//            val widget = housePropertyService
//            if (widget == null) call.respondJson(HttpStatusCode.NotFound)
//            else call.respondJson(widget)
//        }

        put("/") {
            val receive = call.receive(HousePropertyBean::class)
            housePropertyService.addHouseProperty(receive)

        }


    }


}