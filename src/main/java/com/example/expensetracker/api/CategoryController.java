package com.example.expensetracker.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @GetMapping("/all")
    public String getAllCategories(HttpServletRequest request){
        int userId = (Integer) request.getAttribute("userId");
        return "Authentication! userId: " + userId;
    }
}
