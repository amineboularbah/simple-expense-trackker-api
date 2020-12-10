package com.example.expensetracker.service.categoryService;


import com.example.expensetracker.exceptions.EtBadRequestException;
import com.example.expensetracker.exceptions.EtRessourceNotFoundException;
import com.example.expensetracker.models.Category;
import com.example.expensetracker.repositories.categoryRepos.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImplementation implements CategoryService {

    @Autowired
    CategoryRepo categoryRepo;

    @Override
    public List<Category> getAllCategories(int userId) {
        return categoryRepo.findAll(userId);
    }

    @Override
    public Category getCategoryById(int userId, int categoryId) throws EtRessourceNotFoundException {
        return categoryRepo.findById(userId, categoryId);
    }

    @Override
    public Category addCategory(int userId, String title, String description) throws EtBadRequestException {
        int categoryId = categoryRepo.create(userId,title,description);

        return categoryRepo.findById(userId, categoryId);
    }

    @Override
    public void updateCategory(int userId, int categoryId, Category category) throws EtBadRequestException {
        categoryRepo.update(userId,categoryId,category);
    }

    @Override
    public void removeCategoryWithAllTransactions(int userId, int categoryId) throws EtRessourceNotFoundException {
        this.getCategoryById(userId,categoryId);
        categoryRepo.deleteById(userId,categoryId);
    }
}
