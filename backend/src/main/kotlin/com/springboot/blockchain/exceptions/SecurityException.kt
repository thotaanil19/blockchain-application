package com.springboot.blockchain.exceptions

import org.springframework.http.HttpStatus

class SecurityException(override val message: String, val httpStatus: HttpStatus) : RuntimeException()