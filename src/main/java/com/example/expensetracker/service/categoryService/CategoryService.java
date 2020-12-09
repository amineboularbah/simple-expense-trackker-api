package com.example.expensetracker.service.categoryService;

import com.example.expensetracker.exceptions.EtBadRequestException;
import com.example.expensetracker.exceptions.EtRessourceNotFoundException;
import com.example.expensetracker.models.Category;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CategoryService {

    List<Category> getAllCategories(int userId);

    Category getCategoryById(int userId, int categoryId) throws EtRessourceNotFoundException;

    Category addCategory(int userId, String title, String description) throws EtBadRequestException;

    void updateCategory(int userId, int categoryId, Category category) throws EtBadRequestException;

    void removeCategoryWithAllTransactions(int userId, int categoryId) throws EtRessourceNotFoundException;

}
