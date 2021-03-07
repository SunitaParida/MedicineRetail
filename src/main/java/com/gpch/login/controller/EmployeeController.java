package com.gpch.login.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.gpch.login.model.Employee;
import com.gpch.login.model.State;
import com.gpch.login.model.User;
import com.gpch.login.repository.EmployeeRepository;
import com.gpch.login.repository.StateRepository;
import com.gpch.login.repository.UserRepository;
import com.gpch.login.service.UserService;
import com.gpch.login.util.RandomString;
import com.gpch.login.util.SendEmail;

@Controller
public class EmployeeController {
	@Value("${mail.username}")
	private String mailId;
	@Value("${mail.password}")
	private String mailpassword;
	@Value("${mail.port}")
	private String port;
	@Value("${mail.smtp}")
	private String smtp;
	@Value("${user.profile.picture}")
	private String profilePic;
	@Autowired
	private UserService userService;
	@Autowired
	private StateRepository stateRepo;
	
	@GetMapping(value="/admin/employee")
	public String employeeRegistration(Model model)
	{
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	User user = userService.findUserByUserName(auth.getName());
	model.addAttribute("user", user);
	 
	 //fetch active state
	 List<State> state=stateRepo.fetchActiveState();
	 model.addAttribute("state",state);
	 model.addAttribute("chk", 1);
	 model.addAttribute("Employee",new Employee());
	return "admin/employee";
	}
	
	
	@Autowired
	private EmployeeRepository employeeRepo;
	@Autowired
	private UserRepository userRepo;
	@PostMapping(value="/admin/registerEmployee")
	public String registerEmployee(Model model,Employee employee,@RequestParam("file") MultipartFile file)
	{
		//  store the file in a file system.
		try 
		{String existPath = "";

		String fileName = "", dlFile = "";

		String ent = employee.getProfilePicture();
			/*String fileName = System.nanoTime()+"_"+file.getOriginalFilename();
			String pathToStore = path+"/"+fileName;*/
		if (employee.getUserId() == null) {
Optional<Employee> employeeData=employeeRepo.findByEmail(employee.getEmailAddress());
			
			if(employeeData.isPresent())
			{
				model.addAttribute("error", "This Mail Id Already Exist. Please try with another mail Id.");
				
				
			}
			else
			{
			fileName = System.nanoTime() + file.getOriginalFilename();
			String encodeFileName=new String(Base64.getEncoder().encode(fileName.getBytes()));
			employee.setProfilePicture(encodeFileName);
			
		
			BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
			String newPass = RandomString.getAlphaNumericString(10);
			String encodePass=encode.encode(newPass);
			employee.setPassword(encodePass);
			employee.setCountryId(1);
			employeeRepo.save(employee);
			final Integer employeeId = employee.getUserId();
			
			User user=new User();
			user.setEmail(employee.getEmailAddress());
			user.setName(employee.getUserName());
			user.setLastName("");
			user.setDob("");
			user.setMobileNo(employee.getMobileNo());
			user.setUserName(employee.getEmailAddress() ); 	
			user.setPassword(encodePass);
			user.setProfilePic(encodeFileName);
			user.setExternalUserId(employeeId);
			user.setUserType("Employee");
			userService.saveUser(user);
			System.out.println(newPass);
			model.addAttribute("Message","Data registered successfully.");
			// make the file name random
			if (file != null && file.isEmpty() == false && file.getOriginalFilename().trim().length() != 0) {

				file.transferTo( new File(profilePic+ fileName));
			}
			
			// mail send

			try {
				Thread t = new Thread() {

					@Override
					public void run() {

						String subject = "Registration Credentials || Medicine Retail";
						String body = "Dear " + employee.getUserName()
								+ ", <br>Your registration for Medicine Retail portal  "
								+ "has been registered succesfully. Please find the below credentials."
								+ "<br>Your User Name : <b>" + employee.getUserName() + "</b><br>"
								+ "Your Password : <b>" + newPass + "</b><br><br>" + 
								 "" + "<br><b>Thanks and Regards<br>Medicine Retail team.</b>";
						new SendEmail().sendMail(employee.getEmailAddress(), mailId, mailpassword, smtp, port, subject, body);
					}
				};
				t.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			}}else {
		Optional<Employee> employeeDetail=employeeRepo.findByEmail(employee.getEmailAddress());

			//update
		
		if (employeeDetail.isPresent() && (int) employeeDetail.get().getUserId() != (int) employee.getUserId()) {
			model.addAttribute("error", "This Mail Id Already Exist. Please try with another mail Id.");
			
			
		}
		else
		{
			Optional<Employee> employeeData=employeeRepo.findById(employee.getUserId());
			employee.setCountryId(1);
			if (file.isEmpty() == false) {
				fileName = System.nanoTime() + file.getOriginalFilename();
				employee.setProfilePicture(new String(Base64.getEncoder().encode(fileName.getBytes())));
			} else {
				employee.setProfilePicture(employeeData.get().getProfilePicture());
			}
			
			
			employee.setPassword(employeeData.get().getPassword());
			
			employeeRepo.save(employee);
final Integer employeeId = employee.getUserId();
			
			
			User userData=userRepo.findBYUserId(employee.getUserId());
			
				
			userData.setEmail(employee.getEmailAddress());
			userData.setName(employee.getUserName());
			userData.setLastName("");
			userData.setDob("");
			userData.setMobileNo(employee.getMobileNo());
			userData.setUserName(employee.getEmailAddress() ); 	
			userData.setPassword(employeeData.get().getPassword());
			userData.setProfilePic(employee.getProfilePicture());
			userData.setExternalUserId(employeeId);
			userData.setUserType("Employee");
			userService.updateUser(userData);
			model.addAttribute("Message","Data updated Successfully.");
			if (file != null && file.isEmpty() == false && file.getOriginalFilename().trim().length() != 0) {

				file.transferTo( new File(profilePic+ fileName));
			}
			
			// mail send

						try {
							Thread t = new Thread() {

								@Override
								public void run() {

									String subject = "Registration Credentials || Repairing Assets";
									String body = "Dear " + employee.getUserName()
											+ ", <br>Your registration for Repairing Assets portal  "
											+ "has been updated succesfully. Please find the below credentials."
											+ "<br>Your User Name : <b>" + employee.getUserName() + "</b><br>"
											+ "Your Password : <b>" + employeeData.get().getPassword()+ "</b><br><br>" + 
											 "" + "<br><b>Thanks and Regards<br>Repairing Assets team.</b>";
									new SendEmail().sendMail(employee.getEmailAddress(), mailId, mailpassword, smtp, port, subject, body);
								}
							};
							t.start();
						} catch (Exception e) {
							e.printStackTrace();
						}
		}
			}
			//fetch active state
			 List<State> state=stateRepo.fetchActiveState();
			 model.addAttribute("state",state);
			 model.addAttribute("Employee",new Employee());
			 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				User user = userService.findUserByUserName(auth.getName());
				model.addAttribute("user", user);
				 model.addAttribute("chk", 1);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return "admin/employee";
	}
	
	@GetMapping(value="/admin/viewEmployee")
	public String viewEmployee(Model model)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		User user = userService.findUserByUserName(auth.getName());
		model.addAttribute("user", user);
		List<Employee> employee = employeeRepo.findAll();// find all means fetch all the information available inside table
		model.addAttribute("Employee",employee);
		return "admin/viewEmployee";
	}
	
	@RequestMapping(value = "admin/public/load_image", method = RequestMethod.GET)
	void loadImageInformationLicenseeForUpgradationPagee(HttpServletResponse response,
			@RequestParam(value = "imagePath", required = false) String path) throws IOException {
		path = new String(Base64.getDecoder().decode(path));
		BufferedInputStream inputStream = null;

		try {
			inputStream = new BufferedInputStream(new FileInputStream(new File(profilePic + path)));
			OutputStream outputStream = response.getOutputStream();
			byte[] bytes = new byte[1024 * 2];
			int bytesRead = -1;
			while ((bytesRead = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, bytesRead);
			}
			outputStream.flush();

		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}

	}
	
	@GetMapping("/admin/editEmployee")
	public String editEmployee(Model model,@RequestParam("userId") Integer userId)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		User user = userService.findUserByUserName(auth.getName());
		model.addAttribute("user", user);
		// id is availble
		Optional<Employee> ent = employeeRepo.findById(userId);
		model.addAttribute("Employee",ent.get());
		List<State> state=stateRepo.fetchActiveState();
		 model.addAttribute("state",state);
		model.addAttribute("chk", 0);
		return "admin/employee";
	}
	
	// delete the shoptype
			@GetMapping("/admin/deleteEmployee")
			public String deleteEmployee(Model model,@RequestParam("userId") Integer userId)
			{
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();

				User user = userService.findUserByUserName(auth.getName());
				model.addAttribute("user", user);
				employeeRepo.deleteById(userId);
				model.addAttribute("Message","Deleted Successfully.");
				List<Employee> employee = employeeRepo.findAll();// find all means fetch all the information available inside table
				model.addAttribute("Employee",employee);
				return "admin/viewEmployee";
			}
}


