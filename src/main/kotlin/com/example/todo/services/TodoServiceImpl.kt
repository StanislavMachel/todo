package com.example.todo.services

import com.example.todo.converters.TodoItemToGetTodoItemDto
import com.example.todo.dtos.todo.GetTodoItemDto
import com.example.todo.dtos.todo.PutTodoItemDto
import com.example.todo.model.TodoItem
import com.example.todo.repositories.TodoItemRepository
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class TodoServiceImpl(private val todoItemRepository: TodoItemRepository, private val todoItemToGetTodoItemDto: TodoItemToGetTodoItemDto) : TodoService {
    override fun deleteById(id: Long) {
        try {
            todoItemRepository.deleteById(id)
        }
        catch (e: EmptyResultDataAccessException){ }
    }

    override fun update(id: Long, putTodoItemDto: PutTodoItemDto) : GetTodoItemDto {
        val todoItemForUpdate = getTodoItemById(id) ?: throw IllegalArgumentException("TodoItem with id: $id not found.")

        if(!putTodoItemDto.name.isNullOrBlank()){
            todoItemForUpdate.name = putTodoItemDto.name.toString()
        }
        if(putTodoItemDto.isComplete != null){
            todoItemForUpdate.isComplete = putTodoItemDto.isComplete
        }

        val updatedTodoItem = todoItemRepository.save(todoItemForUpdate)

        return todoItemToGetTodoItemDto.convert(updatedTodoItem)
    }

    override fun getById(id: Long) : GetTodoItemDto? {

        val todo = getTodoItemById(id)

        if(todo != null){
            return todoItemToGetTodoItemDto.convert(todo)
        }
        return null
    }

    private fun getTodoItemById(id: Long) = todoItemRepository.findById(id).orElse(null)

    override fun getAll(): Set<GetTodoItemDto> {

        val todos = todoItemRepository.findAll()

        val getTodoItemDtos : List<GetTodoItemDto> = todos.map { todoItem: TodoItem -> todoItemToGetTodoItemDto.convert(todoItem) }

        return getTodoItemDtos.toSet()
    }

}