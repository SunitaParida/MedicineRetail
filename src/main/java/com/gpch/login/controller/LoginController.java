package com.gpch.login.controller;




import com.gpch.login.model.Employee;
import com.gpch.login.model.Medicine;
import com.gpch.login.model.Store;
import com.gpch.login.model.User;
import com.gpch.login.repository.EmployeeRepository;
import com.gpch.login.repository.MedicineDetailRepository;
import com.gpch.login.repository.MedicineRepository;
import com.gpch.login.repository.StoreRepository;
import com.gpch.login.repository.UserRepository;
import com.gpch.login.service.UserService;

import com.gpch.login.util.RandomString;
import com.gpch.login.util.SendEmail;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class LoginController {
	
	@Value("${mail.username}")
	private String mailId;
	@Value("${mail.password}")
	private String mailpassword;
	@Value("${mail.port}")
	private String port;
	@Value("${mail.smtp}")
	private String smtp;
	@Value("${portalUrl}")
	private String portalUrl;
	@Autowired
	private UserService userService;

	@Value("${user.profile.path}")
	private String profilePic;

	
	
	@GetMapping(value = { "/", "/login" })
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}

	@Autowired
	private StoreRepository storeRepo;
	@Autowired
	private MedicineRepository medicineRepo;
	@Autowired
	private EmployeeRepository employeeRepo;
	@Autowired
	private MedicineDetailRepository medicineDtlRepo;
	@GetMapping(value = "/admin/home")
	public ModelAndView home() {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		User user = userService.findUserByUserName(auth.getName());
		modelAndView.addObject("userName", "Welcome " + user.getUserName() + "/" + user.getName() + " "
				+ user.getLastName() + " (" + user.getEmail() + ")");
		modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
		boolean hasUserRole = auth.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ADMIN"));
		modelAndView.addObject("user", user);
		String months[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
		List<String> monthList = Arrays.asList(months);  
		modelAndView.addObject("Months", monthList);
		Object[] ran = medicineDtlRepo.getDashboardDetails();
		if(ran.length > 0)
		{
		BigInteger ranges[] = new BigInteger[12];;//{50, 85,  190,185, 220, 425, 305, 359, 400 ,100,800,5000};
		for(int i=0;i<((Object[])ran[0]).length;i++)
		{
			ranges[i] = (BigInteger) ((Object[])ran[0])[i];
		}
		List<BigInteger> range = Arrays.asList(ranges);
		modelAndView.addObject("Ranges", range);
		}
		else
		{
			Integer arr[] = {0,0,0,0,0,0,0,0,0,0,0,0};
			List<Integer> range = Arrays.asList(arr);
			modelAndView.addObject("Ranges", range);
		}
		if (hasUserRole) {

			modelAndView.setViewName("admin/index");
		}
 
		List<Store> store=storeRepo.findAll();
		modelAndView.addObject("store", store.size());
		List<Medicine> medicine=medicineRepo.findAll();
		modelAndView.addObject("medicine", medicine.size());
		List<Employee> emp=employeeRepo.findAll();
		modelAndView.addObject("emp", emp.size());

		return modelAndView;
	}
	@Autowired
	private UserRepository userRepository;

	
	@GetMapping(value = "/admin/profile")
	public ModelAndView viewProfile() {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		User user = userService.findUserByUserName(auth.getName());
		modelAndView.addObject("user", user);
		System.out.println("UserID: "+user.getId());
		Optional<User> employeeDtl=userRepository.findById(user.getId());
		if(employeeDtl.isPresent())
		{
			User obj=employeeDtl.get();
			modelAndView.addObject("emp", obj);

		}
		modelAndView.setViewName("admin/profile");
		return modelAndView;
	}
	
	@GetMapping(value = "/admin/changePassword")
	public ModelAndView changePassword() {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		User user = userService.findUserByUserName(auth.getName());
		modelAndView.addObject("user", user);
		//modelAndView.addObject("Message","");
		modelAndView.setViewName("admin/changePassword");
		return modelAndView;
	}
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

	@PostMapping(value = "admin/savePassword")
	public ModelAndView changePublicPassword( @RequestParam("password") String password,@RequestParam("newPassword") String newPassword,@RequestParam("confirmPassword") String confirmPassword)
	{
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		User user = userService.findUserByUserName(auth.getName());
		modelAndView.addObject("user", user);
	
		System.out.println("pass= "+password+" new: "+newPassword+" con"+confirmPassword);
		
	 Optional<User> ent =userRepository.findById(user.getId()); 
		  if(ent.isPresent())
		  {
			  User obj = ent.get();
			  System.out.println(user.getPassword());
			  System.out.println(bCryptPasswordEncoder.encode(password));
			  if(!newPassword.equals(confirmPassword))
			  {
				  modelAndView.addObject("Message","New password and confirm password should be same.");
				  modelAndView.setViewName("admin/changePassword");
				  return modelAndView;	
			  }
			  else
			  {
				
				 if(bCryptPasswordEncoder.matches(password, user.getPassword()))
				 {
					 if(bCryptPasswordEncoder.matches(newPassword, user.getPassword()))
					  {
						  modelAndView.addObject("errorMessage","New password should not match with existing password."); 
						  modelAndView.setViewName("admin/changePassword");
							return modelAndView;	
					  }
					 user.setPassword(newPassword);
					 userService.saveUser(user);
					 modelAndView.addObject("Message","Password changed successfully."); 
					 modelAndView.setViewName("admin/changePassword");
					 return modelAndView;	
				 }
				 else
				 {
					 modelAndView.addObject("errorMessage","Kindly provide valid existing password."); 
					 modelAndView.addObject("user",user); 
					 
					  modelAndView.setViewName("admin/changePassword");
						return modelAndView;	
				 }
				 
			  }
			 	
			  }
		
		
		modelAndView.setViewName("admin/changePassword");
		return modelAndView;	
	}
	
	

	@GetMapping(value = "/forgotPassword")
	public ModelAndView forgotPassword() {
		ModelAndView modelAndView = new ModelAndView();
		
		User user = new User();
		modelAndView.addObject("user",user);
		modelAndView.setViewName("admin/forgotPassword");
		return modelAndView;
	}
	
	@PostMapping(value = "/forgotPassword")
	public ModelAndView forgotPasswordd( @RequestParam("userName") String userName,@RequestParam("email") String email)
	{
		ModelAndView modelAndView = new ModelAndView();
		 
		
		System.out.println("userName= "+userName+" email: "+email);
		Optional<User> employeeDtl=userRepository.findByUserNmAndEmail(userName,email);
		if(employeeDtl.isPresent())
		{
			User obj=employeeDtl.get();
			String newPass = RandomString.getAlphaNumericString(10);
			obj.setPassword(newPass);
			userService.saveUser(obj);
			 // mail send 
			
				try
				{
					Thread t = new Thread()
							{
								
							@Override
							public void run()
							{
								
								String subject = "Password Credentials || Medicine retail Portal";
								String body = "Dear "+obj.getName()+", <br>You are requested for New Password. "
										+ "Please find the below credentials."
										+ "<br>Your User Name : <b>"+obj.getUserName()+"</b><br>"
												+ "Your New Password : <b>"+newPass+"</b><br><br>Please login on below URL."
														+ "<br>"+portalUrl+""
														+ "<br><b>Thanks and Regards<br>medicine Retail team.</b>";
								new SendEmail().sendMail(email, mailId, mailpassword, smtp, port, subject, body);
							}
							};
							t.start();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				 modelAndView.addObject("Message","Password will send in your email."); 

			
		}else {
			modelAndView.addObject("Message","Please check User Name and email."); 
		}
		
		modelAndView.setViewName("admin/forgotPassword");
		return modelAndView;	
	}
}
