package com.gpch.login.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="store")
public class Store {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="storeId")
	private Integer storeId;
	
	@Column(name="countryId")
	private Integer countryId;
	
	@Column(name="stateId")
	private Integer stateId;
	
	@Column(name="cityId")
	private Integer cityId;
	
	@Column(name="storeName")
	private String storeName;
	
	@Column(name="storeDescription")
	private String storeDescription;
	
	@Column(name="status")
	private Boolean status;
	
	
	
	@OneToOne
	 @JoinColumn(updatable =false,insertable = false,name="stateId", referencedColumnName = "stateid")
	  private State state;
	
	@OneToOne
	 @JoinColumn(updatable =false,insertable = false,name="cityId", referencedColumnName = "cityId")
	  private City city;

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getStoreDescription() {
		return storeDescription;
	}

	public void setStoreDescription(String storeDescription) {
		this.storeDescription = storeDescription;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}
	
	


}
