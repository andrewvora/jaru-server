package com.andrewvora.jaru.glossary

import org.springframework.data.repository.CrudRepository

interface GlossaryItemRepository : CrudRepository<GlossaryItem, String>
interface GlossaryRepository : CrudRepository<Glossary, String>