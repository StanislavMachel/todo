package com.example.todo.dtos.todo

data class GetTodoItemDto(val id: Long, val name: String, val isComplete: Boolean)