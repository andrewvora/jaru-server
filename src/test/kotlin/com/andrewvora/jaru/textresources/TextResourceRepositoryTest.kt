package com.andrewvora.jaru.textresources

import com.andrewvora.jaru.DevProfile
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@DevProfile
@DataJpaTest
class TextResourceRepositoryTest {

	@Autowired
	private lateinit var entityManager: TestEntityManager

	@Autowired
	private lateinit var repository: TextResourceRepository

	@Test
	fun `can find resources based on name and locale`() {
		// given
		val resource1 = TextResource(resourceName = "text1", text = "this is text1", localeId = "en-US")
		val resource2 = resource1.copy(localeId = "en-MX")
		entityManager.persist(resource1)
		entityManager.persist(resource2)

		// when
		val retrievedResource = repository.find("text1", "en-US").get().first()

		// then
		assertEquals(resource1.resourceName, retrievedResource.resourceName)
		assertEquals(resource1.localeId, retrievedResource.localeId)
		assertEquals(resource1.text, retrievedResource.text)
		assertNotEquals(resource2.localeId, retrievedResource.localeId)
	}

	@Test
	fun `successfully deletes resources based on locale`() {
		// given
		val resource1 = TextResource(resourceName = "text1", text = "this is text1", localeId = "en-US")
		val resource2 = resource1.copy(localeId = "en-MX")
		entityManager.persist(resource1)
		entityManager.persist(resource2)

		// when
		repository.delete("text1", "en-US")
		val remainingResource = repository.find("text1", "en-MX").get().first()

		// then
		assertEquals(resource2.localeId, remainingResource.localeId)
		assertEquals(resource2.resourceName, remainingResource.resourceName)
		assertEquals(resource2.text, remainingResource.text)

		assertFalse(repository.find("text1", "en-US").isPresent)
	}
}