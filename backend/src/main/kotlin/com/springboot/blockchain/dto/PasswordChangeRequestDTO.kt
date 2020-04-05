package com.springboot.blockchain.dto

import lombok.Data

@Data
data class PasswordChangeRequestDTO(
    val username: String?,
    val password: String?,
    val newPassword: String?)