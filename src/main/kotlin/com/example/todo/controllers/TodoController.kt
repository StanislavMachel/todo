package com.example.todo.controllers

import com.example.todo.dtos.todo.GetTodoItemDto
import com.example.todo.dtos.todo.PostTodoItemDto
import com.example.todo.dtos.todo.PutTodoItemDto
import com.example.todo.model.TodoItem
import com.example.todo.repositories.TodoItemRepository
import com.example.todo.services.TodoService
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/todo")
@RestController
class TodoController(private val todoItemRepository : TodoItemRepository, private val todoService: TodoService) {

    @GetMapping
    fun getAll() : ResponseEntity<Iterable<GetTodoItemDto>>{
        return ResponseEntity.ok(todoService.findAll())
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long) : ResponseEntity<GetTodoItemDto>{

        val getTodoItemDto = todoService.getById(id)

        if(getTodoItemDto != null){

            return ResponseEntity.ok(getTodoItemDto)
        }

        return ResponseEntity.badRequest().build()
    }

    @PostMapping
    fun createNewTodo (@RequestBody postTodoItemDto: PostTodoItemDto) : ResponseEntity<GetTodoItemDto>{

        val todoItem = TodoItem(postTodoItemDto.name)

        val newTodoItem = todoItemRepository.save(todoItem)

        val getTodoItemDto = GetTodoItemDto(newTodoItem.id, newTodoItem.name, newTodoItem.isComplete)

        return ResponseEntity.ok(getTodoItemDto)

    }

    @PutMapping("/{id}")
    fun updateNewTodo(@PathVariable id: Long, @RequestBody putTodoItemDto: PutTodoItemDto) : ResponseEntity<GetTodoItemDto>{

        val todoItemForUpdadate = todoItemRepository.findById(id).orElse(null)

        var getTodoItemDto:GetTodoItemDto
        if(todoItemForUpdadate != null){
            if(!putTodoItemDto.name.isNullOrBlank()){
                todoItemForUpdadate.name = putTodoItemDto.name.toString()
            }
            if(putTodoItemDto.isComplete != null){
                todoItemForUpdadate.isComplete = putTodoItemDto.isComplete
            }

            val updatedTodoItem = todoItemRepository.save(todoItemForUpdadate)
            getTodoItemDto = GetTodoItemDto(updatedTodoItem.id, updatedTodoItem.name, updatedTodoItem.isComplete)
            return ResponseEntity.ok(getTodoItemDto)
        }

        return ResponseEntity.badRequest().build()
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: Long): ResponseEntity<Unit>{

        try {
            todoItemRepository.deleteById(id)
        }
        catch (e: EmptyResultDataAccessException){ }
        finally {
            return ResponseEntity(HttpStatus.OK)
        }
    }
}

