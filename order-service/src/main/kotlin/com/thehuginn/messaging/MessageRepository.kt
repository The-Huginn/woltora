package com.thehuginn.messaging

class MessageRepository {
    
    companion object Message {
        const val CREATE_ORDER = "createOrder"
    }
    
    fun getAllMessages(): Set<String> {
        return setOf(
            CREATE_ORDER
        )
    }

}