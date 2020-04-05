package com.springboot.blockchain.config;

import com.springboot.blockchain.constants.SecurityConstants.LOGIN_URL
import com.springboot.blockchain.constants.SecurityConstants.SIGN_UP_URL
import com.springboot.blockchain.filter.JWTAuthenticationFilter
import com.springboot.blockchain.service.UserDetailsServiceImpl
import com.springboot.blockchain.util.JwtTokenUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.BeanIds
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
open class WebSecurity() : WebSecurityConfigurerAdapter() {

	@Autowired
	lateinit private var userDetailsServiceImpl: UserDetailsServiceImpl;

	@Autowired
	private lateinit var bCryptPasswordEncoder: BCryptPasswordEncoder;

	@Autowired
	private lateinit var jwtTokenUtil: JwtTokenUtil;

	@Override
	protected override fun configure(http: HttpSecurity) {
		http.cors().and().csrf().disable().authorizeRequests()
		//.antMatchers(HttpMethod.POST, SIGN_UP_URL, LOGIN_URL).permitAll()
		.antMatchers(HttpMethod.POST, SIGN_UP_URL, LOGIN_URL, "/","/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**",
				"**/user/signup", "/user/signup", "**/signup", "**signup**"
		).permitAll()
		//.antMatchers("**/login/register", "**/login/unregister").hasAuthority("ADMIN")
 
		.anyRequest().authenticated().and()
		.addFilterBefore(
				JWTAuthenticationFilter(userDetailsServiceImpl, bCryptPasswordEncoder, jwtTokenUtil),
				UsernamePasswordAuthenticationFilter::class.java)
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	@Override
	public override fun configure(auth: AuthenticationManagerBuilder) {
		auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(bCryptPasswordEncoder);
	}

	@Bean
	public open fun corsConfigurationSource() : CorsConfigurationSource{
		var source: UrlBasedCorsConfigurationSource = UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
	
	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public open override fun authenticationManagerBean(): AuthenticationManager {
		return super.authenticationManagerBean();
	}

	@Bean
	public open fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
		return BCryptPasswordEncoder();
	}
}
