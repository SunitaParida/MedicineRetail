package com.gpch.login.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gpch.login.model.City;
import com.gpch.login.model.Medicine;
import com.gpch.login.model.MedicineDetails;
import com.gpch.login.model.SoldMedicineDetails;
import com.gpch.login.model.State;
import com.gpch.login.model.Store;
import com.gpch.login.model.User;
import com.gpch.login.repository.MedicineDetailRepository;
import com.gpch.login.repository.MedicineRepository;
import com.gpch.login.repository.SellMedicineRepository;
import com.gpch.login.repository.StoreRepository;
import com.gpch.login.service.UserService;

@Controller
public class MedicineDetailController {

	@Autowired
	private UserService userService;
	@Autowired
	private MedicineRepository medicineRepo;
	@Autowired
	private StoreRepository storeRepo;
	@GetMapping(value="/admin/addMedicine")
	public String addMedicine(Model model)
	{Authentication auth = SecurityContextHolder.getContext().getAuthentication();

	User user = userService.findUserByUserName(auth.getName());
	model.addAttribute("user", user);
	List<Medicine> medicine=medicineRepo.fetchActiveMedicine();
	model.addAttribute("Medicine", medicine);
	List<Store> store=storeRepo.fetchActiveStore();
	model.addAttribute("Store", store);
		model.addAttribute("medicineDtl",new MedicineDetails());
		model.addAttribute("chk", 0);
		return "admin/addMedicine";
	}
	@Autowired
	private MedicineDetailRepository medicineDtlRepo;
	@PostMapping(value="/admin/saveMedicines")
	public String saveMedicineDetails( MedicineDetails medicineDtl,Model model )
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		User user = userService.findUserByUserName(auth.getName());
		model.addAttribute("user", user);
		System.out.println(medicineDtl.getStoreNm());
		medicineDtl.setEntryDate(new Date());
		medicineDtl.setEmployeeId(user.getExternalUserId());
		medicineDtlRepo.save(medicineDtl);
		
