package com.thehuginn.repository

class MessageRepository {
    
    companion object Message {
        const val CREATE_ORDER = "createOrder"
        const val ASSIGN_DELIVERY = "assignDelivery"
    }
    
    fun getAllMessages(): Set<String> {
        return setOf(
            CREATE_ORDER,
            ASSIGN_DELIVERY
        )
    }

}