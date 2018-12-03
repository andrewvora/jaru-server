package com.andrewvora.jaru.questionsets

import org.junit.Assert.*
import org.junit.Test

/**
 * [QuestionSetValidator]
 * [QuestionSet]
 */
class QuestionSetValidatorTest {

	private val validator = QuestionSetValidator()

	@Test
	fun `requires non-empty set ID, title resource and description resource`() {
		assertTrue(validator.isValid(
				QuestionSet(setId = "set1",
						titleResourceName = "text1",
						descriptionResourceName = "text2")
		))

		assertFalse(validator.isValid(
				QuestionSet(setId = "",
						titleResourceName = "text1",
						descriptionResourceName = "text2")
		))

		assertFalse(validator.isValid(
				QuestionSet(setId = "set1",
						titleResourceName = "",
						descriptionResourceName = "text2")
		))

		assertFalse(validator.isValid(
				QuestionSet(setId = "set1",
						titleResourceName = "text1",
						descriptionResourceName = "")
		))
	}

	@Test
	fun `does not require questions to be defined`() {
		assertTrue(validator.isValid(
				QuestionSet(setId = "set1",
						titleResourceName = "text1",
						descriptionResourceName = "text2",
						questions = mutableListOf())
		))
	}
}