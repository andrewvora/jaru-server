package com.andrewvora.jaru.api.mappers

import com.andrewvora.jaru.answers.Answer
import com.andrewvora.jaru.api.models.AnswerDto
import com.andrewvora.jaru.textresources.TextResourceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component

@Component
class AnswerConverter
@Autowired
constructor(private val textResourceRepository: TextResourceRepository) {

	fun toDto(answer: Answer, locale: String = "en-US"): AnswerDto {
		val textResourceName = answer.textResName
		val textResult = textResourceRepository.find(textResourceName, locale)
		val text = if (textResult.isPresent) {
			textResult.get().firstOrNull()?.text ?: ""
		} else {
			""
		}

		return AnswerDto(
				id = answer.answerId,
				text = text
		)
	}
}