		model.addAttribute("Message","Saved Successfully.");
		List<Medicine> medicine=medicineRepo.fetchActiveMedicine();
		model.addAttribute("Medicine", medicine);
		List<Store> store=storeRepo.fetchActiveStore();
		model.addAttribute("Store", store);
		model.addAttribute("chk", 0);
		model.addAttribute("medicineDtl",new MedicineDetails());
		model.addAttribute("chk", 0);
		return "admin/addMedicine";	}
	
	@GetMapping(value="/admin/sellMedicine")
	public String sellMedicine(Model model)
	{Authentication auth = SecurityContextHolder.getContext().getAuthentication();

	User user = userService.findUserByUserName(auth.getName());
	model.addAttribute("user", user);
	List<Medicine> medicine=medicineRepo.fetchActiveMedicine();
	model.addAttribute("Medicine", medicine);
	List<Store> store=storeRepo.fetchActiveStore();
	model.addAttribute("Store", store);
		model.addAttribute("soldMedicine",new SoldMedicineDetails());
		model.addAttribute("chk", 0);
		return "admin/sellMedicine";
	}
	
	@PostMapping("/admin/readMedicinePrice/{medicine}")
	@ResponseBody
	public BigDecimal getPrice(@PathVariable("medicine") Integer medicine)
	{
		Medicine medicineDtl=medicineRepo.findById(medicine).get();
BigDecimal data=medicineDtl.getMedicinePrice();		
return data;
		
		
	}
	@Autowired
	private SellMedicineRepository sellMedicineRepo;
	
	@PostMapping(value="/admin/saveSoldMedicineDtl")
	public String saveSoldMedicineDtl( SoldMedicineDetails sellMedicine,Model model )
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		User user = userService.findUserByUserName(auth.getName());
		model.addAttribute("user", user);
		BigDecimal totalPrice=sellMedicine.getPrice().multiply(BigDecimal.valueOf(sellMedicine.getQuantity()));
		
		sellMedicine.setTotalAmount(totalPrice);
		sellMedicine.setDateOfPurchase(new Date());
		sellMedicine.setEmployeeId(user.getExternalUserId());
		Optional<MedicineDetails> medicineDtl=medicineDtlRepo.findData(sellMedicine.getStoreId(),sellMedicine.getMedicineId());
		if(medicineDtl.isPresent())
		{
		if(medicineDtl.get().getQuantity()>sellMedicine.getQuantity())
		{
		sellMedicineRepo.save(sellMedicine);
		
		
		
			MedicineDetails obj =medicineDtl.get();
			
			Integer quantity=(obj.getQuantity())-(sellMedicine.getQuantity());
			System.out.println(quantity);
			
			obj.setQuantity(quantity);
			medicineDtlRepo.save(obj);
			Optional<Store> storeDtl=storeRepo.findById(sellMedicine.getStoreId());
			Optional<Medicine> medicineDtls=medicineRepo.findById(sellMedicine.getMedicineId());

model.addAttribute("StoreName", storeDtl.get().getStoreName());
model.addAttribute("MedicineName", medicineDtls.get().getMedicineName());
model.addAttribute("BDate", String.valueOf(new SimpleDateFormat("dd-MMM-yyyy").format(new Date())));
model.addAttribute("Price", String.valueOf(sellMedicine.getPrice()));
model.addAttribute("Quantity", String.valueOf(sellMedicine.getQuantity()));
model.addAttribute("CustomerName", sellMedicine.getCustomerNm());
model.addAttribute("PhoneNo", sellMedicine.getCustomerPhNo());
model.addAttribute("Total", String.valueOf(totalPrice));
return "admin/bill";
}else
		{
			model.addAttribute("error","There are only "+medicineDtl.get().getQuantity() +" medicine available");

			List<Medicine> medicine=medicineRepo.fetchActiveMedicine();
			model.addAttribute("Medicine", medicine);
			List<Store> store=storeRepo.fetchActiveStore();
			model.addAttribute("Store", store);
			model.addAttribute("soldMedicine",new SoldMedicineDetails());
			model.addAttribute("chk", 0);
			return "admin/sellMedicine";
		}

		}else {
			model.addAttribute("error", "Medicine is not available");
			List<Medicine> medicine=medicineRepo.fetchActiveMedicine();
			model.addAttribute("Medicine", medicine);
			List<Store> store=storeRepo.fetchActiveStore();
			model.addAttribute("Store", store);
			model.addAttribute("soldMedicine",new SoldMedicineDetails());
			model.addAttribute("chk", 0);
			return "admin/sellMedicine";
		}
		
		}
	@GetMapping(value="/admin/viewExpireMedicine")
	public String viewExpireMedicine(Model model)
	{Authentication auth = SecurityContextHolder.getContext().getAuthentication();

	User user = userService.findUserByUserName(auth.getName());
	model.addAttribute("user", user);
	List<Medicine> medicine=medicineRepo.fetchActiveMedicine();
	model.addAttribute("Medicine", medicine);
	List<Store> store=storeRepo.fetchActiveStore();
	model.addAttribute("Store", store);
		model.addAttribute("chk", 0);
		return "admin/chkExpireMedicine";
	}
	
	@PostMapping(value="/admin/viewMedicines")
	public String viewMedicines(Model model,@RequestParam("storeId")Integer storeId,@RequestParam("medicineId")Integer medicineId)
	{Authentication auth = SecurityContextHolder.getContext().getAuthentication();

	User user = userService.findUserByUserName(auth.getName());
	model.addAttribute("chk", 1);
	System.out.println(storeId);
	//int storeId=medicinedtl.getStoreNm();
	//int medicineId=medicinedtl.getMedicineId();
	Optional<Store> store=storeRepo.findById(storeId);
	model.addAttribute("storeName", store.get().getStoreName());
	Optional<Medicine> medicine=medicineRepo.findById(medicineId);
	model.addAttribute("medicineNm", medicine.get().getMedicineName());
	Integer medicineDtl=medicineDtlRepo.findByDate(storeId,medicineId);
	if(medicineDtl!=null)
	{
	model.addAttribute("totalAvailable",medicineDtl );
	}
	else {
		model.addAttribute("totalAvailable", 0);
	}
	
	model.addAttribute("user", user);
	List<Medicine> medicinee=medicineRepo.fetchActiveMedicine();
	model.addAttribute("Medicine", medicinee);
	List<Store> storee=storeRepo.fetchActiveStore();
	model.addAttribute("Store", storee);
		return "admin/chkExpireMedicine";
	}
	
	@GetMapping(value="/admin/chkAvailableMedicine")
	public String chkAvailableMedicine(Model model)
	{Authentication auth = SecurityContextHolder.getContext().getAuthentication();

	User user = userService.findUserByUserName(auth.getName());
	model.addAttribute("user", user);
	List<Medicine> medicine=medicineRepo.fetchActiveMedicine();
	model.addAttribute("Medicine", medicine);
	List<Store> store=storeRepo.fetchActiveStore();
	model.addAttribute("Store", store);
		return "admin/chkAvailableMedicine";
	}
	@PostMapping(value="/admin/viewAvailableMedicines")
	public String viewAvailableMedicines(Model model,@RequestParam("storeId")Integer storeId,@RequestParam("medicineId")Integer medicineId)
	{Authentication auth = SecurityContextHolder.getContext().getAuthentication();

	User user = userService.findUserByUserName(auth.getName());
	model.addAttribute("chk", 1);
Optional<Store> store=storeRepo.findById(storeId);
	model.addAttribute("storeName", store.get().getStoreName());
	Optional<Medicine> medicine=medicineRepo.findById(medicineId);
	model.addAttribute("medicineNm", medicine.get().getMedicineName());
	Integer medicineDtl=medicineDtlRepo.availableMedicine(storeId,medicineId);
	if(medicineDtl!=null)
	{
	model.addAttribute("totalAvailable",medicineDtl );
	}else {
		model.addAttribute("totalAvailable",0 );

	}
	model.addAttribute("user", user);
	List<Medicine> medicinee=medicineRepo.fetchActiveMedicine();
	model.addAttribute("Medicine", medicinee);
	List<Store> storee=storeRepo.fetchActiveStore();
	model.addAttribute("Store", storee);
		return "admin/chkAvailableMedicine";
	}
}
