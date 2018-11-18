package com.andrewvora.jaru.answers

import com.andrewvora.jaru.questions.Question
import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*
import javax.persistence.*

@Entity(name = "answer")
data class Answer(
		@JsonIgnore
		@GeneratedValue(strategy = GenerationType.AUTO)
		val id: Long = 0,
		@Id
		@Column(name = "answer_id", unique = true)
		val answerId: String = UUID.randomUUID().toString(),
		@Column(name = "text_res_name")
		val textResName: String = "",
		@JsonIgnore
		@ManyToMany(
				fetch = FetchType.EAGER,
				cascade = [CascadeType.PERSIST, CascadeType.MERGE])
		@JoinTable(
				name = "question_answers",
				joinColumns = [ JoinColumn(name = "question_question_id") ],
				inverseJoinColumns = [ JoinColumn(name = "answers_answer_id") ]
		)
		var questions: MutableList<Question> = mutableListOf()
)