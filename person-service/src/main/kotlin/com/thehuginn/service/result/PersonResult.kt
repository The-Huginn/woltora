package com.thehuginn.service.result

import com.thehuginn.enums.PersonType
import java.util.UUID

class PersonResult (
    val id: UUID,
    val firstName: String,
    val lastName: String,
    val email: String,
    val role: PersonType
)

