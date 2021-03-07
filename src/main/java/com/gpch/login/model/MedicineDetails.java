package com.gpch.login.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="medicineDtl")
public class MedicineDetails {


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="medicineDtlId")
	private Integer medicineDtlId;
	
	@Column(name="medicineId")
	private Integer medicineId;
	
	@Column(name="quantity")
	private Integer quantity;
	
	@Column(name="expireDate")
	private String expireDate;
	
	@Column(name="entryDate")
	private Date entryDate;
	
	@Column(name="employeeId")
	private Integer employeeId;
	
	@Column(name="storeNm")
	private Integer storeNm;
	
	

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	
	public Integer getStoreNm() {
		return storeNm;
	}

	public void setStoreNm(Integer storeNm) {
		this.storeNm = storeNm;
	}

	public Integer getMedicineDtlId() {
		return medicineDtlId;
	}

	public void setMedicineDtlId(Integer medicineDtlId) {
		this.medicineDtlId = medicineDtlId;
	}

	public Integer getMedicineId() {
		return medicineId;
	}

	public void setMedicineId(Integer medicineId) {
		this.medicineId = medicineId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	

	public String getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}
	
	
}
