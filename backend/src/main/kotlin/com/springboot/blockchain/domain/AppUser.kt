package com.springboot.blockchain.domain

import lombok.Data
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Data
@Document("User")
data class AppUser (
        @Id
        var _id: ObjectId?,
        var name: String?,
        val email: String?,
		var socialInsuranceNumber: String?,
        var username: String?,
        var password: String?,
		var role: String?,
        var phone: String?,
        var isActive: Boolean = false,
        var timeStamp: Long = Date().time
)