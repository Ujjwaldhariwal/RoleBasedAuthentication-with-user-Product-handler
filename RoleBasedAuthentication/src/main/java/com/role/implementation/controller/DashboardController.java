package com.role.implementation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.role.implementation.model.User;
import com.role.implementation.repository.UserRepository;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String displayDashboard(Model model) {
        String user = returnUsername();
        model.addAttribute("userDetails", user);
        return "dashboard";
    }

    @GetMapping("/go-to-product-portal")
    public String goToProductPortal(RedirectAttributes redirectAttributes) {
        // You can add any logic before redirecting if needed
        return "redirect:/products";
    }

    private String returnUsername() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        UserDetails user = (UserDetails) securityContext.getAuthentication().getPrincipal();
        User users = userRepository.findByEmail(user.getUsername());
        return users.getName();
    }
}
