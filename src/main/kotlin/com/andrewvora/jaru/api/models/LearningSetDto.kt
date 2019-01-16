package com.andrewvora.jaru.api.models

import org.codehaus.jackson.annotate.JsonProperty

data class LearningSetDto(
		@JsonProperty("sets") val questionSets: MutableList<QuestionSetDto> = mutableListOf()
)