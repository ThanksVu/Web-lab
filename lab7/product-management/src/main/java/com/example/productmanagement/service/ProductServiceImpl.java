package com.example.productmanagement.service;

import com.example.productmanagement.entity.Product;
import com.example.productmanagement.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts(String sortBy, String sortDir) {
        if (sortBy == null || sortBy.isEmpty()) {
            sortBy = "id";  
        }
        
        if (sortDir == null || sortDir.isEmpty()) {
            sortDir = "asc";  
        }

        Sort sort = sortDir.equals("asc")
            ? Sort.by(Sort.Order.asc(sortBy))  
            : Sort.by(Sort.Order.desc(sortBy));  
        
        return productRepository.findAll(sort);
    }


    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> searchProducts(String keyword) {
        return productRepository.findByNameContaining(keyword);
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category, null);
    }

    @Override
    public List<Product> advancedSearch(String name, String category, BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.searchProducts(name, category, minPrice, maxPrice);
    }

    @Override
    public List<String> getAllCategories() {
        return productRepository.findAllCategories();
    }

    @Override
    public List<Product> getProductsByCategoryAndSort(String category, String sortBy, String sortDir) {
        return productRepository.findByCategory(category, Sort.by(sortDir.equals("asc") ? Sort.Order.asc(sortBy) : Sort.Order.desc(sortBy)));
    }

    // New methods for statistics
    @Override
    public long getTotalProductCount() {
        return productRepository.count();
    }

    @Override
    public BigDecimal getTotalInventoryValue() {
        return productRepository.calculateTotalValue();
    }

    @Override
    public BigDecimal getAveragePrice() {
        return productRepository.calculateAveragePrice();
    }

    @Override
    public List<Product> getLowStockProducts(int threshold) {
        return productRepository.findLowStockProducts(threshold);
    }
}
