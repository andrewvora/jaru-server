package com.andrewvora.jaru.questions

import com.andrewvora.jaru.answers.Answer
import org.junit.Assert.*
import org.junit.Test

/**
 * [QuestionValidator]
 * [Question]
 */
class QuestionValidatorTest {

	private val questionValidator = QuestionValidator()

	@Test
	fun `requires non-empty ID, text and description`() {
		assertTrue(questionValidator.isValid(
				Question(questionId = "question1",
						textResName = "text1",
						transcriptionResName = "text2")
		))

		assertFalse(questionValidator.isValid(
				Question(questionId = "",
						textResName = "text1",
						transcriptionResName = "text2")
		))

		assertFalse(questionValidator.isValid(
				Question(questionId = "question1",
						textResName = "",
						transcriptionResName = "text2")
		))

		assertFalse(questionValidator.isValid(
				Question(questionId = "question1",
						textResName = "text1",
						transcriptionResName = "")
		))
	}

	@Test
	fun `does not require a correct answer to be specified`() {
		assertTrue(questionValidator.isValid(
				Question(questionId = "question1",
						textResName = "text1",
						transcriptionResName = "text2",
						correctAnswerId = null)
		))
	}

	@Test
	fun `correct answers must be within the provided answers`() {
		assertTrue(questionValidator.isValid(
				Question(questionId = "question1",
						textResName = "text1",
						transcriptionResName = "text2",
						correctAnswerId = "answer2",
						answers = mutableListOf(
								Answer(answerId = "answer1"),
								Answer(answerId = "answer2")
						))
		))

		assertFalse(questionValidator.isValid(
				Question(questionId = "question1",
						textResName = "text1",
						transcriptionResName = "text2",
						correctAnswerId = "answer10",
						answers = mutableListOf(
								Answer(answerId = "answer1"),
								Answer(answerId = "answer2")
						))
		))
	}

	@Test
	fun `does not require answers to be provided`() {
		assertTrue(questionValidator.isValid(
				Question(questionId = "question1",
						textResName = "text1",
						transcriptionResName = "text2",
						answers = mutableListOf())
		))
	}
}