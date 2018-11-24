package com.andrewvora.jaru.api.models

data class LearningSetDto(
		val questionSets: MutableList<QuestionSetDto> = mutableListOf()
)