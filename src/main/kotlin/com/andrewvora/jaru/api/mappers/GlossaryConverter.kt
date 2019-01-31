package com.andrewvora.jaru.api.mappers

import com.andrewvora.jaru.api.models.GlossaryDto
import com.andrewvora.jaru.api.models.GlossaryItemDto
import com.andrewvora.jaru.glossary.Glossary
import com.andrewvora.jaru.glossary.GlossaryItem
import com.andrewvora.jaru.textresources.TextResourceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class GlossaryConverter
@Autowired
constructor(
		private val textResourceRepository: TextResourceRepository
) {

	fun toDto(glossary: Glossary, locale: String = "en-US"): GlossaryDto {
		val titleResource = glossary.titleId
		val titleResult = textResourceRepository.find(titleResource, locale)
		val title = if (titleResult.isPresent) {
			titleResult.get().firstOrNull()?.text ?: ""
		} else {
			""
		}

		val subtitleResource = glossary.subtitleId
		val subtitleResult = textResourceRepository.find(subtitleResource, locale)
		val subtitle = if (subtitleResult.isPresent) {
			subtitleResult.get().firstOrNull()?.text ?: ""
		} else {
			""
		}

		val items = glossary.glossaryItems.map { toDto(it, locale) }
		return GlossaryDto(
				glossaryId = glossary.glossaryId,
				title = title,
				subtitle = subtitle,
				glossaryItems = items
		)
	}

	private fun toDto(glossaryItem: GlossaryItem, locale: String): GlossaryItemDto {
		val textResource = glossaryItem.textId
		val textResult = textResourceRepository.find(textResource, locale)
		val text = if (textResult.isPresent) {
			textResult.get().firstOrNull()?.text ?: ""
		} else { "" }

		val transcriptResource = glossaryItem.transcriptId
		val transcriptResult = textResourceRepository.find(transcriptResource, locale)
		val transcript = if (transcriptResult.isPresent) {
			transcriptResult.get().firstOrNull()?.text ?: ""
		} else { "" }

		return GlossaryItemDto(
				itemId = glossaryItem.itemId,
				text = text,
				transcript = transcript,
				order = glossaryItem.order
		)
	}
}