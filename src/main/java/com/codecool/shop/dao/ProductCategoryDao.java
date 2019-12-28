package com.codecool.shop.dao;

import com.codecool.shop.model.ProductCategory;

import java.util.List;

public interface ProductCategoryDao {

    void add(ProductCategory category);

    ProductCategory find(int id);

    void remove(int id);

    List<ProductCategory> getAll();

    List<ProductCategory> getByDepartments(String department);

    List<String> getAllDepartments();

}
