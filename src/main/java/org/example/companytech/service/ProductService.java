package org.example.companytech.service;

import org.example.companytech.domain.Product;
import org.example.companytech.dto.req.product.ProductAddingReqDto;
import org.example.companytech.dto.req.product.ProductNumerAutoChangeReqDto;
import org.example.companytech.dto.req.product.ProductUpdatingReqDto;
import org.example.companytech.exception.ProductNotFoundException;
import org.example.companytech.exception.UnAcceptableException;
import org.example.companytech.mapper.ProductMapper;
import org.example.companytech.repo.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    public ProductService(ProductMapper productMapper, ProductRepository productRepository) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
    }

    public void adding(ProductAddingReqDto productAddingReqDto) {

        if (productAddingReqDto.getPrice().compareTo(new BigDecimal(0)) < 0) {
            throw new UnAcceptableException("Price not be less than 0");
        }

        Product entity = productMapper.toEntity(productAddingReqDto);
        entity.setCreatedAt(LocalDateTime.now());

        productRepository.save(entity);
    }

    public void update(ProductUpdatingReqDto productUpdatingReqDto) {
        Product entity = productMapper.toEntity(productUpdatingReqDto);

        productRepository.updateNameAndPriceAndDescriptionAndCountAndPlaceById(
                entity.getName(),
                entity.getPrice(),
                entity.getDescription(),
                entity.getCount(),
                entity.getPlace(),
                entity.getId());

    }

    public List<Product> getAll() {
        List<Product> all = productRepository.findAll();
        return all;
    }

    public Product getById(Long id) {
        Optional<Product> byId = productRepository.findById(id);
        if (byId.isEmpty()) {
            throw new ProductNotFoundException();
        }
        return byId.get();
    }

    public Product autoChange(ProductNumerAutoChangeReqDto productNumerAutoChangeReqDto) {
        Product product = getById(productNumerAutoChangeReqDto.getProductId());

        Integer count = product.getCount();
        Integer numberForAutoChange = productNumerAutoChangeReqDto.getNumberForAutoChange();
        if (count + numberForAutoChange < 0) {
            throw new UnAcceptableException("count must not be less than 0");
        }

        int sum = count + numberForAutoChange;
        product.setCount(sum);
        Product saved = productRepository.save(product);

        return saved;

    }
}
