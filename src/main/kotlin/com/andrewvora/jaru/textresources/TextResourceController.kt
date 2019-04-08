package com.andrewvora.jaru.textresources

import com.andrewvora.jaru.exceptions.AlreadyExistsException
import com.andrewvora.jaru.exceptions.BadRequestException
import com.andrewvora.jaru.exceptions.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/content/v1")
class TextResourceController
@Autowired
constructor(private val textResourceRepository: TextResourceRepository,
			private val textResourceValidator: TextResourceValidator) {

	@GetMapping("/text/{resName}/{locale}")
	@ResponseBody
	fun get(@PathVariable("resName") resId: String?,
			@PathVariable("locale") locale: String?): TextResource {
		if (resId.isNullOrBlank() || locale.isNullOrBlank()) {
			throw BadRequestException()
		}

		val result = textResourceRepository.find(resId, locale)
		return if (result.isPresent) {
			result.get().firstOrNull() ?: throw NotFoundException()
		} else {
			throw NotFoundException()
		}
	}

	@PostMapping("/text")
	@ResponseBody
	fun post(@RequestBody textResource: TextResource?): TextResource {
		if (textResource == null || !textResourceValidator.isValid(textResource)) {
			throw BadRequestException()
		}

		if (textResourceRepository.exists(textResource.resourceName, textResource.localeId)) {
			throw AlreadyExistsException()
		}

		return textResourceRepository.save(textResource)
	}

	@PostMapping("/text/batch")
	@ResponseBody
	fun post(@RequestBody textResources: Array<TextResource>?): List<TextResource> {
		if (textResources == null) {
			throw BadRequestException()
		}

		textResources.forEach {
			if (textResourceRepository.exists(it.resourceName, it.localeId)) {
				throw AlreadyExistsException()
			}
		}

		return textResources
				.filterNot { it.text.isBlank() }
				.filter {
					textResourceRepository.save(it).id > 0
				}
	}

	@DeleteMapping("/text/{resName}/{locale}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	fun delete(@PathVariable("resName") name: String?,
			   @PathVariable("locale") locale: String?) {
		if (name.isNullOrBlank() || locale.isNullOrBlank()) {
			throw BadRequestException()
		}

		textResourceRepository.delete(name, locale)
	}
}