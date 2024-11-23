package com.thehuginn.exceptions

class NotFoundException(
    entity: Class<*>,
    id: Any?
): Exception("Not found $entity with the corresponding ID {$id}")