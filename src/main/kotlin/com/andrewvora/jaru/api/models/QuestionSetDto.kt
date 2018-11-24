package com.andrewvora.jaru.api.models

import com.andrewvora.jaru.questionsets.QuestionSet
import com.fasterxml.jackson.annotation.JsonProperty

data class QuestionSetDto(
		@JsonProperty("id") val id: String,
		@JsonProperty("difficulty") val difficulty: QuestionSet.Difficulty,
		@JsonProperty("title") val title: String,
		@JsonProperty("description") val description: String,
		@JsonProperty("questions") val questions: List<QuestionDto>
)