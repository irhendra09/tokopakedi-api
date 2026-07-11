package com.demo.resource;

import com.demo.dto.PageResponse;
import com.demo.entity.Customer;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestQuery;

import java.util.List;

@Path("/customer")
public class CustomerResource {
    @POST
    @Transactional
    public Response create(@Valid Customer customer){
        customer.persist();
        return Response.ok().status(Response.Status.CREATED).entity(customer).build();
    }
    @GET
    public Response getAll(@RestQuery("page") @DefaultValue("1") int p, @RestQuery("size") @DefaultValue("10") int size){
        if (p <= 0)  p = 1;
        PanacheQuery<Customer> query = Customer.findAll().page(Page.of(p-1, size));
        PageResponse<Customer> response = new PageResponse<>(query.list(), query.count(), query.pageCount(), p, size);
        return Response.ok().entity(response).build();
    }
    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Integer id){
        PanacheEntityBase customer = Customer.findByIdOptional(id).orElseThrow(()-> new NotFoundException("Customer Not found"));
        return Response.ok(customer).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Integer id, Customer req){
        Customer customer = Customer.findById(id);
        if (customer != null){
            customer.name = req.name;
            customer.address = req.address;
            customer.phoneNumber = req.phoneNumber;
            return Response.ok().entity(customer).build();
        }
        throw new NotFoundException("Customer not fund");
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteById(@PathParam("id") Integer id){
        boolean isDeleted = Customer.deleteById(id);
        if (!isDeleted) throw new NotFoundException("Customers not found");
        return Response.ok().build();
    }

}
