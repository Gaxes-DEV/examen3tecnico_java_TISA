package com.cenfotec.tisa.mutation;

import com.cenfotec.tisa.entity.Customer;
import com.cenfotec.tisa.service.CustomerService;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerMutation implements GraphQLMutationResolver {
    @Autowired
    private CustomerService customerService;

    public Customer addCustomer(String name, String last_Name, String housing_Address,
                               String cashing_Address, int credit_Card, int expiration_Month,
                               int expiration_Year){
        return this.customerService.addCustomer(name, last_Name, housing_Address, cashing_Address, credit_Card,
                expiration_Month, expiration_Year);
    }

    public boolean deleteCustomer(Long id){
        return this.customerService.deleteCustomer(id);
    }

    public Customer updateCustomer(Long id, String name, String last_Name, String housing_Address,
                                String cashing_Address, int credit_Card, int expiration_Month,
                                int expiration_Year){
        return this.customerService.updateCustomer(id, name, last_Name, housing_Address, cashing_Address, credit_Card,
                expiration_Month, expiration_Year);
    }
}
