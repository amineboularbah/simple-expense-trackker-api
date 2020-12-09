package com.example.expensetracker.api;

import com.example.expensetracker.models.Category;
import com.example.expensetracker.service.categoryService.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/new")
    public ResponseEntity<Category> addCategory(HttpServletRequest request, @RequestBody Map<String, Object> categoryMap){
        int userId = (Integer) request.getAttribute("userId");
        String title = (String) categoryMap.get("title");
        String description = (String) categoryMap.get("description");

        Category category = categoryService.addCategory(userId,title, description);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }


    @GetMapping("/all")
    public ResponseEntity<List<Category>> getAllCategories(HttpServletRequest request){
        int userId = (Integer) request.getAttribute("userId");
        List<Category> categories = categoryService.getAllCategories(userId);
        return new ResponseEntity<>(categories,HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getCategoryById(HttpServletRequest request, @PathVariable Integer categoryId){
        int userId = (Integer) request.getAttribute("userId");
        Category category = categoryService.getCategoryById(userId,categoryId);

        return new ResponseEntity<>(category,HttpStatus.OK);
    }

    @PutMapping("/update/{categoryId}")
    public ResponseEntity<Map<String,Boolean>> updateCategory(HttpServletRequest request, @PathVariable("categoryId") int categoryId,@RequestBody Category category){
        int userId = (Integer) request.getAttribute("userId");
        categoryService.updateCategory(userId,categoryId,category);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map,HttpStatus.OK);
    }
}
