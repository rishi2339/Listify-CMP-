package com.cmp.todo.database

class DBHelper(private val driverFactory: DriverFactory) {
    private var db:ToDoDatabase?=null
    suspend operator fun <R> invoke(block: suspend (ToDoDatabase)-> R): R {
        val database = db ?: ToDoDatabase.invoke(driverFactory.createDriver()).also { db = it }
        return block(database)
    }
}