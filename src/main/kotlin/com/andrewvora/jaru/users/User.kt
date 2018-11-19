package com.andrewvora.jaru.users

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.OffsetDateTime
import javax.persistence.*

@Entity(name = "user")
data class User(
		@JsonIgnore
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		val id: Long = 0,
		@Column(name = "email", unique = true)
		val email: String = "",
		@Column(name = "name")
		val name: String = "",
		@Column(name = "joined")
		val joined: OffsetDateTime = OffsetDateTime.now()
)