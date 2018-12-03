package com.andrewvora.jaru.textresources

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.reset
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*
import org.mockito.Mockito.`when` as whenever

@RunWith(SpringRunner::class)
@WebMvcTest(controllers = [TextResourceController::class], secure = false)
class TextResourceControllerTest {

	private val objectMapper = ObjectMapper()

	@Autowired
	private lateinit var mockMvc: MockMvc

	@MockBean
	private lateinit var repository: TextResourceRepository
	@MockBean
	private lateinit var validator: TextResourceValidator

	@Before
	fun beforeEveryTest() {
		// return whatever was passed in
		whenever(repository.save<TextResource>(any())).then { return@then it.getArgument(0) }
		reset(validator)
	}

	@Test
	fun `responds with not found if no record`() {
		whenever(repository.find("text1", "en-US")).thenReturn(Optional.empty())

		mockMvc.perform(get("/content/v1/text/text1/en-US"))
				.andExpect(status().isNotFound)
	}

	@Test
	fun `can retrieve resource`() {
		// given
		val textResource = TextResource(resourceName = "text1", text = "this is text1", localeId = "en-US")
		whenever(repository.find("text1", "en-US")).thenReturn(Optional.of(listOf(textResource)))

		// then
		mockMvc.perform(get("/content/v1/text/text1/en-US"))
				.andExpect(status().isOk)
				.andExpect(content().json(objectMapper.writeValueAsString(textResource)))
	}

	@Test
	fun `cannot add invalid text resources`() {
		// given
		val invalidResource = TextResource()
		whenever(validator.isValid(invalidResource)).thenCallRealMethod()

		// when
		val json = objectMapper.writeValueAsString(invalidResource)
		mockMvc.perform(post("/content/v1/text")
				.header(HttpHeaders.CONTENT_TYPE, "application/json").content(json))
				.andExpect(status().isBadRequest)
	}

	@Test
	fun `can add valid text resource`() {
		// given
		val validResource = TextResource(resourceName = "a", text = "b", localeId = "en-US")
		whenever(validator.isValid(validResource)).thenCallRealMethod()

		// then
		val json = objectMapper.writeValueAsString(validResource)
		mockMvc.perform(post("/content/v1/text")
				.header(HttpHeaders.CONTENT_TYPE, "application/json").content(json))
				.andExpect(status().isOk)
				.andExpect(content().json(json))
	}

	@Test
	fun `can delete when name and locale is valid`() {
		mockMvc.perform(delete("/content/v1/text/a/en-US"))
				.andExpect(status().isNoContent)
	}

	@Test
	fun `can add text resources in batches`() {
		// given
		val resource1 = TextResource(resourceName = "a", text = "b", localeId = "en-US")
		val resource2 = resource1.copy(resourceName = "b", text = "c")
		whenever(validator.isValid(any() ?: resource2)).thenCallRealMethod()

		// when
		val json = objectMapper.writeValueAsString(listOf(resource1, resource2))
		mockMvc.perform(post("/content/v1/text/batch")
				.header(HttpHeaders.CONTENT_TYPE, "application/json").content(json))
				.andExpect(status().isOk)
	}

	@Test
	fun `in batch creates all resources must be valid`() {
		// given
		val validResource = TextResource(resourceName = "a", text = "b", localeId = "en-US")
		val invalidResource = TextResource()
		whenever(validator.isValid(any() ?: invalidResource)).thenCallRealMethod()

		// when
		val json = objectMapper.writeValueAsString(listOf(validResource, invalidResource))
		mockMvc.perform(post("/content/v1/text")
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).content(json))
				// then
				.andExpect(status().isBadRequest)
	}

	@Test
	fun `throw conflict if trying to create record that already exists`() {
		// given
		val textResource = TextResource(resourceName = "a", text = "b", localeId = "en-US")
		val json = objectMapper.writeValueAsString(textResource)
		whenever(validator.isValid(any() ?: textResource)).thenCallRealMethod()
		whenever(repository.exists(anyString(), anyString())).thenReturn(true)

		// when
		mockMvc.perform(post("/content/v1/text")
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.content(json))
				// then
				.andExpect(status().isConflict)

	}

	@Test
	fun `throw conflict if batch create contains existing record`() {
		// given
		val resource1 = TextResource(resourceName = "a", text = "b", localeId = "en-US")
		val resource2 = resource1.copy(resourceName = "b", text = "c")
		whenever(validator.isValid(any() ?: resource2)).thenCallRealMethod()
		whenever(repository.exists(anyString(), anyString())).thenReturn(true)

		// when
		val json = objectMapper.writeValueAsString(listOf(resource1, resource2))
		mockMvc.perform(post("/content/v1/text/batch")
				.header(HttpHeaders.CONTENT_TYPE, "application/json").content(json))
				.andExpect(status().isConflict)
	}
}