package com.cenfotec.tisa.controller;

import com.cenfotec.tisa.model.COrder;
import com.cenfotec.tisa.model.Customer;
import com.cenfotec.tisa.repository.COrderRepository;
import com.cenfotec.tisa.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/customers"})
public class CustomerController {
    private CustomerRepository repository;
    private COrderRepository cOrderRepo;
    CustomerController(CustomerRepository customerRepository, COrderRepository cOrderRepository){
        this.repository = customerRepository;
        this.cOrderRepo = cOrderRepository;
    }

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

    @GetMapping
    public List findAllCustomers(){
        return repository.findAll();
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity<Customer> findCustomerById(@PathVariable Long id){
        return repository.findById(id).map(record -> ResponseEntity.ok().body(record)).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Customer create(@RequestBody Customer customer){
        return repository.save(customer);
    }

    @PutMapping(path = {"/{id}"})
    public ResponseEntity<Customer> update(@PathVariable Long id, @RequestBody Customer customer){
        return repository.findById(id).map(record -> {
            record.setName(customer.getName());
            record.setLast_Name(customer.getLast_Name());
            record.setHousing_Address(customer.getHousing_Address());
            record.setCashing_Address(customer.getCashing_Address());
            record.setCredit_Card(customer.getCredit_Card());
            record.setExpiration_Month(customer.getExpiration_Month());
            record.setExpiration_Year(customer.getExpiration_Year());
            Customer updated = repository.save(record);
            return ResponseEntity.ok().body(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity<?> delete(@PathVariable Long id){
        try {
            if(deleteCustomersOrders(id)) {
                return repository.findById(id).map(record -> {
                    repository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
            }else{
                return ResponseEntity.notFound().build();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
