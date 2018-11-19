package com.andrewvora.jaru.security

import org.springframework.http.HttpHeaders
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthEntryPoint : BasicAuthenticationEntryPoint() {

	override fun afterPropertiesSet() {
		realmName = "Jaru"
		super.afterPropertiesSet()
	}

	override fun commence(request: HttpServletRequest?, response: HttpServletResponse?, authException: AuthenticationException?) {
		response?.addHeader(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"$realmName\"")
		response?.status = HttpServletResponse.SC_UNAUTHORIZED
		response?.writer?.println(authException?.message ?: "")
	}
}