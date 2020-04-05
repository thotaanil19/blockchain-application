package com.springboot.blockchain.dto

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor

@Data
@AllArgsConstructor
@NoArgsConstructor
data class EmailDTO(var recipientName: String?, var to: String?, var subject: String?, var body: String?) {
    
}