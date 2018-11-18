package com.andrewvora.jaru.textresources

import com.andrewvora.jaru.exceptions.BadRequestException
import com.andrewvora.jaru.exceptions.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1")
class TextResourceController
@Autowired
constructor(private val textResourceRepository: TextResourceRepository) {

	@GetMapping("/text/{resName}/{locale}")
	@ResponseBody
	fun get(@PathVariable("resName") resId: String?,
			@PathVariable("locale") locale: String?): TextResource {
		if (resId == null || locale == null) {
			throw BadRequestException()
		}

		val result = textResourceRepository.find(resId, locale)
		return if (result.isPresent) {
			result.get()
		} else {
			throw NotFoundException()
		}
	}

	@PostMapping("/text")
	@ResponseBody
	fun post(@RequestBody textResource: TextResource?): TextResource {
		if (textResource == null) {
			throw BadRequestException()
		}

		return textResourceRepository.save(textResource)
	}

	@DeleteMapping("/text/{resName}/{locale}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	fun delete(@PathVariable("resName") name: String?,
			   @PathVariable("locale") locale: String?) {
		if (name == null || locale == null) {
			throw BadRequestException()
		}

		textResourceRepository.delete(name, locale)
	}
}