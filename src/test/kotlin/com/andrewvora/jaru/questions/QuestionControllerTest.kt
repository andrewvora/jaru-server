package com.andrewvora.jaru.questions

import com.andrewvora.jaru.answers.Answer
import com.andrewvora.jaru.answers.AnswerValidator
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.mockito.Mockito.`when` as whenever

/**
 * [QuestionController]
 */
@RunWith(SpringRunner::class)
@WebMvcTest(controllers = [QuestionController::class], secure = false)
class QuestionControllerTest {

	private val objectMapper = ObjectMapper()

	@Autowired
	private lateinit var mockMvc: MockMvc

	@MockBean private lateinit var questionRepository: QuestionRepository
	@MockBean private lateinit var questionValidator: QuestionValidator
	@MockBean private lateinit var answerValidator: AnswerValidator

	private var mockAnswer: Answer = Answer()

	@Before
	fun beforeEveryTest() {
		whenever(questionRepository.save(any() ?: Question())).thenAnswer {
			return@thenAnswer it.arguments.first()!!
		}
	}

	@Test
	fun `validates invalid payload when updating`() {
		// given
		val question = Question(questionId = "question1")
		val json = objectMapper.writeValueAsString(question)
		whenever(questionValidator.canBeInserted(any() ?: question)).thenReturn(false)

		// when
		mockMvc.perform(put("/content/v1/question/${question.questionId}")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				// then
				.andExpect(status().isBadRequest)

		verify(questionRepository, never()).save(any() ?: question)
		verify(questionValidator).canBeInserted(any() ?: question)
	}

	@Test
	fun `validates invalid answers in payload when updating`() {
		// given
		val answer = Answer()
		val question = Question(
				questionId = "question1",
				textResName = "textRes1",
				answers = mutableListOf(answer))
		val json = objectMapper.writeValueAsString(question)
		whenever(questionValidator.canBeInserted(any() ?: question)).thenReturn(true)
		whenever(answerValidator.canBeInserted(any() ?: mockAnswer)).thenReturn(false)

		// when
		mockMvc.perform(put("/content/v1/question/${question.questionId}")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				// then
				.andExpect(status().isBadRequest)

		verify(questionRepository, never()).save(any() ?: question)
		verify(questionValidator).canBeInserted(any() ?: question)
		verify(answerValidator).canBeInserted(any() ?: mockAnswer)
	}

	@Test
	fun `validates valid payload when updating`() {
		// given
		val question = Question(questionId = "question1")
		val json = objectMapper.writeValueAsString(question)
		whenever(questionValidator.canBeInserted(any() ?: question)).thenReturn(true)
		whenever(answerValidator.canBeInserted(any() ?: mockAnswer)).thenReturn(true)

		// when
		mockMvc.perform(put("/content/v1/question/${question.questionId}")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				// then
				.andExpect(status().isOk)
				.andExpect(content().json(json))

		verify(questionRepository).save(any() ?: question)
	}

	@Test
	fun `validates invalid payload when inserting`() {
		// given
		val question = Question(questionId = "question1")
		val json = objectMapper.writeValueAsString(question)
		whenever(questionValidator.canBeInserted(any() ?: question)).thenReturn(false)

		// when
		mockMvc.perform(post("/content/v1/question")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				// then
				.andExpect(status().isBadRequest)

		verify(questionRepository, never()).save(any() ?: question)
		verify(questionValidator).canBeInserted(any() ?: question)
	}

	@Test
	fun `validates invalid answers in payload when inserting`() {
		// given
		val answer = Answer()
		val question = Question(
				questionId = "question1",
				textResName = "textRes1",
				answers = mutableListOf(answer))
		val json = objectMapper.writeValueAsString(question)
		whenever(questionValidator.canBeInserted(any() ?: question)).thenReturn(true)
		whenever(answerValidator.canBeInserted(any() ?: answer)).thenReturn(false)

		// when
		mockMvc.perform(post("/content/v1/question")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				// then
				.andExpect(status().isBadRequest)

		verify(questionRepository, never()).save(any() ?: question)
		verify(questionValidator).canBeInserted(any() ?: question)
		verify(answerValidator).canBeInserted(any() ?: mockAnswer)
	}

	@Test
	fun `validates valid payload when inserting`() {
		// given
		val question = Question(questionId = "question1")
		val json = objectMapper.writeValueAsString(question)
		whenever(questionValidator.canBeInserted(any() ?: question)).thenReturn(true)
		whenever(answerValidator.canBeInserted(any() ?: mockAnswer)).thenReturn(true)

		// when
		mockMvc.perform(post("/content/v1/question")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				// then
				.andExpect(status().isOk)
				.andExpect(content().json(json))

		verify(questionRepository).save(any() ?: question)
		verify(questionValidator).canBeInserted(any() ?: question)
	}
}