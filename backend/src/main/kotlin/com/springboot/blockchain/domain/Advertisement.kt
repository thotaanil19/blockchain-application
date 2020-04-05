package com.springboot.blockchain.domain

import lombok.Data
import lombok.ToString
import org.mongodb.morphia.annotations.Entity
import org.mongodb.morphia.annotations.Property
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Data
@ToString
@Document("Advertisement")
@Entity("Advertisement")
class Advertisement {

	@Id
    @Field("_id")
    @org.mongodb.morphia.annotations.Id
    var id: String? = null;
	
    @Field("SellerName")
    @Property("SellerName")
    @NotNull(message = "sellerName is missing")
    var sellerName: String? = null;
	
	
	@Field("sellerSocialInsuranceNumber")
    @Property("sellerSocialInsuranceNumber")
    @NotNull(message = "SellerSocialInsuranceNumber is missing")
    var sellerSocialInsuranceNumber: String? = null;
		

	@Field("SellerPhone")
    @Property("SellerPhone")
    @NotNull(message = "SellerPhone is missing")
    var sellerPhone: String? = null;

    @Field("Price")
    @Property("Price")
    @NotNull
    var price: Double? = null;

    @Field("SurveyNumber")
    @Property("SurveyNumber")
    @NotBlank
    var surveyNumber: String? = null;

    @Field("PropertyAddressState")
    @Property("PropertyAddressState")
    @NotBlank
    var propertyAddressState: String? = null;

    @Field("PropertyAddressCity")
    @Property("PropertyAddressCity")
    @NotBlank
    var propertyAddressCity: String? = null;

    @Field("PropertyType")
    @Property("PropertyType")
    @NotBlank
    var propertyType: String? = null;
	
	@Field("Active")
    @Property("Active")
    @NotNull
	var active: Boolean = false;

	
	@Field("TimeStamp")
    @Property("TimeStamp")
    var timeStamp: Long? = null;

}