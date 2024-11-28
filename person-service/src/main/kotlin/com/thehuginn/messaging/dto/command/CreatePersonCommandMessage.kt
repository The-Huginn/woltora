package com.thehuginn.messaging.dto.command

import com.thehuginn.enums.PersonType
import com.thehuginn.enums.PersonType.CUSTOMER
import java.util.UUID

class CreatePersonCommandMessage (
    val firstName: String,
    val lastName: String,
    val email: String,
    val type: PersonType = CUSTOMER
)