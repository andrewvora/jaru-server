package com.andrewvora.jaru.textresources

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional
import java.util.*

interface TextResourceRepository : CrudRepository<TextResource, Int> {

	@Query("SELECT tr FROM text_resource tr WHERE resource_name = :name AND locale_id = :locale")
	fun find(@Param("name") resourceName: String,
			 @Param("locale") locale: String,
			 pageable: Pageable = PageRequest.of(0, 1)): Optional<List<TextResource>>

	@Transactional
	@Modifying
	@Query("DELETE FROM text_resource WHERE resource_name = :name AND locale_id = :locale")
	fun delete(@Param("name") resourceName: String,
			   @Param("locale") locale: String)
}