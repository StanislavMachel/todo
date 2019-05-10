package com.example.todo.services

import com.example.todo.dtos.todo.GetTodoItemDto
import com.example.todo.dtos.todo.PostTodoItemDto
import com.example.todo.dtos.todo.PutTodoItemDto

interface TodoService {
    fun getAll() : Set<GetTodoItemDto>
    fun getById(id: Long) : GetTodoItemDto?
    fun update(id: Long, putTodoItemDto: PutTodoItemDto) : GetTodoItemDto
    fun createNewTodo(postTodoItemDto: PostTodoItemDto) : GetTodoItemDto
    fun deleteById(id: Long)
}