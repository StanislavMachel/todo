package com.example.todo.controllers

import com.example.todo.dtos.todo.GetTodoItemDto
import com.example.todo.repositories.TodoItemRepository
import com.example.todo.services.TodoService
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.hasSize
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class TodoItemControllerTest {

    lateinit var todoController: TodoController
    @Mock
    lateinit var todoItemRepository: TodoItemRepository
    @Mock
    lateinit var todoService: TodoService
    lateinit var mockMvc: MockMvc

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        todoController = TodoController(todoItemRepository, todoService)
        mockMvc = MockMvcBuilders.standaloneSetup(todoController).build()
    }

    @Test
    fun getAll(){

        val getTodoItemDtos : Set<GetTodoItemDto> = hashSetOf(
                GetTodoItemDto(1, "Cook dishes", false),
                GetTodoItemDto(2, "Clean the floor", false),
                GetTodoItemDto(3, "Water plants", false)
        )

        Mockito.`when`(todoService.findAll()).thenReturn(getTodoItemDtos)

        mockMvc.perform(get("/todo"))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize<Any>(3)))
                .andExpect(jsonPath("$[0].id", `is`(1)))
                .andExpect(jsonPath("$[0].name", `is`("Cook dishes")))
                .andExpect(jsonPath("$[0].complete", `is`(false)))


        verify(todoService, times(1)).findAll()
    }

    @Test
    fun getById(){

        val getTodoItemDto = GetTodoItemDto(1, "Cook dishes", false)

        Mockito.`when`(todoService.getById(1L)).thenReturn(getTodoItemDto)

        mockMvc.perform(get("/todo/1"))
                .andExpect(status().isOk )
                .andExpect(jsonPath("id", `is`(1)) )
                .andExpect(jsonPath("name", `is`("Cook dishes")))
                .andExpect(jsonPath("complete", `is`(false)))

        verify(todoService, times(1)).getById(1L)
    }

    @Test
    fun getByIdBadRequest(){

        Mockito.`when`(todoService.getById(4L)).thenReturn(null)

        mockMvc.perform(get("/todo/4"))
                .andExpect ( status().isBadRequest )

        verify(todoService, times(1)).getById(4L)
    }
}