package com.andrewvora.jaru.api.mappers

import com.andrewvora.jaru.api.models.QuestionSetDto
import com.andrewvora.jaru.questionsets.QuestionSet
import com.andrewvora.jaru.textresources.TextResourceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class QuestionSetConverter
@Autowired
constructor(private val questionConverter: QuestionConverter,
			private val textResourceRepository: TextResourceRepository) {

	fun toDto(questionSet: QuestionSet, locale: String = "en-US"): QuestionSetDto {
		val titleResource = questionSet.titleResourceName
		val titleTextResult = textResourceRepository.find(titleResource, locale)
		val titleText = if (titleTextResult.isPresent) {
			titleTextResult.get().firstOrNull()?.text ?: ""
		} else {
			""
		}
		val descriptionResource = questionSet.descriptionResourceName
		val descriptionResult = textResourceRepository.find(descriptionResource, locale)
		val description = if (descriptionResult.isPresent) {
			descriptionResult.get().firstOrNull()?.text ?: ""
		} else {
			""
		}
		val questions = questionSet.questions.map { questionConverter.toDto(it, locale) }
		return QuestionSetDto(
				id = questionSet.setId,
				difficulty = questionSet.difficulty,
				title = titleText,
				description = description,
				questions = questions
		)
	}
}