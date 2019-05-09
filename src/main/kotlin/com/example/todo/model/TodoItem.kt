package com.example.todo.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class TodoItem @JsonCreator constructor(
        @Id
        @GeneratedValue
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        var id: Long,
        var name: String,
        var isComplete: Boolean){
    constructor(name:String) : this(0, name, false)
}