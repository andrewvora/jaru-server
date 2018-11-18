package com.andrewvora.jaru.sessions

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional
import java.util.*
import javax.websocket.server.PathParam

interface SessionRepository : CrudRepository<Session, Int> {

	@Query("SELECT s FROM session s WHERE s.sessionId = :sessionId")
	fun find(@PathParam("sessionId") sessionId: String?): Optional<Session>

	@Query("SELECT s FROM session s")
	fun retrieveRecentSessions(pageable: Pageable = PageRequest.of(0, 100)): Optional<List<Session>?>

	@Query("SELECT MAX(s.sequenceId) FROM session s")
	fun currentSequenceId(): Optional<Long?>

	@Modifying
	@Transactional
	@Query("DELETE FROM session s WHERE s.sessionId = :sessionId")
	fun deleteSession(@Param("sessionId") sessionId: String)
}