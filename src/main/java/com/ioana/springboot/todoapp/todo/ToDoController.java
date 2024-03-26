package com.ioana.springboot.todoapp.todo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.validation.Valid;

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
	
	@RequestMapping(value="add-todo", method=RequestMethod.GET)
	public String showNewToDoPage(ModelMap model) {
		String username = (String)model.get("name");
		ToDo toDo = new ToDo(0, username, "", LocalDate.now().plusYears(1), false);
		model.put("toDo", toDo);
		
		return "toDo";
	}
	
	@RequestMapping(value="add-todo", method=RequestMethod.POST)
	public String addNewToDoPage(ModelMap model, @Valid ToDo toDo, BindingResult result) {
		
		if(result.hasErrors()) {
			return "toDo";
		}
		
		String username = (String)model.get("name");
		toDoService.addToDo(username, toDo.getDescription(), 
				toDo.getTargetDate(), false);
		
		return "redirect:list-todos";
	}
	
	@RequestMapping("delete-todo")
	public String deleteToDo(@RequestParam int id) {
		toDoService.deleteById(id);
		
		return "redirect:list-todos";
	}
	
	@RequestMapping(value="update-todo", method=RequestMethod.GET)
	public String showUpdateToDoPage(@RequestParam int id, ModelMap model) {
		ToDo toDo = toDoService.findById(id);
		model.addAttribute("toDo", toDo);
		
		return "toDo";
	}
	
	@RequestMapping(value="update-todo", method=RequestMethod.POST)
	public String updateToDo(ModelMap model, @Valid ToDo toDo, BindingResult result) {
		
		if(result.hasErrors()) {
			return "toDo";
		}
		
		String username = (String)model.get("name");
		toDo.setUsername(username);
		toDoService.updateToDo(toDo);
		
		return "redirect:list-todos";
	}
}
