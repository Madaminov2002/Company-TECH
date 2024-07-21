package org.example.companytech.service;

import org.example.companytech.dto.req.product.ProductAddingReqDto;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    public void adding(ProductAddingReqDto productAddingReqDto) {

        System.out.println("productAddingReqDto = " + productAddingReqDto);
    }
}
