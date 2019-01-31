package com.andrewvora.jaru.glossary

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*
import javax.persistence.*

@Entity(name = "glossary")
data class Glossary(
		@JsonIgnore
		@GeneratedValue(strategy = GenerationType.AUTO)
		val id: Long = 0,
		@Id
		@Column(name = "glossary_id", unique = true)
		val glossaryId: String = UUID.randomUUID().toString(),
		@Column(name = "text_id")
		val textId: String = "",
		@Column(name = "subtitle_id")
		val subtitleId: String = "",
		@OneToMany(cascade = [CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE])
		val glossaryItems: MutableList<GlossaryItem> = mutableListOf()
)