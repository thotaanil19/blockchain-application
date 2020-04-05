package com.springboot.blockchain.dto

import lombok.Data

@Data
data class LoginSuccessDTO(var name: String?,
                           var token: String?,
                           var loginId: String?,
                           var email: String?,
                           var phone: String?,
                           var role: String?,
                           var isActive: Boolean,
						   var socialInsuranceNumber: String?
                           )





