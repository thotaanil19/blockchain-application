package com.springboot.blockchain.dto

import lombok.Data

@Data
data class SignUpRequestDTO (
    var name: String?,
    var email: String?,
    var username: String?,
    var password: String?,
	var socialInsuranceNumber: String?,
    var role: String?,
    var phone: String?)