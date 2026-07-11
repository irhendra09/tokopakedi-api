package com.demo.resource;

import com.demo.dto.OrderRequest;
import com.demo.dto.PageResponse;
import com.demo.dto.SearchOrderRequest;
import com.demo.dto.SearchProductRequest;
import com.demo.entity.Orders;
import com.demo.service.OrderService;
import jakarta.inject.Inject;
import jakarta.validation.constraints.Past;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.jboss.resteasy.reactive.RestQuery;

@Path("/order")
public class OrderResource {
    @Inject
    OrderService orderService;

    @POST
    public Response add(OrderRequest orderRequest){
        Orders orders= orderService.create(orderRequest);
        return Response.ok().status(201).entity(orders).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") long id){
        Orders orders = orderService.getById(id);
        return Response.ok().entity(orders).build();
    }

    @GET
    public Response getAll(@RestQuery("page") @DefaultValue("1") int page,
                           @RestQuery("size") @DefaultValue("10") int size,
                           @RestQuery("customerId") long customerId){
        SearchOrderRequest orderRequest = new SearchOrderRequest(page, size, customerId);
        PageResponse<Orders> ordersPageResponse = orderService.getAll(orderRequest);
        return Response.ok().entity(ordersPageResponse).build();
    }
}
