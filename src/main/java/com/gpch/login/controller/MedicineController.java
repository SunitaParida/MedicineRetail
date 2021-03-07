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

import com.gpch.login.model.Medicine;
import com.gpch.login.model.User;
import com.gpch.login.repository.MedicineRepository;
import com.gpch.login.service.UserService;

@Controller
public class MedicineController {
	
	 
	@Autowired
	private UserService userService;
	
	@GetMapping(value="/admin/addMedicineName")
	public String medicine(Model model)
	{Authentication auth = SecurityContextHolder.getContext().getAuthentication();

	User user = userService.findUserByUserName(auth.getName());
	model.addAttribute("user", user);
		model.addAttribute("Medicine",new Medicine());
		return "admin//medicineName";
	}
	
	@Autowired
	private MedicineRepository medicineRepo;
	
	// Save the medicine details
	@PostMapping(value="/admin/saveMedicineDetails")
	public String saveMedicineDetails( Medicine medicine,Model model )
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		User user = userService.findUserByUserName(auth.getName());
		model.addAttribute("user", user);
		// Business Logic
		System.out.println("The Data is : "+medicine.toString());
		medicineRepo.save(medicine);
	model.addAttribute("Message", "Saved successfully");
		model.addAttribute("Medicine",new Medicine());
		
		return "admin/medicineName";
	}
	
	
	// Get the view url
	@GetMapping(value="/admin/viewMedicine")
	public String viewmedicine(Model model)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		User user = userService.findUserByUserName(auth.getName());
		model.addAttribute("user", user);
		List<Medicine> medicines = medicineRepo.findAll();// find all means fetch all the information available inside table
		model.addAttribute("Medicine",medicines);
		return "admin/viewMedicineName";
	}
	
	
	// delete the medicine
	@GetMapping("/admin/deleteMedicine")
	public String deleteMedicine(Model model,@RequestParam("medicineId") Integer medicineId)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		User user = userService.findUserByUserName(auth.getName());
		model.addAttribute("user", user);
		medicineRepo.deleteById(medicineId);
		model.addAttribute("Message","Deleted Successfully.");
		List<Medicine> medicines = medicineRepo.findAll();// find all means fetch all the information available inside table
		model.addAttribute("Medicine",medicines);
		return "admin/viewMedicineName";
	}
	
	@GetMapping("/admin/editMedicine")
	public String editMedicine(Model model,@RequestParam("medicineId") Integer medicineId)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		User user = userService.findUserByUserName(auth.getName());
		model.addAttribute("user", user);
		// id is availble
		Optional<Medicine> ent = medicineRepo.findById(medicineId);
		model.addAttribute("Medicine",ent.get());
		return "admin/medicineName";
	}

}


