package com.andrewvora.jaru.answers

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.util.*

interface AnswerRepository : CrudRepository<Answer, Int> {

	@Query("SELECT a FROM answer a WHERE a.answerId = :answerId")
	fun find(@Param("answerId") answerId: String): Optional<Answer>
}