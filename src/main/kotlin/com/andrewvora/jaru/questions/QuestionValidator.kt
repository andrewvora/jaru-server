package com.andrewvora.jaru.questions

import com.andrewvora.jaru.answers.AnswerValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class QuestionValidator
@Autowired
constructor(private val answerValidator: AnswerValidator) {

	fun canBeInserted(question: Question): Boolean {
		question.answers.forEach {
			if (!answerValidator.canBeInserted(it)) {
				return false
			}
		}

		return question.textResName.isNotEmpty() &&
				question.transcriptionResName.isNotEmpty() &&
				question.questionId.isNotEmpty()
	}
}