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

import com.gpch.login.model.City;
import com.gpch.login.model.State;
import com.gpch.login.model.User;
import com.gpch.login.repository.CityRepository;
import com.gpch.login.repository.StateRepository;
import com.gpch.login.service.UserService;

@Controller
public class CityController {

	@Autowired
	private UserService userService;
	@GetMapping(value="/admin/city")
	public String state(Model model)
	{Authentication auth = SecurityContextHolder.getContext().getAuthentication();

	User user = userService.findUserByUserName(auth.getName());
	model.addAttribute("user", user);
	List<State> state=stateRepo.fetchActiveState();
	model.addAttribute("State", state);
		model.addAttribute("city",new City());
		model.addAttribute("chk", 0);
		return "admin/city";
	}
	
	@Autowired
	private StateRepository stateRepo;
	@Autowired
	private CityRepository cityRepo;
	// Save the state details
	@PostMapping(value="/admin/saveCityDetails")
	public String saveStateDetails( City city,Model model )
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		User user = userService.findUserByUserName(auth.getName());
		model.addAttribute("user", user);
		// Business Logic
	city.setCountryId(1);
	
		if(null == city.getCityId())
		{
		model.addAttribute("Message","Saved Successfully.");
	}
		else
		{
			model.addAttribute("Message","Updated Successfully.");	
		}
		cityRepo.save(city);
		
		model.addAttribute("city",new City());
		List<State> state=stateRepo.fetchActiveState();
		model.addAttribute("State", state);
		model.addAttribute("chk", 0);
		return "admin/city";
	}
	
	@GetMapping(value="/admin/viewCity")
	public String viewState(Model model)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		User user = userService.findUserByUserName(auth.getName());
		model.addAttribute("user", user);
		List<City> city = cityRepo.findAll();
		model.addAttribute("City",city);
		return "admin/viewCity";
	}
	
	// delete the state
		@GetMapping("/admin/deleteCity")
		public String deleteState(Model model,@RequestParam("cityId") Integer cityId)
		{
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();

			User user = userService.findUserByUserName(auth.getName());
			model.addAttribute("user", user);
			cityRepo.deleteById(cityId);
			model.addAttribute("Message","Deleted Successfully.");
			List<City> city = cityRepo.findAll();// find all means fetch all the information available inside table
			model.addAttribute("City",city);
			return "admin/viewCity";
		}
		@GetMapping("/admin/editCity")
		public String editState(Model model,@RequestParam("cityId") Integer cityId)
		{
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();

			User user = userService.findUserByUserName(auth.getName());
			model.addAttribute("user", user);
			// id is availble
			Optional<City> ent = cityRepo.findById(cityId);
			model.addAttribute("city",ent.get());
			List<State> state=stateRepo.fetchActiveState();
			model.addAttribute("State", state);
			model.addAttribute("chk", 1);
			return "admin/city";
		}	
}
