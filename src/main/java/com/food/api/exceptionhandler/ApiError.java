package com.food.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;

@JsonInclude(Include.NON_NULL)
@Getter
@Builder
public class ApiError {
	
	private Integer status;
	private String type;
	private String title;
	private String detail;
	private String userMessage;
	
	@Default
	private OffsetDateTime timestamp = OffsetDateTime.now();
	
	private List<Field> objects;
	
	
	

}