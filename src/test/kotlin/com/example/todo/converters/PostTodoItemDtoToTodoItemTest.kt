package com.example.todo.converters

import com.example.todo.dtos.todo.PostTodoItemDto
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class PostTodoItemDtoToTodoItemTest {

    private lateinit var converter: PostTodoItemDtoToTodoItem
    @Before
    fun setUp() {
        converter = PostTodoItemDtoToTodoItem()
    }

    @Test
    fun convert() {
        val postTodoItemDto = PostTodoItemDto("Cook dinner")

        val todoItem = converter.convert(postTodoItemDto)

        assertEquals(postTodoItemDto.name, todoItem.name)
        assertEquals(false, todoItem.isComplete)
    }
}