package com.example.todo.converters

import com.example.todo.dtos.todo.GetTodoItemDto
import com.example.todo.model.TodoItem
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class GetTodoItemDtoToTodoItem : Converter<GetTodoItemDto, TodoItem> {
    @Synchronized
    override fun convert(source: GetTodoItemDto): TodoItem? {
        return TodoItem(source.id, source.name, source.isComplete)
    }

}