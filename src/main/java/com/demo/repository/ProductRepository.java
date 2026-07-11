package com.demo.repository;

import com.demo.dto.SearchProductRequest;
import com.demo.entity.Product;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class ProductRepository implements PanacheRepository<Product> {
    public PanacheQuery<Product> filterProduct(SearchProductRequest request){
        List<String> conditions = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();

        if (request.name()!=null){
            conditions.add("lower(name) like :name");
            params.put("name", "%" + request.name().toLowerCase() + "%");
        }
        if (request.minPrize()!= null){
            conditions.add("price >= :minPrice");
            params.put("minPrice", request.minPrize());
        }
        if (request.maxPrize()!= null){
            conditions.add("price <= :maxPrice");
            params.put("maxPrice", request.maxPrize());
        }
        String hql = conditions.isEmpty()? "": String.join(" and ", conditions);
        return hql.isBlank() ? findAll():find(hql, params);
    }
}
