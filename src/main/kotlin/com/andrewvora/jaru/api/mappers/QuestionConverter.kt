package com.andrewvora.jaru.api.mappers

import com.andrewvora.jaru.api.models.QuestionDto
import com.andrewvora.jaru.questions.Question
import com.andrewvora.jaru.textresources.TextResourceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class QuestionConverter
@Autowired
constructor(private val answerConverter: AnswerConverter,
			private val textResourceRepository: TextResourceRepository) {

	fun toDto(question: Question, locale: String = "en-US"): QuestionDto {
		val transcriptionRes = question.transcriptionResName
		val transcriptionResult = textResourceRepository.find(transcriptionRes, locale)
		val transcriptionText = if (transcriptionResult.isPresent) {
			transcriptionResult.get().firstOrNull()?.text ?: ""
		} else {
			""
		}
		val questionRes = question.textResName
		val questionResult = textResourceRepository.find(questionRes, locale)
		val questionText = if (questionResult.isPresent) {
			questionResult.get().firstOrNull()?.text ?: ""
		} else {
			""
		}

		val answerDtos = question.answers.map {
			answerConverter.toDto(it)
		}
		return QuestionDto(
				id = question.questionId,
				type = question.questionType,
				text = questionText,
				transcript = transcriptionText,
				answers = answerDtos
		)
	}
}