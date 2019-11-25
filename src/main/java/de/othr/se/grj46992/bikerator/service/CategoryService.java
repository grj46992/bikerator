package de.othr.se.grj46992.bikerator.service;

import de.othr.se.grj46992.bikerator.entity.Category;
import de.othr.se.grj46992.bikerator.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService implements CategoryServiceIF {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category createCategory(Category category) {
        Category newCategory = categoryRepository.save(category);
        return newCategory;
    }
}