package com.springboot.blockchain.exceptions

class InvalidRegistrationDataException : RuntimeException {

    constructor() {}
    constructor(message: String?) : super(message) {}
}