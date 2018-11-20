package com.andrewvora.jaru.api

import com.andrewvora.jaru.api.models.LearningSetDto
import com.andrewvora.jaru.questionsets.QuestionSetRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class ApiContentController
@Autowired
constructor(private val questionSetRepository: QuestionSetRepository) {

	@GetMapping("/v1/content")
	@ResponseBody
	fun fetchQuestionSets(): LearningSetDto {
		return questionSetRepository.findAll()
				.asSequence()
				.toList()
				.let {
					val learningSetDto = LearningSetDto()
					return@let learningSetDto;
				}
	}

}