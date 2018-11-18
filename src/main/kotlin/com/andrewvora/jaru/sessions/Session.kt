package com.andrewvora.jaru.sessions

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.OffsetDateTime
import java.util.*
import javax.persistence.*

@Entity(name = "session")
data class Session(
		@JsonIgnore
		@GeneratedValue(strategy = GenerationType.AUTO)
		val id: Long = 0,
		@Id
		@Column(name = "session_id", unique = true)
		val sessionId: String = UUID.randomUUID().toString(),
		@Column(name = "sequence_id")
		val sequenceId: Long = 0,
		@Column(name = "start_time")
		val startTime: OffsetDateTime = OffsetDateTime.now())