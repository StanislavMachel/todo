package com.example.todo.converters

import com.example.todo.dtos.todo.GetTodoItemDto
import com.example.todo.model.TodoItem
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class TodoItemToGetTodoItemDto : Converter<TodoItem?, GetTodoItemDto?> {

    @Synchronized
    override fun convert(source: TodoItem): GetTodoItemDto {
        return GetTodoItemDto(source.id, source.name, source.isComplete)
    }

}