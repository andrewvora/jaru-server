package com.andrewvora.jaru.textresources

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * [TextResourceValidator]
 */
@RunWith(JUnit4::class)
class TextResourceValidatorTest {

	private val validator = TextResourceValidator()

	@Test
	fun `can catch bad locales`() {
		val textResource = TextResource(resourceName = "text1", text = "this is text1")

		// null or empty
		assertFalse(validator.isValid(textResource.copy(localeId = "")))
		assertFalse(validator.isValid(textResource.copy(localeId = "\t\n")))
		assertFalse(validator.isValid(textResource.copy(localeId = " ")))

		// malformed locales
		assertFalse(validator.isValid(textResource.copy(localeId = "enn-USR")))
		assertFalse(validator.isValid(textResource.copy(localeId = "enn-US")))
		assertFalse(validator.isValid(textResource.copy(localeId = "coo_US")))
		assertFalse(validator.isValid(textResource.copy(localeId = "zebra")))
		assertFalse(validator.isValid(textResource.copy(localeId = "en")))
	}

	@Test
	fun `validates real locales`() {
		val textResource = TextResource(resourceName = "text1", text = "this is text1")

		assertTrue(validator.isValid(textResource.copy(localeId = "en-US")))
		assertTrue(validator.isValid(textResource.copy(localeId = "en-mx")))
		assertTrue(validator.isValid(textResource.copy(localeId = "ch-CH")))
	}

	@Test
	fun `validates required fields`() {
		val textResource = TextResource(resourceName = "text1", text = "this is text1", localeId = "en-US")

		assertFalse(validator.isValid(textResource.copy(resourceName = "")))
		assertFalse(validator.isValid(textResource.copy(resourceName = " \n")))
		assertFalse(validator.isValid(textResource.copy(text = "")))
		assertFalse(validator.isValid(textResource.copy(text = " \n\t")))
	}
}