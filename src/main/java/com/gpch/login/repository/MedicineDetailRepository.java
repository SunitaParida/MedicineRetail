package com.gpch.login.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gpch.login.model.MedicineDetails;

public interface MedicineDetailRepository extends JpaRepository<MedicineDetails, Integer>{

    @Query(nativeQuery = true,value="SELECT * FROM medicinedtl where storeNm=?1 and medicineId=?2 and quantity !=0 order by medicineDtlId asc limit 1;\r\n" + 
    		"")
	Optional<MedicineDetails> findData(Integer storeId, Integer medicineId);

    @Query(nativeQuery = true,value="SELECT sum(quantity),expireDate FROM medicinedtl where storeNm=?1 and medicineId=?2 and quantity !=0 and DATE_FORMAT(expireDate, '%m-%d') < DATE_FORMAT(CURDATE() , '%m-%d');\r\n" + 
    		"")
	Integer findByDate(Integer storeId, Integer medicineId);
    @Query(nativeQuery = true,value="SELECT sum(quantity) FROM medicinedtl where storeNm=?1 and medicineId=?2 ;")
	Integer availableMedicine(int storeId, int medicineId);

    
    @Query(nativeQuery = true,value="SELECT \r\n" + 
    		" ( select IFNULL(count(*), 0) from medicinedtl where MONTH(entryDate)=1)  AS January,\r\n" + 
    		"  (select IFNULL(count(*), 0) from medicinedtl where MONTH(entryDate)=2)  AS February,\r\n" + 
    		"( select IFNULL(count(*), 0) from medicinedtl where MONTH(entryDate)=3 ) AS March,\r\n" + 
    		" (select IFNULL(count(*), 0) from medicinedtl where MONTH(entryDate)=4) AS April,\r\n" + 
    		"(select IFNULL(count(*), 0) from medicinedtl where MONTH(entryDate)=5) AS May,\r\n" + 
    		"  (select IFNULL(count(*), 0) from medicinedtl where MONTH(entryDate)=6)AS June,\r\n" + 
    		"  (select IFNULL(count(*), 0) from medicinedtl where MONTH(entryDate)=7) AS July,\r\n" + 
    		"( select IFNULL(count(*), 0) from medicinedtl where MONTH(entryDate)=8) AS August,\r\n" + 
    		" ( select IFNULL(count(*), 0) from medicinedtl where MONTH(entryDate)=9) AS September,\r\n" + 
    		" ( select IFNULL(count(*), 0) from medicinedtl where MONTH(entryDate)=10) AS October,\r\n" + 
    		"(select IFNULL(count(*), 0) from medicinedtl where MONTH(entryDate)=11) AS November,\r\n" + 
    		" ( select IFNULL(count(*), 0) from medicinedtl where MONTH(entryDate)=12) AS December\r\n" + 
    		"FROM medicinedtl\r\n" + 
    		"WHERE medicineDtlId IS NOT NULL AND YEAR(entryDate) = year(curdate())\r\n" + 
    		"GROUP BY medicineDtlId limit 1;\r\n" + 
    		" #select year(curdate());\r\n" + 
    		"# select IFNULL(count(*), 0) from medicinedtl where MONTH(entryDate)=2" + 
    		" ")
	Object[] getDashboardDetails();

}

