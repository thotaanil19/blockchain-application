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

@Service
class RegistrationService {

	//@Autowired
	//lateinit private var blockMorphiaRepository: BlockMorphiaRepository;

	@Autowired
	lateinit private var blockRepository: BlockRepository;
	
	@Autowired
	lateinit private var advertisementRepository: AdvertisementRepository;
	
	@PostConstruct
	public fun addGenesisBlock () {
		var allBlocks = blockRepository.findAll();
		if (CollectionUtils.isEmpty(allBlocks)) {
			var genesisBlock = Block(Registration(), null);
			blockRepository.save(genesisBlock);
		}
	}
	
	public fun saveRegistration(registration: Registration): Boolean{
		var previousHash: String? = null;

		// Validation: Seller and Buyer can not be same
		if (registration.buyerSocialInsuranceNumber.equals(registration.sellerSocialInsuranceNumber)) {
			throw InvalidRegistrationDataException("Seller and Buyer can not be same");
		}

		var allBlocks = blockRepository.findAll(Sort(Direction.DESC, "timeStamp"));
		if (!CollectionUtils.isEmpty(allBlocks)) {

			// Validations
			Utils.validateBlockChain(allBlocks);
			var recentBlockBySurvey = getLatestBlockForProperty(registration.surveyNumber, allBlocks);
			if (recentBlockBySurvey != null
					&& !(registration.sellerSocialInsuranceNumber.equals(recentBlockBySurvey.data.buyerSocialInsuranceNumber, true)
							&& registration.sellerName.equals(recentBlockBySurvey.data.buyerName, true))
					&& registration.propertyAddressState
							.equals(recentBlockBySurvey.data.propertyAddressState, true)
					&& registration.propertyAddressCity
							.equals(recentBlockBySurvey.data.propertyAddressCity, true)) {
				if (registration.buyerSocialInsuranceNumber.equals(recentBlockBySurvey.data.buyerSocialInsuranceNumber, true)) {
					throw InvalidRegistrationDataException("Duplicate registration");
				} else if (registration.sellerSocialInsuranceNumber.equals(recentBlockBySurvey.data.buyerSocialInsuranceNumber, true)
						&& !registration.sellerName.equals(recentBlockBySurvey.data.buyerName, true)) {
					throw InvalidRegistrationDataException("Seler Adhar Id and Names are not matching");
				} else {
					throw InvalidRegistrationDataException(
							String.format("Seller %s not owns this property.  %s owns this property.", registration.sellerName,
									recentBlockBySurvey.data.buyerName));
				}
			}

			var previousBlock = allBlocks.get(0);
			previousHash = previousBlock.hash;
		}
		
		var block = Block(registration, previousHash);
		//blockMorphiaRepository.save(block);
		blockRepository.save(block);
		//Delete the the Advertisemnst on this property if any.
		advertisementRepository.deleteAllBySurveyNumber(registration.surveyNumber);
		
		return true;
	}

	public fun getLatestBlockForProperty(surveyNumber: String?, allBlocks: MutableList<Block>): Block? {
		if (!CollectionUtils.isEmpty(allBlocks)) {
			for (block in allBlocks) {
				if (surveyNumber != null && surveyNumber.equals(block.data.surveyNumber, true)) {
					return block;
				}
			}
		}
		return null;
	}
	
	public fun getLatestBlockForProperty(surveyNumber: String?): Block? {
		var allBlocks: MutableList<Block>? = getRegistrationHistory();
		if (!CollectionUtils.isEmpty(allBlocks)) {
			var allBlocks2: List<Block> = ArrayList<Block>(allBlocks);
			for (block in allBlocks2) {
				if (surveyNumber != null && surveyNumber.equals(block.data.surveyNumber, true)) {
					return block;
				}
			}
		}
		return null;
	}

	public fun getRegistrationHistory(): MutableList<Block>? {
		var blockChain: MutableList<Block>? = null;
		var optional: Optional<MutableList<Block>> = blockRepository.findAllByOrderByTimeStampDesc();

		if (optional.isPresent()) {
			blockChain = optional.get();
			Utils.validateBlockChain(blockChain);
		}
		// Remove Genesis Block
		if (blockChain != null) {
			blockChain.removeAt(blockChain.size-1);
		}
		return blockChain;
	}

}