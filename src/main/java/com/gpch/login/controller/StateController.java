package com.gpch.login.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.gpch.login.model.State;
import com.gpch.login.model.User;
import com.gpch.login.repository.StateRepository;
import com.gpch.login.service.UserService;

@Controller
public class StateController {
	@Autowired
	private UserService userService;
	@GetMapping(value="/admin/state")
	public String state(Model model)
	{Authentication auth = SecurityContextHolder.getContext().getAuthentication();

	User user = userService.findUserByUserName(auth.getName());
	model.addAttribute("user", user);
		model.addAttribute("State",new State());
		return "admin/state";
	}
	
	@Autowired
	private StateRepository stateRepo;
	
	// Save the state details
	@PostMapping(value="/admin/saveStateDetails")
	public String saveStateDetails( State state,Model model )
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		User user = userService.findUserByUserName(auth.getName());
		model.addAttribute("user", user);
		// Business Logic
	state.setCountryId(1);
	
		if(null == state.getStateId())
		{
		model.addAttribute("Message","Saved Successfully.");
	}
		else
		{
			model.addAttribute("Message","Updated Successfully.");	
		}
		stateRepo.save(state);
		
		model.addAttribute("State",new State());
		
		return "admin/state";
	}
	
	@GetMapping(value="/admin/viewState")
	public String viewState(Model model)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		User user = userService.findUserByUserName(auth.getName());
		model.addAttribute("user", user);
		List<State> state = stateRepo.findAll();// find all means fetch all the information available inside table
		model.addAttribute("State",state);
		return "admin/viewState";
	}
	
	// delete the state
		@GetMapping("/admin/deleteState")
		public String deleteState(Model model,@RequestParam("stateId") Integer stateId)
		{
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();

			User user = userService.findUserByUserName(auth.getName());
			model.addAttribute("user", user);
			stateRepo.deleteById(stateId);
			model.addAttribute("Message","Deleted Successfully.");
			List<State> state = stateRepo.findAll();// find all means fetch all the information available inside table
			model.addAttribute("State",state);
			return "admin/viewState";
		}
		@GetMapping("/admin/editState")
		public String editState(Model model,@RequestParam("stateId") Integer stateId)
		{
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();

			User user = userService.findUserByUserName(auth.getName());
			model.addAttribute("user", user);
			// id is availble
			Optional<State> ent = stateRepo.findById(stateId);
			model.addAttribute("State",ent.get());
			return "admin/state";
		}	

}
