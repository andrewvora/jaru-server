package com.andrewvora.jaru.glossary

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*
import javax.persistence.*

@Entity(name = "glossary_item")
data class GlossaryItem(
		@JsonIgnore
		@GeneratedValue(strategy = GenerationType.AUTO)
		val id: Long = 0,
		@Id
		@Column(name = "glossary_item_id", unique = true)
		val itemId: String = UUID.randomUUID().toString(),
		@Column(name = "text_id")
		val textId: String = "",
		@Column(name = "transcript_id")
		val transcriptId: String = "",
		@Column(name = "display_order")
		val order: Int = 0,

		@JsonIgnore
		@ManyToOne(
				fetch = FetchType.EAGER,
				cascade = [CascadeType.PERSIST, CascadeType.MERGE]
		)
		val glossary: Glossary? = null
)