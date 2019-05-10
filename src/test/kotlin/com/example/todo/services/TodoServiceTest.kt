package com.example.todo.services

import com.example.todo.converters.TodoItemToGetTodoItemDto
import com.example.todo.dtos.todo.GetTodoItemDto
import com.example.todo.dtos.todo.PutTodoItemDto
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
import org.springframework.dao.EmptyResultDataAccessException
import java.util.*
import kotlin.IllegalArgumentException

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

        val todos : Set<TodoItem> = hashSetOf(
            TodoItem(1, "Cook dishes", false),
            TodoItem(2, "Clean the floor", false),
            TodoItem(3, "Water plants", false)
        )

        Mockito.`when`(todoItemRepository.findAll()).thenReturn(todos)

        val getTodoItemDtos : Set<GetTodoItemDto> = todoService.getAll()

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

    @Test(expected = IllegalArgumentException::class)
    fun updateThrowIllegalArgumentException(){
        Mockito.`when`(todoItemRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.ofNullable(null))


        todoService.update(1, PutTodoItemDto(null, null))
        verify(todoItemRepository, times(1)).findById(ArgumentMatchers.anyLong())
        verify(todoItemRepository, never()).save(any())
    }

    @Test
    fun update(){
        val todo = TodoItem(1, "Cook dishes", false)
        val updatedTodo = TodoItem(1, "Cook", true)
        val optionalOfTodo = Optional.of(todo)
        val putTodoItemDto = PutTodoItemDto("Cook", true )

        Mockito.`when`(todoItemRepository.findById(ArgumentMatchers.anyLong())).thenReturn(optionalOfTodo)
        Mockito.`when`(todoItemRepository.save(ArgumentMatchers.any(TodoItem::class.java))).thenReturn(updatedTodo)

        todoService.update(1, putTodoItemDto)

        verify(todoItemRepository, times(1)).findById(ArgumentMatchers.anyLong())
        verify(todoItemRepository, times(1)).save(any(TodoItem::class.java))

    }

    @Test
    fun daleteByIdExistingTodoItem(){

        Mockito.doNothing().`when`(todoItemRepository).deleteById(ArgumentMatchers.anyLong())

        todoService.deleteById(1)

        verify(todoItemRepository, times(1)).deleteById(ArgumentMatchers.anyLong())
    }

    @Test
    fun deleteByIdTodoItemWhichNotExist(){

        Mockito.doThrow(EmptyResultDataAccessException::class.java).`when`(todoItemRepository).deleteById(ArgumentMatchers.anyLong())

        todoService.deleteById(1)

        verify(todoItemRepository, times(1)).deleteById(ArgumentMatchers.anyLong())
    }


}