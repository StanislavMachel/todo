package com.example.todo.services

import com.example.todo.converters.TodoItemToGetTodoItemDto
import com.example.todo.dtos.todo.GetTodoItemDto
import com.example.todo.model.TodoItem
import com.example.todo.repositories.TodoItemRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class TodoServiceImpl(private val todoItemRepository: TodoItemRepository, private val todoItemToGetTodoItemDto: TodoItemToGetTodoItemDto) : TodoService {
    override fun getById(id: Long) : GetTodoItemDto? {

        val todo = todoItemRepository.findById(id).orElse(null)

        if(todo != null){
            return todoItemToGetTodoItemDto.convert(todo)
        }
        return null
    }

    override fun findAll(): Set<GetTodoItemDto> {

        val todos = todoItemRepository.findAll()

        val getTodoItemDtos : List<GetTodoItemDto> = todos.map { todoItem: TodoItem -> todoItemToGetTodoItemDto.convert(todoItem) }

        return getTodoItemDtos.toSet()
    }

}