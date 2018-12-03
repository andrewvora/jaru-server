package com.andrewvora.jaru.questionsets

import org.springframework.stereotype.Component

/**
 * [QuestionSet]
 */
@Component
class QuestionSetValidator {

	fun isValid(set: QuestionSet): Boolean {
		return set.setId.isNotEmpty() && set.descriptionResourceName.isNotEmpty() &&
				set.titleResourceName.isNotEmpty()
	}
}