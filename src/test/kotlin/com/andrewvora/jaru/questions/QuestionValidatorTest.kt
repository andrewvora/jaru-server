package com.andrewvora.jaru.questions

import com.andrewvora.jaru.answers.Answer
import com.andrewvora.jaru.answers.AnswerValidator
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when` as whenever

/**
 * [QuestionValidator]
 * [Question]
 */
class QuestionValidatorTest {

	private val answerValidator = mock(AnswerValidator::class.java)
	private val questionValidator = QuestionValidator(answerValidator = answerValidator)

	@Before
	fun beforeEveryTest() {
		whenever(answerValidator.canBeInserted(any() ?: Answer())).thenReturn(true)
	}

	@Test
	fun `requires non-empty ID, text and description`() {
		assertTrue(questionValidator.canBeInserted(
				Question(questionId = "question1",
						textResName = "text1",
						transcriptionResName = "text2")
		))

		assertFalse(questionValidator.canBeInserted(
				Question(questionId = "",
						textResName = "text1",
						transcriptionResName = "text2")
		))

		assertFalse(questionValidator.canBeInserted(
				Question(questionId = "question1",
						textResName = "",
						transcriptionResName = "text2")
		))

		assertFalse(questionValidator.canBeInserted(
				Question(questionId = "question1",
						textResName = "text1",
						transcriptionResName = "")
		))
	}

	@Test
	fun `does not require answers to be provided`() {
		assertTrue(questionValidator.canBeInserted(
				Question(questionId = "question1",
						textResName = "text1",
						transcriptionResName = "text2",
						answers = mutableListOf())
		))
	}

	@Test
	fun `included answers must be valid`() {
		// given
		whenever(answerValidator.canBeInserted(any() ?: Answer())).thenReturn(false)

		// when
		assertFalse(questionValidator.canBeInserted(
				Question(questionId = "question1",
						textResName = "text1",
						transcriptionResName = "text2",
						answers = mutableListOf(Answer()))
		))
	}
}