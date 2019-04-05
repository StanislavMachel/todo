package com.example.todo.controllers

import com.example.todo.dtos.todo.GetTodoItemDto
import com.example.todo.dtos.todo.PostTodoItemDto
import com.example.todo.dtos.todo.PutTodoItemDto
import com.example.todo.model.TodoItem
import com.example.todo.repositories.TodoItemRepository
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/todo")
@RestController

class TodoController(private val todoItemRepository: TodoItemRepository) {
//    companion object {
//        val todos : MutableList<TodoItem> =  mutableListOf(
//                TodoItem(1, "Cook dishes", false),
//                TodoItem(2, "Clean the floor", false),
//                TodoItem(3, "Water plants", false)
//        )
//    }

    @GetMapping
    fun getAll() : ResponseEntity<Iterable<GetTodoItemDto>>{
        //return ResponseEntity.ok(todos)
        val todos = todoItemRepository.findAll()

        val getTodoItemDtos = todos.map { todoItem: TodoItem -> GetTodoItemDto(todoItem.id, todoItem.name, todoItem.isComplete) }

        return ResponseEntity.ok(getTodoItemDtos)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long) : ResponseEntity<GetTodoItemDto>{
        val todo : TodoItem? = todoItemRepository.findById(id).orElse(null)

        if(todo != null){
            val getTodoItem = GetTodoItemDto(todo.id, todo.name, todo.isComplete)
            return ResponseEntity.ok(getTodoItem)
        }

        return ResponseEntity.badRequest().build()
    }

    @PostMapping
    fun createNewTodo (@RequestBody postTodoItemDto: PostTodoItemDto) : ResponseEntity<GetTodoItemDto>{
//        var nextId = todos.last().id
//        nextId++
//        todoItem.id = nextId
//        todos.add(todoItem)
//        return ResponseEntity.ok(todoItem)

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
            if(!putTodoItemDto?.name.isNullOrBlank()){
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

