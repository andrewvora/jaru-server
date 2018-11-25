package com.andrewvora.jaru.textresources

import org.springframework.stereotype.Component
import java.util.*

@Component
class TextResourceValidator {
	fun isValid(textResource: TextResource): Boolean {
		// process locale
		val locale = Locale(textResource.localeId)
		val languageParts = textResource.localeId.split("-", "_")

		// check required fields are set
		// locale must have language in "xx-xx" format
		return (languageParts.size == 2 && locale.language.length == 5) &&
				locale.language.isNotBlank() &&
				textResource.resourceName.isNotBlank() &&
				textResource.text.isNotBlank()
	}
}