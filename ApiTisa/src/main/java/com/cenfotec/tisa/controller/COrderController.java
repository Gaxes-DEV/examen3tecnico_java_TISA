package com.cenfotec.tisa.controller;

import com.cenfotec.tisa.model.COrder;
import com.cenfotec.tisa.repository.COrderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/orders"})
public class COrderController {

    private COrderRepository repository;

    COrderController(COrderRepository cOrderRepository){
        this.repository = cOrderRepository;
    }

    //Return the total price of the items
    private int getTotalPriceForItems(int pQUantity, String pItem){
        int price = 0;
        switch (pItem){
            case "Tasa":
                price = 15;
                break;
            case "Camiseta":
                price = 9;
                break;
            case "Almohadon":
                price = 8;
                break;
            case "Vaso":
                price = 13;
                break;
        }

        price *= pQUantity;

        return price;
    }

    @GetMapping
    public List findAllOrders(){
        return repository.findAll();
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity<COrder> findOrderById(@PathVariable Long id){
        return repository.findById(id).map(record -> ResponseEntity.ok().body(record)).orElse(ResponseEntity.notFound().build());
    }

//    @GetMapping(path = {"/item/{name}"})
//    public List findOrderByItem(@PathVariable String name){
//
//        List<COrder> list = null;
//        int i = 0;
//
//        for(COrder obj: repository.findAll()){
//            if(obj.getProduct() != name){
//                list.add(i, obj);
//            }
//            i++;
//        }
//
//        return list;
//    }


    @PostMapping(path = {"/{id}"})
    public COrder create(@RequestBody COrder cOrder, @PathVariable Long id){
        if(cOrder.getProduct() != null && id > 0) {
            cOrder.setCustomer_id(id);
            cOrder.setTotal_Price(getTotalPriceForItems(cOrder.getQuantity(), cOrder.getProduct()));
            return repository.save(cOrder);
        }else{
            return null;
        }
    }

    @PutMapping(path = {"/{id}"})
    public ResponseEntity<COrder> update(@PathVariable Long id, @RequestBody COrder cOrder){
        return repository.findById(id).map(record -> {
            record.setProduct(cOrder.getProduct());
            record.setQuantity(cOrder.getQuantity());
            record.setTotal_Price(getTotalPriceForItems(cOrder.getQuantity(), cOrder.getProduct()));
            record.setStatus(cOrder.getStatus());
            COrder updated = repository.save(record);
            return ResponseEntity.ok().body(updated);
        }).orElse(ResponseEntity.notFound().build());
    }
}
