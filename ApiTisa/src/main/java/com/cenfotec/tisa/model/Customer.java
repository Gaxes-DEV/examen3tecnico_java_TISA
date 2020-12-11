package com.cenfotec.tisa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String last_Name;
    private String housing_Address;
    private String cashing_Address;
    private int credit_Card;
    private int expiration_Month;
    private int expiration_Year;

    @OneToMany(targetEntity = COrder.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Set<COrder> cOrders;
}
