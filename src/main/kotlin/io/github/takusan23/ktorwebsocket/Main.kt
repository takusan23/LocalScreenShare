package io.github.takusan23.ktorwebsocket

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import java.util.*


fun main() {

    val classloader = Thread.currentThread().contextClassLoader
    val indexHtml = classloader.getResourceAsStream("index.html")!!.readAllBytes()

    embeddedServer(Netty, port = 8080) {
        install(WebSockets)

        routing {
            get("/") {
                this.call.respondBytes(indexHtml, ContentType.parse("text/html"))
            }

            val connections = Collections.synchronizedSet<DefaultWebSocketSession?>(LinkedHashSet())
            webSocket("capture") {
                connections += this
                for (frame in incoming) {
                    frame as? Frame.Binary ?: continue
                    connections.forEach { it.send(frame.readBytes()) }
                }
            }
        }

    }.start(wait = true)


}