package io.github.takusan23.ktorwebsocket

import io.ktor.websocket.*
import java.util.concurrent.atomic.AtomicInteger

class Connection(val session: DefaultWebSocketSession) {
    companion object {
        private val lastId = AtomicInteger(0)
    }

    /** ユーザーID的な、特になくても良い */
    val userNo = lastId.getAndIncrement()
}