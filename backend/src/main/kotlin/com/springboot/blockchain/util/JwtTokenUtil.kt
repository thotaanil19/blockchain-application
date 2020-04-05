package com.springboot.blockchain.util

import com.springboot.blockchain.constants.SecurityConstants
import com.springboot.blockchain.dao.JwtTokenRepository
import com.springboot.blockchain.exceptions.SecurityException
import com.springboot.blockchain.service.UserDetailsServiceImpl
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*
import javax.crypto.spec.SecretKeySpec
import javax.servlet.http.HttpServletRequest

/**
 * Utility class for JWT token
 *
 * @author Anil
 */
@Component
class JwtTokenUtil {

    @Autowired
    private lateinit var userDetailsService: UserDetailsServiceImpl;
    @Autowired
    private lateinit var jwtTokenRepository: JwtTokenRepository;

    /**
     * Create JWT token
     *
     * @param id
     * @param issuer
     * @param subject
     * @param ttlMillis
     * @return String
     */
    fun createJWT(id: String?, issuer: String?, subject: String?, ttlMillis: Long): String { // The JWT signature algorithm we will be using to sign the token
        val signatureAlgorithm = SignatureAlgorithm.HS256
        val nowMillis = System.currentTimeMillis()
        val now = Date(nowMillis)
        // We will sign our JWT with our ApiKey secret
        val apiKeySecretBytes = SecurityConstants.SECRET.toByteArray()
        val signingKey: Key = SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.jcaName)
        // Let's set the JWT Claims
        val builder = Jwts.builder().setId(id).setIssuedAt(now).setSubject(subject).signWith(signatureAlgorithm, signingKey)
        // if it has been specified, let's add the expiration
        if (ttlMillis > 0) {
            val expMillis = nowMillis + ttlMillis
            val exp = Date(expMillis)
            builder.setExpiration(exp)
        }
        // Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact()
    }

    /**
     * Read JWT token from HttpServletRequest object
     *
     * @param req
     * @return String
     */
    fun resolveToken(req: HttpServletRequest): String? {
       return req.getHeader(SecurityConstants.AUTHORIZATION)
    }

    /**
     * Validate JWT token
     *
     * @param token
     * @return boolean
     * @throws Exception - when provided jwt token is invalid
     */
    @Throws(Exception::class)
    fun validateToken(token: String?): Boolean {
        Jwts.parser().setSigningKey(SecurityConstants.SECRET.toByteArray()).parseClaimsJws(token)
        return true
    }

    /**
     * Read username from given jwt token
     *
     * @param token
     * @return String
     */
    fun getUsername(token: String?): String {
        return Jwts.parser().setSigningKey(SecurityConstants.SECRET.toByteArray()).parseClaimsJws(token).body.subject
    }

    /**
     * Generate Authentication from given jwt token
     *
     * @param token
     * @return Authentication
     */
    fun getAuthentication(token: String?): Authentication {
        val userDetails = userDetailsService.loadUserByUsername(getUsername(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    /**
     * Checks given jwt token exists or not in DB
     * @param token
     */
    fun isTokenExistsinDB(token: String) {
        val jwtToken = jwtTokenRepository.findByToken(token)
                ?: throw SecurityException("Invalid JWT token", HttpStatus.UNAUTHORIZED)
    }
}