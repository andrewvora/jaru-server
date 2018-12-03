package com.andrewvora.jaru.answers

import org.junit.Assert.*
import org.junit.Test

/**
 * [AnswerValidator]
 */
class AnswerValidatorTest {

	private val validator = AnswerValidator()

	/**
	 * Answers don't need to reference an existing text resource
	 * The intent is to completely decouple any dependencies.
	 */
	@Test
	fun `answers must have non-empty IDs and text resource names`() {
		val result1 = validator.isValid(Answer(answerId = "answer1", textResName = "answerText1"))
		assertTrue(result1)

		val result2 = validator.isValid(Answer(answerId = "", textResName = "answerText1"))
		assertFalse(result2)

		val result3 = validator.isValid(Answer(answerId = "answer1", textResName = ""))
		assertFalse(result3)

		val result4 = validator.isValid(Answer(answerId = "", textResName = ""))
		assertFalse(result4)
	}
}