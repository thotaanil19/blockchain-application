package com.springboot.blockchain.domain

import lombok.Data;
import lombok.ToString;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.mongodb.morphia.annotations.Property;
import org.springframework.data.mongodb.core.mapping.Field;


@Data
@ToString
class Registration {

    @Field("SellerName")
    @Property("SellerName")
    @NotNull(message = "sellerName is missing")
    var sellerName: String? = null;

    @Field("SellerSocialInsuranceNumber")
    @Property("SellerSocialInsuranceNumber")
    @NotNull(message = "SellerSocialInsuranceNumber is missing")
    var sellerSocialInsuranceNumber: String? = null;
	
	@Field("SellerPhone")
    @Property("SellerPhone")
    @NotNull(message = "SellerPhone is missing")
    var sellerPhone: String? = null;

    @Field("BuyerName")
    @Property("BuyerName")
    @NotNull
    var buyerName: String? = null;

    @Field("BuyerSocialInsuranceNumber")
    @Property("BuyerSocialInsuranceNumber")
    @NotNull(message = "BuyerSocialInsuranceNumber is missing")
    var buyerSocialInsuranceNumber: String? = null;
	
	@Field("BuyerPhone")
    @Property("BuyerPhone")
    @NotNull(message = "BuyerPhone is missing")
    var buyerPhone: String? = null;

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



}