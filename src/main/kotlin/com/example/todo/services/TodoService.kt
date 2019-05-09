package com.example.todo.services

import com.example.todo.dtos.todo.GetTodoItemDto

interface TodoService {
    fun findAll() : Set<GetTodoItemDto>
    fun getById(id: Long) : GetTodoItemDto?
}