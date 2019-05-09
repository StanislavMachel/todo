package com.example.todo.services

import com.example.todo.converters.TodoItemToGetTodoItemDto
import com.example.todo.dtos.todo.GetTodoItemDto
import com.example.todo.model.TodoItem
import com.example.todo.repositories.TodoItemRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

class TodoServiceTest {

    lateinit var todoService: TodoService

    @Mock
    lateinit var todoItemRepository: TodoItemRepository


    private var todoItemToGetTodoItemDto: TodoItemToGetTodoItemDto = TodoItemToGetTodoItemDto()

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        todoService = TodoServiceImpl(todoItemRepository, todoItemToGetTodoItemDto)
    }

    @Test
    fun findAll(){

        var todos : Set<TodoItem> = hashSetOf(
            TodoItem(1, "Cook dishes", false),
            TodoItem(2, "Clean the floor", false),
            TodoItem(3, "Water plants", false)
        )

        Mockito.`when`(todoItemRepository.findAll()).thenReturn(todos)

        val getTodoItemDtos : Set<GetTodoItemDto> = todoService.findAll()

        assertEquals(3, getTodoItemDtos.size)
        verify(todoItemRepository, times(1)).findAll()
        verify(todoItemRepository, never()).findById(ArgumentMatchers.anyLong())
    }

    @Test
    fun findById(){
        val todo = TodoItem(1, "Cook dishes", false)

        val optionalOfTodo = Optional.of(todo)

        Mockito.`when`(todoItemRepository.findById(ArgumentMatchers.anyLong())).thenReturn(optionalOfTodo)

        val getTodoItemDto = todoService.getById(1L)

        assertEquals(1L, getTodoItemDto?.id)
        verify(todoItemRepository, times(1)).findById(1L)
        verify(todoItemRepository, never()).findAll()

    }


}