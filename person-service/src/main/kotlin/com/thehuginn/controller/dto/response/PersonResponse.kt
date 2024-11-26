package com.thehuginn.controller.dto.response

import com.thehuginn.enums.PersonType
import java.util.UUID

data class PersonResponse (
    val id: UUID,
    val firstName: String,
    val lastName: String,
    val email: String,
    val role: PersonType
)