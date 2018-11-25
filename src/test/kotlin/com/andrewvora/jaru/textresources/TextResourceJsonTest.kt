package com.andrewvora.jaru.textresources

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * [TextResource]
 */
@RunWith(JUnit4::class)
class TextResourceJsonTest {

	private val objectMapper = ObjectMapper()

	@Test
	fun `ignores database ID when serializing`() {
		// given
		val textResource = TextResource(
				id = 5,
				resourceName = "text1",
				text = "this is text1",
				localeId = "en-US"
		)

		// when
		val json = objectMapper.writeValueAsString(textResource)
		val deserializedResource = objectMapper.readValue(json, TextResource::class.java)

		// then
		assertNotEquals(textResource.id, deserializedResource.id)
		assertEquals(textResource.resourceName, deserializedResource.resourceName)
		assertEquals(textResource.text, deserializedResource.text)
		assertEquals(textResource.localeId, deserializedResource.localeId)
	}
}