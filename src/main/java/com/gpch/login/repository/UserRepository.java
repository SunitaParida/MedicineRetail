package com.gpch.login.repository;


import com.gpch.login.model.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	/*
	 * User findByEmail(String email);
	 */    User findByUserName(String userName);
    
    @Query("FROM User where active=true and id is not :userId")
	List<User> findAllEmployee(@Param("userId") Integer userId);
    
    @Query("FROM User where active=true ")
	List<User> findAllEmployee();
    @Query("FROM User  order by id desc")
	List<User> findAllEmployeeDetail();
    
    @Query("FROM User where userName=?1 ")
	Optional<User> findByUserNm(String name);
    
    @Query("FROM User where userName=?1 and email=?2")
	Optional<User> findByUserNmAndEmail(String userName, String email);

    @Query("FROM User where email=?1")
	Optional<User> findByEmail(String email);
    
    @Query(nativeQuery = true,value="SELECT name,profilePic FROM users where DATE_FORMAT(dob, '%m-%d') = DATE_FORMAT(CURDATE() , '%m-%d')")
    List<Object[]> findTodaysBirthday();



    @Query("FROM User where externalUserId=?1")
	User findBYUserId(Integer userId);
	
    
    
    
    
	
  
    
  
}