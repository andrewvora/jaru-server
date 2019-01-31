package com.andrewvora.jaru.api.models

import com.fasterxml.jackson.annotation.JsonProperty

data class LearningSetDto(
		@JsonProperty("glossary") val glossary: MutableList<GlossaryDto> = mutableListOf(),
		@JsonProperty("sets") val questionSets: MutableList<QuestionSetDto> = mutableListOf()
)