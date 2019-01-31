package com.andrewvora.jaru.questions

import org.springframework.data.repository.CrudRepository

interface QuestionRepository : CrudRepository<Question, String>