package com.example.todo.converters

import com.example.todo.dtos.todo.GetTodoItemDto
import com.example.todo.model.TodoItem
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetTotoItemToTodoItemTest {
    private lateinit var conveter: GetTodoItemDtoToTodoItem

    @Before
    fun sutUp(){
        conveter = GetTodoItemDtoToTodoItem()
    }

    @Test
    fun convert(){
        val getTodoItemDto : GetTodoItemDto = GetTodoItemDto(1, "Test", false)

        val todoItem: TodoItem? = conveter.convert(getTodoItemDto)

        assertEquals(getTodoItemDto.id, todoItem?.id)
        assertEquals(getTodoItemDto.name, todoItem?.name)
        assertEquals(getTodoItemDto.isComplete, todoItem?.isComplete)
    }

}