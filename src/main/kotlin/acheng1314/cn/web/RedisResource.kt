package acheng1314.cn.web

import io.ktor.application.call
import acheng1314.cn.response.respondJson
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route
import acheng1314.cn.redis.RedissionFactory
import java.util.concurrent.TimeUnit

fun Route.redis() {

    route("") {
        get {
            val mylock = RedissionFactory.getRedissionClient().getLock("999999999999")
            mylock.lock(2, TimeUnit.MINUTES)
            call.respondJson(mylock.isLocked)
        }

    }


}