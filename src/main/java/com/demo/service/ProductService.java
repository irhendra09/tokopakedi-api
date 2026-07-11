package com.demo.service;

import com.demo.dto.PageResponse;
import com.demo.dto.ProductRequest;
import com.demo.dto.SearchProductRequest;
import com.demo.entity.Product;
import com.demo.repository.ProductRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class ProductService {
    @Inject
    ProductRepository productRepository;

    @Transactional
    public Product add(ProductRequest request){
        Product product = new Product();
        product.setName(request.name());
        product.setPrice(request.price());
        product.setStock(request.stock());
        productRepository.persist(product);
        return product;
    }

    public Product getById(Long id){
        return productRepository.findByIdOptional(id).orElseThrow(()-> new NotFoundException("Product Not Found"));
    }

    public PageResponse<Product> filter(SearchProductRequest request){
        int page = request.page() - 1;
        PanacheQuery<Product> query = productRepository.filterProduct(request);
        query.page(page, request.size());

        return new PageResponse<Product>(query.list(), query.count(), query.pageCount(), request.page(), request.size());
    }
    @Transactional
    public Product updateById(Product request){
        Product product = productRepository.findByIdOptional(request.getId()).orElseThrow(()-> new NotFoundException("Product Not Found"));
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        return product;
    }

    public void delete(Long id){
        Product product = productRepository.findByIdOptional(id).orElseThrow(()-> new NotFoundException("Product Not Found"));
        productRepository.delete(product);
    }
}
