package com.andrewvora.jaru

import com.andrewvora.jaru.api.ApiContentController
import com.andrewvora.jaru.questionsets.QuestionSetController
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

/**
 * [JaruApplication]
 */
@RunWith(SpringRunner::class)
@SpringBootTest
class JaruApplicationTest {

	@Autowired
	private lateinit var apiController: ApiContentController
	@Autowired
	private lateinit var questionSetController: QuestionSetController

	@Test
	fun contextLoads() {
		assertThat(apiController).isNotNull
		assertThat(questionSetController).isNotNull
	}
}