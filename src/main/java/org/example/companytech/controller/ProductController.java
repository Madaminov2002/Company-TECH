package org.example.companytech.controller;

import jakarta.validation.Valid;
import org.example.companytech.domain.Product;
import org.example.companytech.dto.req.product.ProductAddingReqDto;
import org.example.companytech.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
public class ProductController {


    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody @Valid ProductAddingReqDto productAddingReqDto) {

        System.out.println("Ishliyor");
        productService.adding(productAddingReqDto);

        return ResponseEntity.ok("Ok");

    }


}
