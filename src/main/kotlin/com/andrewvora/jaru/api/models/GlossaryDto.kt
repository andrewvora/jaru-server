package com.andrewvora.jaru.api.models

import com.fasterxml.jackson.annotation.JsonProperty

data class GlossaryDto(
		@JsonProperty("id") val glossaryId: String = "",
		@JsonProperty("title") val title: String = "",
		@JsonProperty("subtitle") val subtitle: String = "",
		@JsonProperty("items") val glossaryItems: List<GlossaryItemDto> = listOf()
)