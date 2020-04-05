package com.springboot.blockchain.util

import com.springboot.blockchain.domain.Block
import com.springboot.blockchain.exceptions.InvalidBlockChainException
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.security.core.context.SecurityContextHolder
import java.security.MessageDigest
import com.springboot.blockchain.domain.AppUser

class Utils {

    companion object {


        // Applies Sha256 to a string and returns the result.
        @JvmStatic
        fun applySha256(input: String): String {
            return try {
                val digest = MessageDigest.getInstance("SHA-256")
                // Applies sha256 to our input,
                val hash = digest.digest(input.toByteArray(charset("UTF-8")))
                val hexString = StringBuffer() // This will contain hash as hexidecimal
                for (i in hash.indices) {
                    val hex = Integer.toHexString(0xff and hash[i].toInt())
                    if (hex.length == 1) hexString.append('0')
                    hexString.append(hex)
                }
                hexString.toString()
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }

        @JvmStatic
        fun validateBlockChain(blockchain: List<Block>) {
            var currentBlock: Block
            var previousBlock: Block
            for (i in 0 until blockchain.size - 1) {
                currentBlock = blockchain[i]
                previousBlock = blockchain[i + 1]
                if (currentBlock.hash != currentBlock.calculateHash()) {
                    throw InvalidBlockChainException(String.format("Block %d hash is not matching with its block data", i))
                }
                if (previousBlock.hash != currentBlock.previousHash) {
                    println("Previous Hashes not equal")
                    throw InvalidBlockChainException(String.format("Block %d previous hash and %d block hash values are not matching", i - 1, i))
                }
            }
        }

        @JvmStatic
        fun generateRandomPassword(): String {
			var password = RandomStringUtils.randomAlphanumeric(6);
			//System.out.println("*****************************************");
			//System.out.println(password);
			//System.out.println("*****************************************");
            return password;
        }

        @JvmStatic
        fun getLoggedInUserName (): String? {
            if (null != SecurityContextHolder.getContext() &&
                    null != SecurityContextHolder.getContext().authentication) {
                return SecurityContextHolder.getContext().authentication.name;
            }
            return null;

        }
		

    }
}