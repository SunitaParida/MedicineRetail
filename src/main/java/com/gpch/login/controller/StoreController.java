package com.gpch.login.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gpch.login.model.City;
import com.gpch.login.model.State;
import com.gpch.login.model.Store;
import com.gpch.login.model.User;
import com.gpch.login.repository.CityRepository;
import com.gpch.login.repository.StateRepository;
import com.gpch.login.repository.StoreRepository;
import com.gpch.login.service.UserService;

@Controller
public class StoreController {
	
	@Autowired
	private UserService userService;
	@GetMapping(value="/admin/storeName")
	public String state(Model model)
	{Authentication auth = SecurityContextHolder.getContext().getAuthentication();

	User user = userService.findUserByUserName(auth.getName());
	model.addAttribute("user", user);
	List<State> state=stateRepo.fetchActiveState();
	model.addAttribute("State", state);
		model.addAttribute("store",new Store());
		model.addAttribute("chk", 0);
		return "admin/storeName";
	}
	
	@Autowired
	private StateRepository stateRepo;
	@Autowired
	private CityRepository cityRepo;
	@Autowired
	private StoreRepository storeRepo;
	// Save the store details
	@PostMapping(value="/admin/saveStoreDetails")
	public String saveStateDetails(@ModelAttribute Store store,Model model )
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		User user = userService.findUserByUserName(auth.getName());
		model.addAttribute("user", user);
		// Business Logic
	store.setCountryId(1);
	
		if(null == store.getStoreId())
		{
		model.addAttribute("Message","Saved Successfully.");
	}
		else
		{
			model.addAttribute("Message","Updated Successfully.");	
		}
		storeRepo.save(store);
		
		model.addAttribute("store",new Store());
		List<State> state=stateRepo.fetchActiveState();
		model.addAttribute("State", state);
		model.addAttribute("chk", 0);
		return "admin/storeName";
	}
	
	@GetMapping(value="/admin/viewStore")
	public String viewState(Model model)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		User user = userService.findUserByUserName(auth.getName());
		model.addAttribute("user", user);
		List<Store> store = storeRepo.findAll();
		model.addAttribute("Store",store);
		return "admin/viewStore";
	}
	
	// delete the state
		@GetMapping("/admin/deleteStore")
		public String deleteState(Model model,@RequestParam("storeId") Integer storeId)
		{
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();

			User user = userService.findUserByUserName(auth.getName());
			model.addAttribute("user", user);
			storeRepo.deleteById(storeId);
			model.addAttribute("Message","Deleted Successfully.");
			List<Store> store = storeRepo.findAll();// find all means fetch all the information available inside table
			model.addAttribute("Store",store);
			return "admin/viewStore";
		}
		@GetMapping("/admin/editStore")
		public String editState(Model model,@RequestParam("storeId") Integer storeId)
		{
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();

			User user = userService.findUserByUserName(auth.getName());
			model.addAttribute("user", user);
			// id is availble
			Optional<Store> ent = storeRepo.findById(storeId);
			model.addAttribute("store",ent.get());
			List<State> state=stateRepo.fetchActiveState();
			model.addAttribute("State", state);
			model.addAttribute("update", 1);
			model.addAttribute("chk", 1);
			return "admin/storeName";
		}	
		@PostMapping("/admin/getCityListInfo")
		@ResponseBody
		public List<City> getCityList(@RequestBody City ce) {

			List<City> cities = cityRepo.fetchActiveCities(ce.getStateId());
			return cities;

		}

}
