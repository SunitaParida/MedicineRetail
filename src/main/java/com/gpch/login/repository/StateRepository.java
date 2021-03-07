package com.gpch.login.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gpch.login.model.State;


@Repository
public interface StateRepository extends JpaRepository<State, Integer>{
	@Query("FROM State where status=true")
	List<State> fetchActiveState();

}
