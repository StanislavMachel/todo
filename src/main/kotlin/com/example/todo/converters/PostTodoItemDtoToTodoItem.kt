package com.example.todo.converters

import com.example.todo.dtos.todo.PostTodoItemDto
import com.example.todo.model.TodoItem
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class PostTodoItemDtoToTodoItem : Converter<PostTodoItemDto, TodoItem> {
    @Synchronized
    override fun convert(source: PostTodoItemDto): TodoItem {
        return TodoItem(source.name)
    }
}