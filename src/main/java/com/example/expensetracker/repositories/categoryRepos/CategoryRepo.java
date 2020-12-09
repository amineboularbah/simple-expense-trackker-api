package com.example.expensetracker.repositories.categoryRepos;

import com.example.expensetracker.exceptions.EtBadRequestException;
import com.example.expensetracker.exceptions.EtRessourceNotFoundException;
import com.example.expensetracker.models.Category;

import java.util.List;

public interface CategoryRepo {
    List<Category> findAll(int userId) throws EtRessourceNotFoundException;

    Category findById(int userId, int categoryId) throws EtRessourceNotFoundException;

    int create (int userId, String title, String description) throws EtBadRequestException;

    void update(int userId, int categoryId, Category category) throws EtBadRequestException;

    void deleteById(int userId, int categoryId);
}
