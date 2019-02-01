package com.andrewvora.jaru.questionsets

import com.andrewvora.jaru.questions.Question
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*
import javax.persistence.*

@Entity(name = "question_set")
data class QuestionSet(
		@JsonIgnore
		@GeneratedValue(strategy = GenerationType.AUTO)
		val id: Long = 0,
		@Id
		@Column(name = "set_id", unique = true)
		val setId: String = UUID.randomUUID().toString(),
		@Column(name = "difficulty")
		@Enumerated(EnumType.STRING)
		val difficulty: Difficulty = Difficulty.UNRATED,
		@Column(name = "title_res_name")
		val titleResourceName: String = "",
		@Column(name = "description_res_name")
		val descriptionResourceName: String = "",
		@Column(name = "display_order")
		val displayOrder: Int = 0,
		@ManyToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
		val questions: MutableList<Question> = mutableListOf()
) {
	enum class Difficulty {
		@JsonProperty("beginner")
		BEGINNER,
		@JsonProperty("intermediate")
		INTERMEDIATE,
		@JsonProperty("advanced")
		ADVANCED,
		@JsonProperty("unrated")
		UNRATED
	}
}