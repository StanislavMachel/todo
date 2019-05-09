package com.example.todo.repositories

import com.example.todo.model.TodoItem
import org.springframework.data.repository.CrudRepository


interface TodoItemRepository : CrudRepository<TodoItem, Long>