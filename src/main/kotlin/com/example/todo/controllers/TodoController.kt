package com.example.todo.controllers

import com.example.todo.dtos.todo.GetTodoItemDto
import com.example.todo.dtos.todo.PostTodoItemDto
import com.example.todo.dtos.todo.PutTodoItemDto
import com.example.todo.services.TodoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/todo")
@RestController
class TodoController(private val todoService: TodoService) {

    @GetMapping
    fun getAll() : ResponseEntity<Iterable<GetTodoItemDto>>{
        return ResponseEntity.ok(todoService.getAll())
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

        val getTodoItemDto = todoService.createNewTodo(postTodoItemDto)

        return ResponseEntity.ok(getTodoItemDto)

    }

    @PutMapping("/{id}")
    fun updateTodo(@PathVariable id: Long, @RequestBody putTodoItemDto: PutTodoItemDto) : ResponseEntity<GetTodoItemDto>{

        return try {
            val getTodoItemDto = todoService.update(id, putTodoItemDto)
            ResponseEntity.ok(getTodoItemDto)
        }
        catch (e: IllegalArgumentException){
            ResponseEntity.badRequest().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: Long): ResponseEntity<Unit>{
        todoService.deleteById(id)
        return ResponseEntity(HttpStatus.OK)
    }
}

