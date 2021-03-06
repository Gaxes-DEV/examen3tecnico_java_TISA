package com.cenfotec.tisa.repository;

import com.cenfotec.tisa.entity.COrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface COrderRepository extends JpaRepository<COrder, Long> {
}
