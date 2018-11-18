package com.andrewvora.jaru.questionsets

import com.andrewvora.jaru.questions.Question
import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*
import javax.persistence.*

@Entity(name = "question_set")
data class QuestionSet(
		@JsonIgnore
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		val id: Long = 0,
		@Column(name = "set_id", unique = true)
		val setId: String = UUID.randomUUID().toString(),
		@Column(name = "difficulty")
		val difficulty: Int = 0,
		@Column(name = "title_id")
		val titleResourceId: String = "",
		@Column(name = "description_id")
		val descriptionResourceId: String = "",
		@ManyToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
		val questions: MutableList<Question> = mutableListOf()
)