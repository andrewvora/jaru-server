package com.andrewvora.jaru.questionsets

import com.andrewvora.jaru.questions.Question
import com.andrewvora.jaru.questions.QuestionValidator
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when` as whenever

/**
 * [QuestionSetValidator]
 * [QuestionSet]
 */
class QuestionSetValidatorTest {

	private val questionValidator = mock(QuestionValidator::class.java)
	private val validator = QuestionSetValidator(questionValidator = questionValidator)

	@Before
	fun beforeEveryTest() {
		whenever(questionValidator.canBeInserted(any() ?: Question())).thenReturn(true)
	}

	@Test
	fun `requires non-empty set ID, title resource and description resource`() {
		assertTrue(validator.canBeInserted(
				QuestionSet(setId = "set1",
						titleResourceName = "text1",
						descriptionResourceName = "text2")
		))

		assertFalse(validator.canBeInserted(
				QuestionSet(setId = "",
						titleResourceName = "text1",
						descriptionResourceName = "text2")
		))

		assertFalse(validator.canBeInserted(
				QuestionSet(setId = "set1",
						titleResourceName = "",
						descriptionResourceName = "text2")
		))

		assertFalse(validator.canBeInserted(
				QuestionSet(setId = "set1",
						titleResourceName = "text1",
						descriptionResourceName = "")
		))
	}

	@Test
	fun `does not require questions to be defined`() {
		assertTrue(validator.canBeInserted(
				QuestionSet(setId = "set1",
						titleResourceName = "text1",
						descriptionResourceName = "text2",
						questions = mutableListOf())
		))
	}

	@Test
	fun `requires questions to be valid`() {
		// given
		whenever(questionValidator.canBeInserted(any() ?: Question())).thenReturn(false)

		// when
		assertFalse(validator.canBeInserted(
				QuestionSet(setId = "set1",
						titleResourceName = "text1",
						descriptionResourceName = "text2",
						questions = mutableListOf(Question()))
		))
	}
}