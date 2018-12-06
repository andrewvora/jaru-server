package com.andrewvora.jaru.questionsets

import com.andrewvora.jaru.questions.QuestionRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.mockito.Mockito.`when` as whenever

/**
 * [QuestionSetController]
 */
@RunWith(SpringRunner::class)
@WebMvcTest(controllers = [QuestionSetController::class], secure = false)
class QuestionSetControllerTest {

	private val objectMapper = ObjectMapper()

	@Autowired
	private lateinit var mockMvc: MockMvc

	@MockBean private lateinit var questionSetRepository: QuestionSetRepository
	@MockBean private lateinit var questionRepository: QuestionRepository
	@MockBean private lateinit var questionSetValidator: QuestionSetValidator

	@Before
	fun beforeEveryTest() {
		whenever(questionSetRepository.save(ArgumentMatchers.any() ?: QuestionSet())).thenAnswer {
			return@thenAnswer it.arguments.first()!!
		}
	}

	@Test
	fun `validates invalid payload when updating`() {
		// given
		val questionSet = QuestionSet(setId = "set1")
		val json = objectMapper.writeValueAsString(questionSet)
		whenever(questionSetValidator.canBeInserted(any() ?: questionSet)).thenReturn(false)

		// when
		mockMvc.perform(MockMvcRequestBuilders.put("/content/v1/set/${questionSet.setId}")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				// then
				.andExpect(MockMvcResultMatchers.status().isBadRequest)

		verify(questionSetRepository, never()).save(any() ?: questionSet)
		verify(questionSetValidator).canBeInserted(any() ?: questionSet)
	}

	@Test
	fun `validates valid payload when updating`() {
		// given
		val questionSet = QuestionSet(setId = "set1")
		val json = objectMapper.writeValueAsString(questionSet)
		whenever(questionSetValidator.canBeInserted(any() ?: questionSet)).thenReturn(true)

		// when
		mockMvc.perform(MockMvcRequestBuilders.put("/content/v1/set/${questionSet.setId}")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				// then
				.andExpect(MockMvcResultMatchers.status().isOk)
				.andExpect(MockMvcResultMatchers.content().json(json))

		verify(questionSetRepository).save(any() ?: questionSet)
	}

	@Test
	fun `validates invalid payload when inserting`() {
		// given
		val questionSet = QuestionSet(setId = "set1")
		val json = objectMapper.writeValueAsString(questionSet)
		whenever(questionSetValidator.canBeInserted(any() ?: questionSet)).thenReturn(false)

		// when
		mockMvc.perform(MockMvcRequestBuilders.post("/content/v1/set")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				// then
				.andExpect(MockMvcResultMatchers.status().isBadRequest)

		verify(questionSetRepository, never()).save(any() ?: questionSet)
		verify(questionSetValidator).canBeInserted(any() ?: questionSet)
	}

	@Test
	fun `validates valid payload when inserting`() {
		// given
		val questionSet = QuestionSet(setId = "set1")
		val json = objectMapper.writeValueAsString(questionSet)
		whenever(questionSetValidator.canBeInserted(any() ?: questionSet)).thenReturn(true)

		// when
		mockMvc.perform(MockMvcRequestBuilders.post("/content/v1/set")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				// then
				.andExpect(MockMvcResultMatchers.status().isOk)
				.andExpect(MockMvcResultMatchers.content().json(json))

		verify(questionSetRepository).save(any() ?: questionSet)
		verify(questionSetValidator).canBeInserted(any() ?: questionSet)
	}
}