package com.andrewvora.jaru.questionsets

import com.andrewvora.jaru.questions.QuestionValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * [QuestionSet]
 */
@Component
class QuestionSetValidator
@Autowired
constructor(private val questionValidator: QuestionValidator) {

	fun canBeInserted(set: QuestionSet): Boolean {
		set.questions.forEach {
			if (!questionValidator.canBeInserted(it)) {
				return false
			}
		}
		return set.setId.isNotEmpty() && set.descriptionResourceName.isNotEmpty() &&
				set.titleResourceName.isNotEmpty()
	}
}