package com.andrewvora.jaru.answers

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*
import org.mockito.Mockito.`when` as whenever

@RunWith(SpringRunner::class)
@WebMvcTest(controllers = [AnswerController::class], secure = false)
class AnswerControllerTest {

	private val objectMapper = ObjectMapper()

	@Autowired
	private lateinit var mockMvc: MockMvc

	@MockBean
	private lateinit var answerRepository: AnswerRepository

	@Test
	fun `returns 404 not found if resource not found`() {
		whenever(answerRepository.findById("answer1")).thenReturn(Optional.empty())
		mockMvc.perform(get("/content/v1/answer/answer1"))
				.andExpect(status().isNotFound)
	}

	@Test
	fun `returns answer if found`() {
		val answer = Answer(answerId = "answer1", textResName = "answer1Text")
		whenever(answerRepository.findById("answer1")).thenReturn(Optional.of(answer))

		mockMvc.perform(get("/content/v1/answer/answer1"))
				.andExpect(status().isOk)
				.andExpect(content().json(objectMapper.writeValueAsString(answer)))
	}

}