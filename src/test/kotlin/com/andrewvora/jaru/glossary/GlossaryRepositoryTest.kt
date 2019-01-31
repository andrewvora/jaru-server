package com.andrewvora.jaru.glossary

import com.andrewvora.jaru.DevProfile
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.junit4.SpringRunner

/**
 * [GlossaryItemRepository]
 * [GlossaryRepository]
 */
@RunWith(SpringRunner::class)
@DevProfile
@DataJpaTest
class GlossaryRepositoryTest {

	@Autowired
	private lateinit var glossaryRepository: GlossaryRepository

	@Autowired
	private lateinit var entityManager: TestEntityManager

	@Test
	fun `adding glossary items persists them`() {
		// given
		val item1 = GlossaryItem(itemId = "item1", order = 1)
		val item2 = item1.copy(itemId = "item2", order = 2)
		val glossary = Glossary(glossaryId = "glossary1", glossaryItems = mutableListOf(item1, item2))

		// when
		glossaryRepository.save(glossary)

		// then
		assertEquals(glossary, entityManager.find(Glossary::class.java, glossary.glossaryId))
		assertEquals(item1, entityManager.find(GlossaryItem::class.java, item1.itemId))
		assertEquals(item2, entityManager.find(GlossaryItem::class.java, item2.itemId))
	}

	@Test
	fun `updating a glossary will replace the items`() {
		// given
		val item1 = GlossaryItem(itemId = "item1", order = 1)
		val item2 = item1.copy(itemId = "item2", order = 2)
		val glossary = Glossary(glossaryId = "glossary1", glossaryItems = mutableListOf(item1, item2))
		val emptyGlossary = glossary.copy(glossaryItems = mutableListOf())

		// when
		glossaryRepository.save(glossary)
		assertTrue(entityManager.find(Glossary::class.java, glossary.glossaryId).glossaryItems.isNotEmpty())

		glossaryRepository.save(emptyGlossary)

		// then
		assertTrue(entityManager.find(Glossary::class.java, glossary.glossaryId).glossaryItems.isEmpty())
	}

	@Test
	fun `deleting glossary deletes the glossary items`() {
		// given
		val item1 = GlossaryItem(itemId = "item1", order = 1)
		val item2 = item1.copy(itemId = "item2", order = 2)
		val glossary = Glossary(glossaryId = "glossary1", glossaryItems = mutableListOf(item1, item2))

		// when
		glossaryRepository.save(glossary)
		assertEquals(item1, entityManager.find(GlossaryItem::class.java, item1.itemId))
		assertEquals(item2, entityManager.find(GlossaryItem::class.java, item2.itemId))

		glossaryRepository.deleteById(glossary.glossaryId)

		// then
		assertNull(entityManager.find(GlossaryItem::class.java, item1.itemId))
		assertNull(entityManager.find(GlossaryItem::class.java, item2.itemId))
	}
}