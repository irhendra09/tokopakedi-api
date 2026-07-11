package com.demo.resource;

import com.demo.dto.ErrorResponse;
import com.demo.dto.PageResponse;
import com.demo.dto.ProductRequest;
import com.demo.dto.SearchProductRequest;
import com.demo.entity.Product;
import com.demo.repository.ProductRepository;
import com.demo.service.ProductService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestQuery;

@Path("/product")
public class ProductResource {
    @Inject
    ProductService productService;

    @POST
    @Transactional
    public Response add(@Valid ProductRequest request){
        Product product = productService.add(request);
        return Response.ok().status(201).entity(product).build();
    }

    @GET
    public Response getAll(@RestQuery("page") @DefaultValue("1") int page,
                           @RestQuery("size") @DefaultValue("10") int size,
                           @RestQuery("name") String name,
                           @RestQuery("minPrice") Double minPrice,
                           @RestQuery("maxPrice") Double maxPrice
    ){
        SearchProductRequest request = new SearchProductRequest(page, size, name, minPrice, maxPrice);
        PageResponse<Product> data = productService.filter(request);
        return Response.ok().entity(data).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") long id){
        Product product = productService.getById(id);
        return Response.ok().entity(product).build();
    }

    @PUT
    public Response update(Product request){
        Product product = productService.updateById(request);
        return Response.ok().entity(product).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id){
        productService.delete(id);
        return Response.ok().entity("Deleted Successfully").build();
    }

}
