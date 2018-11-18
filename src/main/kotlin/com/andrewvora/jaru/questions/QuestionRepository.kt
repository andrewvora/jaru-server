package com.andrewvora.jaru.questions

import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional
import java.util.*

interface QuestionRepository : CrudRepository<Question, Int> {

	@Query("SELECT q FROM question q WHERE questionId = :id")
	fun find(@Param("id") id: String): Optional<Question>

	@Modifying
	@Transactional
	@Query("DELETE FROM question q WHERE questionId = :id")
	fun delete(@Param("id") id: String)
}