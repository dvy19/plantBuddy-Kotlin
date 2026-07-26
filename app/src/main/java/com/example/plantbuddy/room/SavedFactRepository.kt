package com.example.plantbuddy.room

class SavedFactRepository(
    private val dao: FactDao
) {

    val savedFacts = dao.getAllFacts()

    suspend fun saveFact(fact: SavedFact) {
        dao.insertFact(fact)
    }

    suspend fun deleteFact(fact: SavedFact) {
        dao.deleteFact(fact)
    }

    suspend fun isSaved(title: String, content: String) =
        dao.isSaved(title, content)
}