package sa45.team9.inventoryApp.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import sa45.team9.inventoryApp.Component.SessionComponent;
import sa45.team9.inventoryApp.model.User;
import sa45.team9.inventoryApp.services.IUserService;

@Controller
public class LoginController {

	@Autowired
	private IUserService userService;
	
	@RequestMapping("/") 
	public ModelAndView home(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/login");	
	    return modelAndView; 
	} 

	@RequestMapping(value={"/login"}, method = RequestMethod.GET)
	public ModelAndView login(){
		ModelAndView modelAndView = new ModelAndView();
//		User user = new User();
//		modelAndView.addObject("user", user);
		modelAndView.setViewName("login");	
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/home", method = RequestMethod.GET)
	 public ModelAndView adminHome(Model model){
	 ModelAndView modelAndView = new ModelAndView();
	 SecurityContext context = SecurityContextHolder.getContext();
	 //User user = userService.findUserByName(auth.getName());
	 //modelAndView.addObject("userName", "Welcome " + user.getName() + " " +
	 //user.getLastName() + " (" + user.getEmail() + ")");
	 modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
	 model.addAttribute("message", "You are logged in as " + context.getAuthentication().getName());
	 modelAndView.setViewName("/admin/home");
	 return modelAndView;
	 }
	
//	@RequestMapping(value="/login", method = RequestMethod.POST)
//	public ModelAndView authenticate(Model model){
//		ModelAndView mav = new ModelAndView();		
////		SessionComponent us = new SessionComponent();	
////		us.setSessionId(session.getId());
////		session.setAttribute("USERSESSION", us);
//		 SecurityContext context = SecurityContextHolder.getContext();
//		 model.addAttribute("message", "You are logged in as " + context.getAuthentication().getName());
//		 mav = new ModelAndView("redirect:/admin/home");
//		 return mav;		
//	}
//	@RequestMapping(value="/", method = RequestMethod.POST)
//	public String authenticate(Model model){
//		ModelAndView mav = new ModelAndView();
//		SecurityContext context = SecurityContextHolder.getContext();
//		model.addAttribute("message", "You are logged in as " + context.getAuthentication().getName());
//		//mav = new ModelAndView("redirect:/admin/home");
//		return "/admin/home";		
//	}
	
		
	
	
	

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public ModelAndView registration() {
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("registration");
		return modelAndView;
	}
	 @RequestMapping(value = "/registration", method = RequestMethod.POST)
	 public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
	 ModelAndView modelAndView = new ModelAndView();
	 User userExists = userService.findUserByName(user.getUsername());
	 if (userExists != null) {
	 bindingResult
	 .rejectValue("email", "error.user", "There is already a user registered with this name");
	 }
	 if (bindingResult.hasErrors()) {
	 modelAndView.setViewName("registration");
	 }
	 else {
	 userService.saveUser(user);
	 modelAndView.addObject("successMessage", "User has been registered successfully");
	 modelAndView.addObject("user", new User());
	 modelAndView.setViewName("registration");
	 }
	 return modelAndView;
	 }
	 
	 
	
}