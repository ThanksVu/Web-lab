package com.example.productmanagement.repository;

import com.example.productmanagement.entity.Product;
import org.springframework.data.domain.Sort;  
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    // Spring Data JPA generates implementation automatically!
    
    // Custom query methods (derived from method names)
    
    // Method to find products by category and sort them based on the Sort parameter
    // Sort will allow you to specify the field and direction (asc/desc)
    List<Product> findByCategory(String category, Sort sort);
    
    // Find products by name containing a keyword
    List<Product> findByNameContaining(String keyword);
    
    // Find products with prices between minPrice and maxPrice
    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    
    // Find products by category and sort them in ascending order by price
    List<Product> findByCategoryOrderByPriceAsc(String category);

    // Check if a product exists by its product code
    boolean existsByProductCode(String productCode);
    
    // Advanced search query using multiple criteria: name, category, price range
    // This query allows filtering based on name (contains), category (exact match), and price range (min-max)
    @Query("SELECT p FROM Product p WHERE " +
            "(:name IS NULL OR p.name LIKE %:name%) AND " +
            "(:category IS NULL OR p.category = :category) AND " +
            "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR p.price <= :maxPrice)")
    List<Product> searchProducts(
            @Param("name") String name,
            @Param("category") String category,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice
    );

        @Query("SELECT COUNT(p) FROM Product p WHERE p.category = :category")
        long countByCategory(@Param("category") String category);

        @Query("SELECT SUM(p.price * p.quantity) FROM Product p")
        BigDecimal calculateTotalValue();

        @Query("SELECT AVG(p.price) FROM Product p")
        BigDecimal calculateAveragePrice();

        @Query("SELECT p FROM Product p WHERE p.quantity < :threshold")
        List<Product> findLowStockProducts(@Param("threshold") int threshold);


    // Query to find all unique categories from products
    @Query("SELECT DISTINCT p.category FROM Product p ORDER BY p.category")
    List<String> findAllCategories();

    // All basic CRUD methods inherited from JpaRepository:
    // - findAll()  // Retrieves all products
    // - findById(Long id)  // Retrieves a product by its ID
    // - save(Product product)  // Saves a product (insert or update)
    // - deleteById(Long id)  // Deletes a product by its ID
    // - count()  // Counts the total number of products
    // - existsById(Long id)  // Checks if a product exists by its ID
}
