package com.andrewvora.jaru.questionsets

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.util.*

interface QuestionSetRepository : CrudRepository<QuestionSet, Int> {

	@Query("SELECT qs FROM question_set qs WHERE qs.setId = :id")
	fun find(@Param("id") id: String): Optional<QuestionSet>
}