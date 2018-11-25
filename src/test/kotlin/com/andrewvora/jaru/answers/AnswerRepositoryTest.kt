package com.andrewvora.jaru.answers


import com.andrewvora.jaru.DevProfile
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
class AnswerRepositoryTest {

	@Autowired
	private lateinit var entityManager: TestEntityManager

	@Autowired
	private lateinit var answerRepository: AnswerRepository

	@Test
	fun `successfully retrieves answers`() {
		// given
		val answer = Answer(answerId = "answer1", textResName = "answer1Text")
		entityManager.persist(answer)

		// when
		val retrievedAnswer = answerRepository.findById(answer.answerId).get()

		// then
		assertEquals(answer.textResName, retrievedAnswer.textResName)
		assertEquals(answer.answerId, retrievedAnswer.answerId)
	}

	@Test
	fun `successfully stores answers`() {
		// given
		val answer = Answer(answerId = "answer1", textResName = "answer1Text")

		// when
		val insertedAnswer = answerRepository.save(answer)
		val retrievedAnswer = answerRepository.findById(answer.answerId).get()

		// then
		assertEquals(insertedAnswer.answerId, retrievedAnswer.answerId)
		assertEquals(insertedAnswer.textResName, retrievedAnswer.textResName)
	}
}