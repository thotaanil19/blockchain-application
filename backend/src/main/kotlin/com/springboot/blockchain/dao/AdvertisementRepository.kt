package com.springboot.blockchain.dao

import com.springboot.blockchain.domain.Block
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.Optional
import com.springboot.blockchain.domain.Advertisement

interface AdvertisementRepository : MongoRepository<Advertisement, String> {
	fun findAllByActiveOrderByTimeStampDesc(active : Boolean): Optional<MutableList<Advertisement>>
	fun deleteAllBySurveyNumber (surveyNumber: String?): Long;
}