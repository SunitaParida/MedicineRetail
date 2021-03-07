package com.gpch.login.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="medicine")
public class Medicine {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="medicineid")
	private Integer medicineId;
	
	@Column(name="medicinename")
	private String medicineName;
	
	@Column(name="medicinePrice",precision = 10,scale = 2)
	private BigDecimal medicinePrice;
	
	@Column(name="medicinedescription")
	private String medicineDescription;
	
	@Column(name="status")
	private Boolean status;

	
	

	public BigDecimal getMedicinePrice() {
		return medicinePrice;
	}

	public void setMedicinePrice(BigDecimal medicinePrice) {
		this.medicinePrice = medicinePrice;
	}

	public Integer getMedicineId() {
		return medicineId;
	}

	public void setMedicineId(Integer medicineId) {
		this.medicineId = medicineId;
	}

	public String getMedicineName() {
		return medicineName;
	}

	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}

	public String getMedicineDescription() {
		return medicineDescription;
	}

	public void setMedicineDescription(String medicineDescription) {
		this.medicineDescription = medicineDescription;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	
}
