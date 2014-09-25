package com.wataru420.helloworld.core;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class PlayHistoryWithC {
	
	private Integer id;

	
	private Integer count;



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	@JsonProperty("times")
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}


}
