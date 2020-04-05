package com.springboot.blockchain.domain;

import org.mongodb.morphia.annotations.Property;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
@Document("JwtToken")
class JwtToken {

    @Id
    @Field("_id")
    @org.mongodb.morphia.annotations.Id
    var id: String? = null;

    @Field("username")
    @Property("username")
    var username: String? = null;

    @Field("token")
    @Property("token")
    var token: String? = null;

    constructor(username: String?, token: String?) {
        this.username = username;
        this.token = token;
    }

    constructor() {
    }


}
