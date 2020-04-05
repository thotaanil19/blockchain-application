package com.springboot.blockchain.service;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.springboot.blockchain.dao.BlockRepository;
import com.springboot.blockchain.dao.morphia.BlockMorphiaRepository;
import com.springboot.blockchain.domain.Block
import com.springboot.blockchain.domain.Registration;
import com.springboot.blockchain.exceptions.InvalidRegistrationDataException;
import com.springboot.blockchain.util.Utils;
import com.springboot.blockchain.dao.AdvertisementRepository
import com.springboot.blockchain.domain.Advertisement

@Service
class AdvertisementsService {

	@Autowired
	lateinit private var advertisementRepository: AdvertisementRepository;
	
	@Autowired
	lateinit private var registrationService: RegistrationService;
	
	public fun save(advertisement: Advertisement): Boolean {
		// Before advertising the property check whether advertiser is current owner or not
		var propertyBlock: Block? = registrationService.getLatestBlockForProperty(advertisement.surveyNumber);
		if (null != propertyBlock &&
			propertyBlock.data.buyerSocialInsuranceNumber.equals(advertisement.sellerSocialInsuranceNumber, true)) {
			// Check whether advertisement already exists or not
			/*if (advertisement.id == null) {
				advertisementRepository.save(advertisement);
				return true;
			} else {
				advertisement.id?.let {
				var advertisementOptional: Optional<Advertisement> = advertisementRepository.findById(it);
			    if (!advertisementOptional.isPresent() || advertisementOptional.get().active == false) {
				advertisementRepository.save(advertisement);
				return true;
				}
			}
			}*/
			
			advertisementRepository.save(advertisement);
			return true;
						
		}		
		return false;
	}
	
	
	public fun delete(id: String): Boolean {
		// Before deleting advertisement check whether advertiser is current owner or not
		var advertisementOptional: Optional<Advertisement> = advertisementRepository.findById(id);
		if (advertisementOptional.isPresent()) {
			var advertisement: Advertisement = advertisementOptional.get();			
			var propertyBlock: Block? = registrationService.getLatestBlockForProperty(advertisement.surveyNumber);
			Utils.getLoggedInUserName()
			if (null != propertyBlock &&
				propertyBlock.data.buyerSocialInsuranceNumber.equals(advertisement.sellerSocialInsuranceNumber, true)) {
				advertisementRepository.deleteById(id);
				return true;
			}
		}
		
		return false;
	}

	public fun getAll(): MutableList<Advertisement>? {
		var advertisements: MutableList<Advertisement>? = null;
		var optional: Optional<MutableList<Advertisement>> = advertisementRepository.findAllByActiveOrderByTimeStampDesc(true);

		if (optional.isPresent()) {
			advertisements = optional.get();
		}
		return advertisements;
	}

}