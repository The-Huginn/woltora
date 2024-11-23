package com.thehuginn.sql

import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.FUNCTION
import kotlin.annotation.AnnotationTarget.CLASS

@Target(FUNCTION, CLASS)
@Retention(RUNTIME)
annotation class Sql(val scripts: Array<String>)