package com.ioana.springboot.todoapp.todo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class ToDoControllerJpa {
	
	private ToDoRepository toDoRepository;
	
	public ToDoControllerJpa(ToDoRepository toDoRepository) {
		super();
		this.toDoRepository = toDoRepository;
	}

	@RequestMapping("list-todos")
	public String listAllToDos(ModelMap model) {
		String username = getLoggedInUsername(model);
		List<ToDo> todos = toDoRepository.findByUsername(username);
		model.addAttribute("todos", todos);
		
		return "listToDos";
	}
	
	@RequestMapping(value="add-todo", method=RequestMethod.GET)
	public String showNewToDoPage(ModelMap model) {
		String username = getLoggedInUsername(model);
		ToDo toDo = new ToDo(0, username, "", LocalDate.now().plusYears(1), false);
		model.put("toDo", toDo);
		
		return "toDo";
	}
	
	@RequestMapping(value="add-todo", method=RequestMethod.POST)
	public String addNewToDoPage(ModelMap model, @Valid ToDo toDo, BindingResult result) {
		
		if(result.hasErrors()) {
			return "toDo";
		}
		
		String username = getLoggedInUsername(model);
		toDo.setUsername(username);
		toDoRepository.save(toDo);
		
		return "redirect:list-todos";
	}
	
	@RequestMapping("delete-todo")
	public String deleteToDo(@RequestParam int id) {
		toDoRepository.deleteById(id);
		
		return "redirect:list-todos";
	}
	
	@RequestMapping(value="update-todo", method=RequestMethod.GET)
	public String showUpdateToDoPage(@RequestParam int id, ModelMap model) {
		ToDo toDo = toDoRepository.findById(id).get();
		model.addAttribute("toDo", toDo);
		
		return "toDo";
	}
	
	@RequestMapping(value="update-todo", method=RequestMethod.POST)
	public String updateToDo(ModelMap model, @Valid ToDo toDo, BindingResult result) {
		
		if(result.hasErrors()) {
			return "toDo";
		}
		
		String username = getLoggedInUsername(model);
		toDo.setUsername(username);
		toDoRepository.save(toDo);
		
		return "redirect:list-todos";
	}
	
	private String getLoggedInUsername(ModelMap model) {
		Authentication authentication = 
				SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}
}
