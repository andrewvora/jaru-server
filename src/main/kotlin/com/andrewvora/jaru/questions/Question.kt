package com.andrewvora.jaru.questions

import com.andrewvora.jaru.answers.Answer
import com.andrewvora.jaru.questionsets.QuestionSet
import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*
import javax.persistence.*

@Entity(name = "question")
data class Question(
		@JsonIgnore
		@GeneratedValue(strategy = GenerationType.AUTO)
		val id: Long = 0,
		@Id
		@Column(name = "question_id", unique = true)
		val questionId: String = UUID.randomUUID().toString(),
		@Column(name = "text_id")
		val textResId: String = "",
		@Column(name = "transcription_id")
		val transcriptionResId: String = "",
		@JsonIgnore
		@ManyToMany(
				fetch = FetchType.EAGER,
				cascade = [CascadeType.PERSIST, CascadeType.MERGE])
		@JoinTable(
				name = "question_set_questions",
				joinColumns = [ JoinColumn(name = "question_set_set_id") ],
				inverseJoinColumns = [ JoinColumn(name = "questions_question_id") ]
		)
		val questionSets: MutableList<QuestionSet> = mutableListOf(),
		@ManyToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
		val answers: MutableList<Answer> = mutableListOf()
)