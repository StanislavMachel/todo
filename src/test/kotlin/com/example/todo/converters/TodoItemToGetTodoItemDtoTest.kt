package com.example.todo.converters

import com.example.todo.dtos.todo.GetTodoItemDto
import com.example.todo.model.TodoItem
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*


class TodoItemToGetTodoItemDtoTest {
    private lateinit var conveter : TodoItemToGetTodoItemDto

    @Before
    fun setUp() {
        conveter = TodoItemToGetTodoItemDto()
    }

    @Test
    fun testEmptyObject(){
        assertNotNull(conveter.convert(TodoItem()))
    }

    @Test
    fun convert(){

        val todoItem : TodoItem = TodoItem(1, "Test", false)

        val getTodoItemDto: GetTodoItemDto = conveter.convert(todoItem)

        assertEquals(todoItem.id, getTodoItemDto.id)
        assertEquals(todoItem.name, todoItem.name)
        assertEquals(todoItem.isComplete, getTodoItemDto.isComplete)

    }

}