package com.andrewvora.jaru.api.models

import com.fasterxml.jackson.annotation.JsonProperty

data class GlossaryItemDto(
		@JsonProperty("id") val itemId: String = "",
		@JsonProperty("text") val text: String = "",
		@JsonProperty("transcript") val transcript: String = "",
		@JsonProperty("order") val order: Int = 0
)