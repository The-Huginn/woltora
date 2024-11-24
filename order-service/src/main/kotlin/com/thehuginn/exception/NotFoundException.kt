package com.thehuginn.exception

class NotFoundException(
    entity: Class<*>,
    id: Any?
): Exception("Not found $entity with the corresponding ID {$id}")