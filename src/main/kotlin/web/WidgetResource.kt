package web

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.http.cio.websocket.Frame
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondJson
import io.ktor.routing.*
import io.ktor.websocket.webSocket
import kotlinx.coroutines.ObsoleteCoroutinesApi
import model.NewWidget
import service.WidgetService

@ObsoleteCoroutinesApi
fun Route.widget() {

    val widgetService = WidgetService()

    route("/widget") {

        get("/") {
            call.respondJson(widgetService.getAllWidgets())
        }

        get("/{id}") {
            val widget = widgetService.getWidget(call.parameters["id"]?.toInt()!!)
            if (widget == null) call.respondJson(HttpStatusCode.NotFound)
            else call.respondJson(widget)
        }

        post("/") {
            val widget = call.receive<NewWidget>()
            call.respond(HttpStatusCode.Created, widgetService.addWidget(widget))
        }

        put("/") {
            val widget = call.receive<NewWidget>()
            val updated = widgetService.updateWidget(widget)
            if (updated == null) call.respondJson(HttpStatusCode.NotFound)
            else call.respond(HttpStatusCode.OK, updated)
        }

        delete("/{id}") {
            val removed = widgetService.deleteWidget(call.parameters["id"]?.toInt()!!)
            if (removed) call.respondJson(HttpStatusCode.OK)
            else call.respondJson(HttpStatusCode.NotFound)
        }

    }

    val mapper = jacksonObjectMapper().apply {
        setSerializationInclusion(JsonInclude.Include.NON_NULL)
    }

    webSocket("/updates") {
        try {
            widgetService.addChangeListener(this.hashCode()) {
                outgoing.send(Frame.Text(mapper.writeValueAsString(it)))
            }
            while (true) {
                incoming.receiveOrNull() ?: break
            }
        } finally {
            widgetService.removeChangeListener(this.hashCode())
        }
    }
}
