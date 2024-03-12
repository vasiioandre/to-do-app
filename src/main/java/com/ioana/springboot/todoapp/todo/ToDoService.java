package com.ioana.springboot.todoapp.todo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ToDoService {

	private static List<ToDo> todos = new ArrayList<>();
	
	private static int todosCount = 0;
	
	static {
		todos.add(new ToDo(++todosCount, "ioana", "Learn AWS", 
				LocalDate.now().plusYears(1), false));
		todos.add(new ToDo(++todosCount, "ioana", "Docker", 
				LocalDate.now().plusYears(1), false));
		todos.add(new ToDo(++todosCount, "ioana", "Full Stack Development", 
				LocalDate.now().plusYears(3), false));
	}
	
	public List<ToDo> findByUsername(String username) {
		return todos;
	}
	
	public void addToDo(String username, String description, LocalDate targetDate, boolean done) {
		ToDo toDo = new ToDo(++todosCount, username, description, targetDate, done);
		todos.add(toDo);
	}

}
