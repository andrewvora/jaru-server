package com.andrewvora.jaru.answers

import com.andrewvora.jaru.questions.Question
import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*
import javax.persistence.*

@Entity(name = "answer")
data class Answer(
		@JsonIgnore
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		val id: Long = 0,
		@Column(name = "answer_id", unique = true)
		val answerId: String = UUID.randomUUID().toString(),
		@Column(name = "text_id")
		val textResId: String = "",
		@JsonIgnore
		@ManyToMany(
				fetch = FetchType.EAGER,
				cascade = [CascadeType.PERSIST, CascadeType.MERGE])
		@JoinTable(
				name = "question_answer",
				joinColumns = [ JoinColumn(name = "answer_answer_id") ],
				inverseJoinColumns = [ JoinColumn(name = "question_question_id") ]
		)
		var questions: MutableList<Question> = mutableListOf()
)