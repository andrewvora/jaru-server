package com.andrewvora.jaru.api.models

import com.andrewvora.jaru.questions.Question
import com.fasterxml.jackson.annotation.JsonProperty

data class QuestionDto(
		@JsonProperty("id") val id: String,
		@JsonProperty("text") val text: String,
		@JsonProperty("transcription") val transcript: String,
		@JsonProperty("correctAnswerId") val answerId: String,
		@JsonProperty("type") val type: Question.QuestionType,
		@JsonProperty("answers") val answers: List<AnswerDto>
)