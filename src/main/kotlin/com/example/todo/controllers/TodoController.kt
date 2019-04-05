package com.example.todo.controllers

import com.example.todo.model.TodoItem
import com.example.todo.repositories.TodoItemRepository
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
    fun getAll() : ResponseEntity<Iterable<TodoItem>>{
        //return ResponseEntity.ok(todos)
        return ResponseEntity.ok(todoItemRepository.findAll())
    }

    @PostMapping
    fun newTodo (@RequestBody todoItem: TodoItem) : ResponseEntity<TodoItem>{
//        var nextId = todos.last().id
//        nextId++
//        todoItem.id = nextId
//        todos.add(todoItem)
//        return ResponseEntity.ok(todoItem)
        val todo = todoItemRepository.save(todoItem)
        return ResponseEntity.ok(todo)

    }
}

