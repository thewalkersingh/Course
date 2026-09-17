package com.course.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {
	private Long userId;
	private Long courseId;
	private LocalDate date;
	private Double amount;
	private String paymentMethod;
	
}