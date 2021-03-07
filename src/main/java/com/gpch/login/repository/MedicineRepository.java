package com.gpch.login.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gpch.login.model.Medicine;


public interface MedicineRepository extends JpaRepository<Medicine, Integer> {

	@Query("FROM Medicine where status=true")
	List<Medicine> fetchActiveMedicine();

}
