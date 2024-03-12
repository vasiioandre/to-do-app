package com.ioana.springboot.todoapp.todo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("name")
public class ToDoController {
	
	private ToDoService toDoService;
	
	public ToDoController(ToDoService toDoService) {
		super();
		this.toDoService = toDoService;
	}

	@RequestMapping("list-todos")
	public String listAllToDos(ModelMap model) {
		List<ToDo> todos = toDoService.findByUsername("ioana");
		model.addAttribute("todos", todos);
		
		return "listToDos";
	}
	
	@RequestMapping(value="add-todo", method = RequestMethod.GET)
	public String showNewToDoPage() {
		return "toDo";
	}
	
	@RequestMapping(value="add-todo", method = RequestMethod.POST)
	public String addNewToDoPage(@RequestParam String description, ModelMap model) {
		String username = (String)model.get("name");
		toDoService.addToDo(username, description, 
				LocalDate.now().plusYears(1), false);
		
		return "redirect:list-todos";
	}
}
