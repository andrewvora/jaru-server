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
		/**
		 * Questions with only one or multiple answers.
		 */
		@JsonProperty("single_input")
		SINGLE_INPUT,
		/**
		 * Questions with only one correct answer among many.
		 */
		@JsonProperty("multiple_choice")
		MULTIPLE_CHOICE,
		/**
		 * Questions that may or may not have a real answer.
		 * These aren't meant for practice and usually show guidance.
		 * ex. "Try to speak this sentence"
		 * ex. "How would you say this in Thai?" -> "Here's one way to say it: []"
		 */
		@JsonProperty("free_form")
		FREE_FORM,
		/**
		 * Question type isn't defined. Likely indicating invalid data.
		 */
		@JsonProperty("unknown")
		UNKNOWN
	}
}