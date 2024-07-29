package org.example.companytech.controller;

import jakarta.validation.Valid;
import org.example.companytech.domain.Product;
import org.example.companytech.dto.req.product.ProductAddingReqDto;
import org.example.companytech.dto.req.product.ProductNumerAutoChangeReqDto;
import org.example.companytech.dto.req.product.ProductUpdatingReqDto;
import org.example.companytech.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@PreAuthorize("hasAnyRole('SUPER ADMIN','EMPLOYEE')")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody @Valid ProductAddingReqDto productAddingReqDto) {

        productService.adding(productAddingReqDto);

        return ResponseEntity.ok("Added Successfully");

    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    @Secured("EMPLOYEE")
    @GetMapping("/get/by/id/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getById(id));
    }


    @PostMapping("/update")
    public ResponseEntity<String> add(@RequestBody @Valid ProductUpdatingReqDto productUpdatingReqDto) {

        productService.update(productUpdatingReqDto);

        return ResponseEntity.ok("Updated Successfully");
    }

    @PostMapping("/product-count/auto-change")
    public ResponseEntity<Product> autoChange(@RequestBody @Valid ProductNumerAutoChangeReqDto productNumerAutoChangeReqDto) {

        Product savedEntity = productService.autoChange(productNumerAutoChangeReqDto);

        return ResponseEntity.ok(savedEntity);

    }


}
