package com.springboot.blockchain.exceptions

class InvalidBlockChainException : RuntimeException {

    constructor() {}
    constructor(message: String?) : super(message) {}
}