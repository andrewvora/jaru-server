package com.andrewvora.jaru.api

import com.andrewvora.jaru.api.mappers.QuestionSetConverter
import com.andrewvora.jaru.api.models.LearningSetDto
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
			private val questionSetConverter: QuestionSetConverter) {

	@GetMapping("/v1/sets/all")
	@ResponseBody
	fun fetchQuestionSets(@PathParam("locale") locale: String?): LearningSetDto {
		return questionSetRepository.findAll()
				.asSequence()
				.toList()
				.map {
					questionSetConverter.toDto(it, locale ?: "en-US")
				}
				.let {
					return@let LearningSetDto(
							questionSets = it.toMutableList()
					);
				}
	}
}