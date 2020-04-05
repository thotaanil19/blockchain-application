package com.springboot.blockchain.domain

import java.util.Date;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.blockchain.util.Utils;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
@Document("Block")
@Entity("Block")

class Block {

    @Id
    @Field("_id")
    @org.mongodb.morphia.annotations.Id
    var id: String? = null;

    //@Reference
    var data: Registration;

    @Field("Hash")
    @Property("Hash")
    var hash: String?;

    @Field("PreviousHash")
    @Property("PreviousHash")
    var previousHash: String?;

    @Field("TimeStamp")
    @Property("TimeStamp")
    var timeStamp: Long;

    @Field("RegisterationDoneBy")
    @Property("RegisterationDoneBy")
    var registerationDoneBy: String?;

    constructor(data: Registration, previousHash: String?) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = Date().time;
        this.hash = calculateHash();
        this.registerationDoneBy = Utils.getLoggedInUserName();
    }

    fun calculateHash(): String {
        var objectMapper = ObjectMapper();
        var json: String? = null;
        try {
            json =  objectMapper.writeValueAsString(this.data);
        } catch (e: Exception) {
            throw RuntimeException("Failed to convert Registration data into JSON String");
        }
        var calculatedhash = Utils.applySha256(previousHash + timeStamp.toString() + json);
        return calculatedhash;
    }

}