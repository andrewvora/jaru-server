package com.andrewvora.jaru.api

import com.andrewvora.jaru.api.mappers.GlossaryConverter
import com.andrewvora.jaru.api.mappers.QuestionSetConverter
import com.andrewvora.jaru.api.models.LearningSetDto
import com.andrewvora.jaru.api.models.QuestionSetDto
import com.andrewvora.jaru.glossary.Glossary
import com.andrewvora.jaru.glossary.GlossaryRepository
import com.andrewvora.jaru.questionsets.QuestionSet
import com.andrewvora.jaru.questionsets.QuestionSetRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.mockito.Mockito.`when` as whenever

@RunWith(SpringRunner::class)
@WebMvcTest(controllers = [ApiContentController::class], secure = false)
class ApiContentControllerTest {

	@Autowired
	private lateinit var objectMapper: ObjectMapper

	@Autowired
	private lateinit var mockMvc: MockMvc

	@MockBean
	private lateinit var questionSetRepository: QuestionSetRepository
	@MockBean
	private lateinit var questionSetConverter: QuestionSetConverter
	@MockBean
	private lateinit var glossaryRepository: GlossaryRepository
	@MockBean
	private lateinit var glossaryConverter: GlossaryConverter

	@Before
	fun beforeEveryTest() {
		whenever(glossaryRepository.findAll()).thenReturn(mutableListOf())
	}

	@Test
	fun `returns empty array when no question sets are found`() {
		// given
		val learningSetDto = LearningSetDto(
				questionSets = mutableListOf(),
				glossary = mutableListOf())

		whenever(questionSetRepository.findAll()).thenReturn(mutableListOf())

		// when
		mockMvc.perform(get("/api/v1/full"))
				// then
				.andExpect(status().isOk)
				.andExpect(content().json(objectMapper.writeValueAsString(learningSetDto)))
	}

	@Test
	fun `converts question sets and packages it as a learning set`() {
		// given
		val questionSet = QuestionSet()
		val questionSetDto = QuestionSetDto(
				id = "1",
				difficulty = QuestionSet.Difficulty.BEGINNER,
				title = "hey",
				description = "crazy questions",
				questions = emptyList())
		val learningSetDto = LearningSetDto(
				questionSets = mutableListOf(questionSetDto),
				glossary = mutableListOf())
		whenever(questionSetRepository.findAll()).thenReturn(mutableListOf(questionSet))
		whenever(questionSetConverter.toDto(questionSet, "en-MX")).thenReturn(questionSetDto)

		// when
		mockMvc.perform(get("/api/v1/full?locale=en-MX"))
				// then
				.andExpect(status().isOk)
				.andExpect(content().json(objectMapper.writeValueAsString(learningSetDto)))
	}

	@Test
	fun `not passing locale defaults to en-US`() {
		// given
		val questionSet = QuestionSet()
		whenever(questionSetRepository.findAll()).thenReturn(mutableListOf(questionSet))
		val glossary = Glossary()
		whenever(glossaryRepository.findAll()).thenReturn(mutableListOf(glossary))

		// when
		mockMvc.perform(get("/api/v1/full"))
				// then
				.andExpect(status().isOk)

		// then
		verify(glossaryConverter).toDto(glossary, "en-US")
		verify(questionSetConverter).toDto(questionSet, "en-US")
	}
}