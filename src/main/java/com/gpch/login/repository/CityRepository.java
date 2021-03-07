package com.gpch.login.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gpch.login.model.City;


public interface CityRepository  extends JpaRepository<City, Integer>{

	@Query("FROM City where stateId=?1 and status=true")
	List<City> fetchActiveCities(Integer stateId);

}
