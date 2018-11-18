package com.andrewvora.jaru.textresources

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity(name = "text_resource")
data class TextResource(
		@JsonIgnore
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		val id: Long = 0,
		@Column(name = "resource_name")
		val resourceName: String = "",
		@Column(name = "text")
		val text: String = "",
		@Column(name = "locale_id")
		val localeId: String = ""
)