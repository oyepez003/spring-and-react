package com.github.services.model;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

import lombok.Getter;

/**
 * History model.
 * @author omar.yepez
 *
 */
@KeySpace("user")
@Getter
public class History {
	
	@Id 
	String id;
	
	Calendar date; 
	
	String api;
	
	Map<String, Object> params;
	
	public History(){
		date = Calendar.getInstance();
	}

	public History(String id){
		this.id = id;
	}
	
	public History(String id, String api){
		this.id = id;
		this.api = api;
	}
	
	public History(String id, String api, Map<String, Object> params){
		this.id = id;
		this.api = api;
		this.params = params;
	}
}
