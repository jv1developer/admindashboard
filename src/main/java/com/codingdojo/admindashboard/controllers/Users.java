package com.codingdojo.admindashboard.controllers;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.codingdojo.admindashboard.models.User;
import com.codingdojo.admindashboard.services.UserService;
import com.codingdojo.admindashboard.validator.UserValidator;

// imports removed for brevity
@Controller
public class Users {
	private final UserService userService;
	
	// NEW
    private final UserValidator userValidator;
    
    public Users(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }
    @RequestMapping("/registration")
    public String registerForm(@ModelAttribute("user") User user) {
    	return "registrationPage.jsp";
    }
    @RequestMapping(value="/registration", method=RequestMethod.POST)
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session) {
    	System.out.println("Users.java - registration POST");
    	List <User> users = userService.allUsers();
    	if (users.size() > 0) {
    		user.setRole("normal");
    	}
    	else {
    		/*user.setRole("admin");*/
    		user.setRole("super");
    	}
    	Date newDate = new Date();
    	user.setSignInDate(newDate.toString());
    	userValidator.validate(user,  result);
    	// if result has errors, return the registration page (don't worry about validations just now)
    	if (result.hasErrors()) {
    		return "registrationPage.jsp";
    	}
        // else, save the user in the database, save the user id in session, and redirect them to the /home route
    	User u = userService.registerUser(user);
    	System.out.println("Users.java - registration POST - Registered User ID: "+u.getId());
    	session.setAttribute("userId", u.getId());
    	return "redirect:/home";
    }
    @RequestMapping("/home")
    public String home(HttpSession session, Model model) {
    	System.out.println("Users.java - home");
        // get user from session, save them in the model and return the home page
    	Long userId = (Long) session.getAttribute("userId");
    	User u = userService.findUserById(userId);
    	model.addAttribute("user", u);
    	if (u.getRole().equals("super")) {
    		List <User> users = userService.allUsers();
        	model.addAttribute("users", users);
        	return "homePage.jsp";
    	}
    	else if (u.getRole().equals("admin")) {
    		List <User> users = userService.allUsersExceptAdmin();
        	model.addAttribute("users", users);
        	return "homePage.jsp";
    	}
    	else {
    		return "details.jsp";
    	}
    	
    }
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
    	System.out.println("Users.java - logout");
        // invalidate session
    	session.invalidate();
        // redirect to login and registration page
    	return "redirect:/registration";
    }
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password, Model model, HttpSession session, @ModelAttribute("user") User user) {
        // if the user is authenticated, save their user id in session
    	boolean isAuthenticated = userService.authenticateUser(email,  password);
    	if (isAuthenticated) {
    		User u = userService.findByEmail(email);
    		Date newDate = new Date();
        	u.setSignInDate(newDate.toString());
        	userService.updateUser(u);
    		System.out.println("Users.java - login POST - Login User ID: "+u.getId());
    		session.setAttribute("userId", u.getId());
    		return "redirect:/home";
    	}
        // else, add error messages and return the login page
    	else {
    		model.addAttribute("error", "Invalid Credentials. Please try again.");
    		System.out.println("Invalid credentials - return to registration page");
    		return "registrationPage.jsp";
    	}
    }
    @RequestMapping(value="/deleteUser")
    public String deleteUser(@RequestParam("user_id") Long user_id) {
    	System.out.println("Users.java - deleteUser");
    	userService.deleteUser(user_id);
    	return "redirect:/home";
    }
    @RequestMapping(value="/makeAdmin")
    public String makeAdmin(@RequestParam("user_id") Long user_id) {
    	System.out.println("Users.java - makeAdmin");
    	User u = userService.findUserById(user_id);
    	u.setRole("admin");
    	userService.updateUser(u);
    	return "redirect:/home";
    }
    /*@RequestMapping(value="/deleteUser/{user_id}")
    public String deleteUser(@PathVariable("user_id") Long user_id, HttpSession session) {
    	System.out.println("Users.java - deleteUser");
    	userService.deleteUser(user_id);
    	return "redirect:/home";
    }*/
}