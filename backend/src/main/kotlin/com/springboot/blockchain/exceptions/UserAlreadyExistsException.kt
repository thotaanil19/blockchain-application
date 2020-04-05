package com.springboot.blockchain.exceptions

class UserAlreadyExistsException(val username: String) : RuntimeException()