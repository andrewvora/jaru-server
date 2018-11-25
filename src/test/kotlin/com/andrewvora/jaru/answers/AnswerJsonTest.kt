package com.andrewvora.jaru.answers

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class AnswerJsonTest {

	private val jacksonMapper = ObjectMapper()

	@Test
	fun `ignores db ID and question associations`() {
		// given
		val answer = Answer(
				id = 5,
				answerId = "answer1",
				textResName = "string1",
				questions = mutableListOf())

		// when serializing and deserializing
		jacksonMapper.canSerialize(Answer::class.java)
		val answerJson = jacksonMapper.writeValueAsString(answer)
		val deserializedAnswer = jacksonMapper.readValue(answerJson, Answer::class.java)

		// then the ID and question fields are ignored
		assertEquals(0, deserializedAnswer.id)
		assertEquals(answer.answerId, deserializedAnswer.answerId)
		assertEquals(answer.textResName, deserializedAnswer.textResName)
		assertTrue(deserializedAnswer.questions.isEmpty())
	}
}