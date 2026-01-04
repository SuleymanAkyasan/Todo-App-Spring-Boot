package com.suleyman.springboot.todos.service;

import com.suleyman.springboot.todos.request.TodoRequest;
import com.suleyman.springboot.todos.response.TodoResponse;

import java.util.List;

public interface TodoService {

    List<TodoResponse> getAllTodos();

    TodoResponse createTodo(TodoRequest todoRequest);

    TodoResponse toggleTodoCompletion(long id);

    void deleteTodo(long id);

}
