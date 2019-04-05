package com.example.todo.bootstrap

import com.example.todo.model.TodoItem
import com.example.todo.repositories.TodoItemRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component


@Component
class DataLoader(private val todoItemRepository: TodoItemRepository) : CommandLineRunner {

    override fun run(vararg args: String?) {
        todoItemRepository.save(TodoItem(1, "Cook dishes", false))
        todoItemRepository.save(TodoItem(2, "Clean the floor", false))
        todoItemRepository.save(TodoItem(3, "Water plants", false))
    }
}