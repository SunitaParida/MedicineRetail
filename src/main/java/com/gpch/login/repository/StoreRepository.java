package com.gpch.login.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gpch.login.model.Store;


public interface StoreRepository extends JpaRepository<Store, Integer>{

	@Query("FROM Store where status=true")
	List<Store> fetchActiveStore();

}
