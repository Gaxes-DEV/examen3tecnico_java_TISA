package com.cenfotec.tisa.service;

import com.cenfotec.tisa.entity.COrder;
import com.cenfotec.tisa.entity.Customer;
import com.cenfotec.tisa.repository.COrderRepository;
import com.cenfotec.tisa.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepo;
    @Autowired
    COrderRepository cOrderRepo;

    private boolean customerHasOrders(Long id){
        boolean customerHas = false;

        for(COrder obj: cOrderRepo.findAll()){
            if(obj.getCustomer_id() == id){
                if(obj.getStatus() == 0){
                    customerHas = true;
                    break;
                }
            }
        }

        return customerHas;
    }

    private boolean deleteCustomersOrders(Long id){
        boolean ordersDeleted = false;

        if(!customerHasOrders(id)) {
            for (COrder obj : cOrderRepo.findAll()) {
                if (obj.getCustomer_id() == id) {
                    cOrderRepo.deleteById(obj.getId());
                }
            }
            ordersDeleted = true;
        }

        return ordersDeleted;
    }

    public List<Customer> getAllCustomers(int count){
        return this.customerRepo.findAll().stream().limit(count).collect(Collectors.toList());
    }

    public Optional<Customer> getCustomer(long id){
        return this.customerRepo.findById(id);
    }

    public Customer addCustomer(String name, String last_Name, String housing_Address,
                                String cashing_Address, int credit_Card, int expiration_Month,
                                int expiration_Year){
        Customer customer = new Customer();
        customer.setName(name);
        customer.setLast_Name(last_Name);
        customer.setHousing_Address(housing_Address);
        customer.setCashing_Address(cashing_Address);
        customer.setCredit_Card(credit_Card);
        customer.setExpiration_Month(expiration_Month);
        customer.setExpiration_Year(expiration_Year);
        return this.customerRepo.save(customer);
    }

    public boolean deleteCustomer(long id){
        try{
            if(deleteCustomersOrders(id)) {
                this.customerRepo.deleteById(id);
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }

    public Customer updateCustomer(Long id, String name, String last_Name, String housing_Address,
                                String cashing_Address, int credit_Card, int expiration_Month,
                                int expiration_Year){
        Customer customer = new Customer();
        customer.setId(id);
        customer.setName(name);
        customer.setLast_Name(last_Name);
        customer.setHousing_Address(housing_Address);
        customer.setCashing_Address(cashing_Address);
        customer.setCredit_Card(credit_Card);
        customer.setExpiration_Month(expiration_Month);
        customer.setExpiration_Year(expiration_Year);
        return this.customerRepo.save(customer);
    }
}
