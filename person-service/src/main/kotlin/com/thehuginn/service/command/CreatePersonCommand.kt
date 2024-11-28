package com.thehuginn.service.command

import com.thehuginn.enums.PersonType

class CreatePersonCommand (
    val firstName: String,
    val lastName: String,
    val email: String,
    val type: PersonType
)