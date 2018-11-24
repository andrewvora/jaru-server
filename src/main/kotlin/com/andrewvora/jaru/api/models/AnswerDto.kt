package com.andrewvora.jaru.api.models

import com.fasterxml.jackson.annotation.JsonProperty

data class AnswerDto(
		@JsonProperty("id") val id: String,
		@JsonProperty("text") val text: String
)