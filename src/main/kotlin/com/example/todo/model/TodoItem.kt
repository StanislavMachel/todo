package com.example.todo.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class TodoItem constructor(
        @Id
        @GeneratedValue
        var id: Long,
        var name: String,
        var isComplete: Boolean){
    constructor(): this(0, "", false)
    constructor(name:String) : this(0, name, false)
}