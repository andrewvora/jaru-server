package com.andrewvora.jaru.questions

import com.andrewvora.jaru.DevProfile
import com.andrewvora.jaru.answers.Answer
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@DevProfile
@DataJpaTest
class QuestionRepositoryTest {

	@Autowired
	private lateinit var questionRepository: QuestionRepository

	@Autowired
	private lateinit var entityManager: TestEntityManager

	@Test
	fun `adding questions with answers will persist everything`() {
		// given
		val answer = Answer(answerId = "answer1", textResName = "answerText1")
		val question = Question(
				questionId = "question1",
				questionType = Question.QuestionType.SINGLE_INPUT,
				textResName = "questionText1",
				answers = mutableListOf(answer))

		// when
		questionRepository.save(question)

		// then
		assertEquals(answer, entityManager.find(Answer::class.java, answer.answerId))
		assertEquals(question, entityManager.find(Question::class.java, question.questionId))
	}
}