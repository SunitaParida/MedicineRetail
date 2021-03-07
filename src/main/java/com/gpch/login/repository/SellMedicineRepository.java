package com.gpch.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gpch.login.model.SoldMedicineDetails;

public interface SellMedicineRepository extends JpaRepository<SoldMedicineDetails, Integer> {

}
