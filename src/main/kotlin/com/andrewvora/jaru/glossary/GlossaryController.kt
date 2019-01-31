package com.andrewvora.jaru.glossary

import com.andrewvora.jaru.exceptions.BadRequestException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/content/v1")
class GlossaryController
@Autowired
constructor(
		private val glossaryRepository: GlossaryRepository
) {
	@GetMapping("/glossary/all")
	@ResponseBody
	fun getAll(): List<Glossary> {
		return glossaryRepository.findAll().toList()
	}

	@PutMapping("/glossary/{glossaryId}")
	@ResponseBody
	fun update(@PathVariable("glossaryId") id: String?,
			   @RequestBody glossary: Glossary?): Glossary {

		if (id.isNullOrBlank() || glossary == null) {
			throw BadRequestException()
		}

		val safeGlossary = glossary.copy(glossaryId = id)
		return glossaryRepository.save(safeGlossary)
	}

	@PostMapping("/glossary")
	@ResponseBody
	fun add(@RequestBody glossary: Glossary?): Glossary {
		if (glossary == null) {
			throw BadRequestException()
		}

		return glossaryRepository.save(glossary)
	}

	@DeleteMapping("/glossary/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	fun delete(@PathVariable("id") id: String?) {
		if (id.isNullOrBlank()) {
			throw BadRequestException()
		}

		glossaryRepository.deleteById(id)
	}

}