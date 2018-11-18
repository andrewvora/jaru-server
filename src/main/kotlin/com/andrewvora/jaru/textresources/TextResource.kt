package com.andrewvora.jaru.textresources

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*
import javax.persistence.*

/**
 * [TextResource] is meant to be queried based on the resource name and the locale.
 * The ID isn't as important.
 */
@Entity(name = "text_resource")
data class TextResource(
		@Id
		@JsonIgnore
		@GeneratedValue(strategy = GenerationType.AUTO)
		val id: Long = 0,
		@Column(name = "resource_name")
		val resourceName: String = "",
		@Column(name = "text")
		val text: String = "",
		@Column(name = "locale_id")
		val localeId: String = ""
)