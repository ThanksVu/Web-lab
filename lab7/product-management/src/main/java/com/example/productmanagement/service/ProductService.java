package com.example.productmanagement.service;

import com.example.productmanagement.entity.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    
    List<Product> getAllProducts(String sortBy, String sortDir);  
    Optional<Product> getProductById(Long id);
    Product saveProduct(Product product);
    void deleteProduct(Long id);
    List<Product> searchProducts(String keyword);
    List<Product> getProductsByCategory(String category);
    List<Product> advancedSearch(String name, String category, BigDecimal minPrice, BigDecimal maxPrice);
    List<String> getAllCategories();
    List<Product> getProductsByCategoryAndSort(String category, String sortBy, String sortDir);
    List<Product> getLowStockProducts(int threshold);
    BigDecimal getAveragePrice();
    BigDecimal getTotalInventoryValue();
    long getTotalProductCount();  

}
