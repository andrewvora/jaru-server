package com.andrewvora.jaru.api

import com.andrewvora.jaru.api.mappers.GlossaryConverter
import com.andrewvora.jaru.api.mappers.QuestionSetConverter
import com.andrewvora.jaru.api.models.GlossaryDto
import com.andrewvora.jaru.api.models.LearningSetDto
import com.andrewvora.jaru.api.models.QuestionSetDto
import com.andrewvora.jaru.glossary.GlossaryRepository
import com.andrewvora.jaru.questionsets.QuestionSetRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import javax.websocket.server.PathParam

@RestController
@RequestMapping("/api")
class ApiContentController
@Autowired
constructor(private val questionSetRepository: QuestionSetRepository,
			private val questionSetConverter: QuestionSetConverter,
			private val glossaryRepository: GlossaryRepository,
			private val glossaryConverter: GlossaryConverter) {

	/**
	 * Fetches a complete data set for mobile apps and SPAs
	 */
	@GetMapping("/v1/full")
	@ResponseBody
	fun fetchAllContentForLocale(@PathParam("locale") locale: String?): LearningSetDto {
		val questionSets = try {
			questionSetRepository.findAll()
					.map {
						questionSetConverter.toDto(it, locale ?: "en-US")
					}
		} catch (e: Exception) {
			emptyList<QuestionSetDto>()
		}
		val glossaries = try {
			glossaryRepository.findAll()
					.map {
						glossaryConverter.toDto(it, locale ?: "en-US")
					}
		} catch (e: Exception) {
			emptyList<GlossaryDto>()
		}

		return LearningSetDto(
				questionSets = questionSets.toMutableList(),
				glossary = glossaries.toMutableList()
		)
	}
}