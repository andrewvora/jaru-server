package com.andrewvora.jaru.questionset

import com.andrewvora.jaru.DevProfile
import com.andrewvora.jaru.answers.Answer
import com.andrewvora.jaru.questions.Question
import com.andrewvora.jaru.questionsets.QuestionSet
import com.andrewvora.jaru.questionsets.QuestionSetRepository
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.junit4.SpringRunner

/**
 * Integration tests
 */
@RunWith(SpringRunner::class)
@DevProfile
@DataJpaTest
class QuestionSetRepositoryTest {

	@Autowired
	private lateinit var questionSetRepository: QuestionSetRepository

	@Autowired
	private lateinit var entityManager: TestEntityManager

	@Test
	fun `adding a question set with questions and answers will persist everything`() {
		// given
		val answer = Answer(answerId = "answer1", textResName = "answerText1")
		val question = Question(
				questionId = "question1",
				questionType = Question.QuestionType.SINGLE_INPUT,
				textResName = "questionText1",
				answers = mutableListOf(answer))
		val questionSet = QuestionSet(
				setId = "set1",
				difficulty = QuestionSet.Difficulty.ADVANCED,
				titleResourceName = "titleText1",
				descriptionResourceName = "descriptionText1",
				questions = mutableListOf(question))

		// when
		questionSetRepository.save(questionSet)

		// then
		assertEquals(answer, entityManager.find(Answer::class.java, answer.answerId))
		assertEquals(question, entityManager.find(Question::class.java, question.questionId))
		assertEquals(questionSet, entityManager.find(QuestionSet::class.java, questionSet.setId))
	}
}