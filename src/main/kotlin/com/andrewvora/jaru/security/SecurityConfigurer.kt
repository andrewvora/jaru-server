package com.andrewvora.jaru.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import javax.servlet.http.HttpServletResponse

@Configuration
@EnableWebSecurity
class SecurityConfig
@Autowired
constructor(
		private val authEntryPoint: AuthEntryPoint,
		@Value("\${admin.username}")
		private val adminUsername: String,
		@Value("\${admin.password}")
		private val adminPassword: String
) : WebSecurityConfigurerAdapter() {

	override fun configure(auth: AuthenticationManagerBuilder?) {
		auth?.inMemoryAuthentication()
				?.withUser(adminUsername)
				?.password(passwordEncoder().encode(adminPassword))
				?.roles("ADMIN")
	}

	override fun configure(http: HttpSecurity?) {
		http?.csrf()?.disable()
				?.sessionManagement()
				?.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				?.and()
				?.exceptionHandling()?.authenticationEntryPoint { _, res, _ ->
					res.sendError(HttpServletResponse.SC_FORBIDDEN)
				}
				?.and()
				?.httpBasic()
				?.authenticationEntryPoint(authEntryPoint)
				?.and()
				?.authorizeRequests()
				?.antMatchers("/content/v1/**")
				?.hasRole("ADMIN")
				?.antMatchers("/api/v1/**")
				?.permitAll()
	}

	@Bean
	fun passwordEncoder(): PasswordEncoder {
		return BCryptPasswordEncoder()
	}
}