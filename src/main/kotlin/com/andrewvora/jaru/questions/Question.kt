package com.andrewvora.jaru.questions

import com.andrewvora.jaru.answers.Answer
import com.andrewvora.jaru.questionsets.QuestionSet
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
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
		@Column(name = "text_res_name")
		val textResName: String = "",
		@Column(name = "transcription_res_name")
		val transcriptionResName: String = "",
		@Column(name = "correct_answer_id")
		val correctAnswerId: String? = null,
		@Column(name = "question_type")
		@Enumerated(EnumType.STRING)
		val questionType: QuestionType = QuestionType.UNKNOWN,
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
) {
	enum class QuestionType {
		@JsonProperty("single_input")
		SINGLE_INPUT,
		@JsonProperty("multiple_choice")
		MULTIPLE_CHOICE,
		@JsonProperty("free_form")
		FREE_FORM,
		@JsonProperty("unknown")
		UNKNOWN
	}
}