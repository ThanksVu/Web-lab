package com.example.productmanagement.controller;

import com.example.productmanagement.entity.Product;
import com.example.productmanagement.service.ProductService;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String showDashboard(Model model) {
        // Get total product count
        long totalProducts = productService.getTotalProductCount();

        // Get total inventory value
        BigDecimal totalValue = productService.getTotalInventoryValue();

        // Get average price of products
        BigDecimal avgPrice = productService.getAveragePrice();

        // Get low stock products (threshold = 10)
        List<Product> lowStockProducts = productService.getLowStockProducts(10);

        // Add statistics to model
        model.addAttribute("totalProducts", totalProducts);
        model.addAttribute("totalValue", totalValue);
        model.addAttribute("avgPrice", avgPrice);
        model.addAttribute("lowStockProducts", lowStockProducts);

        return "dashboard";  // Returns the dashboard view
    }
}
