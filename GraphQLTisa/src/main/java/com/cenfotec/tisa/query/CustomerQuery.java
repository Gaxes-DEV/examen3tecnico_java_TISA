package com.cenfotec.tisa.query;

import com.cenfotec.tisa.entity.Customer;
import com.cenfotec.tisa.service.CustomerService;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CustomerQuery implements GraphQLQueryResolver {
    @Autowired
    private CustomerService customerService;

    public List<Customer> getCustomers(int count){
        return this.customerService.getAllCustomers(count);
    }

    public Optional<Customer> getCustomer(Long id){
        return this.customerService.getCustomer(id);
    }
}
