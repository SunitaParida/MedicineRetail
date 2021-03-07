package com.gpch.login.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="soldMedicineDtl")
public class SoldMedicineDetails {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="soldId")
	private Integer soldId;
	
	@Column(name="medicineId")
	private Integer medicineId;
	
	@Column(name="quantity")
	private Integer quantity;
	
	@Column(name="dateOfPurchase")
	private Date dateOfPurchase;
	
	@Column(name="employeeId")
	private Integer employeeId;
	
	@Column(name="storeId")
	private Integer storeId;
	
	@Column(name="customerNm")
	private String customerNm;
	
	@Column(name="customerPhNo")
	private String customerPhNo;
	
	@Column(name="totalAmount",precision = 10,scale = 2)
	private BigDecimal totalAmount;

	@Transient
	private BigDecimal price ;
	
	
	
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getSoldId() {
		return soldId;
	}

	public void setSoldId(Integer soldId) {
		this.soldId = soldId;
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

	public Date getDateOfPurchase() {
		return dateOfPurchase;
	}

	public void setDateOfPurchase(Date dateOfPurchase) {
		this.dateOfPurchase = dateOfPurchase;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public String getCustomerNm() {
		return customerNm;
	}

	public void setCustomerNm(String customerNm) {
		this.customerNm = customerNm;
	}

	public String getCustomerPhNo() {
		return customerPhNo;
	}

	public void setCustomerPhNo(String customerPhNo) {
		this.customerPhNo = customerPhNo;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	
	
	
}
