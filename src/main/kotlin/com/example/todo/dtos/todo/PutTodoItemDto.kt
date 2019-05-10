package com.example.todo.dtos.todo

import com.fasterxml.jackson.annotation.JsonProperty

data class PutTodoItemDto(val name: String?, @JsonProperty("complete") val isComplete: Boolean?)