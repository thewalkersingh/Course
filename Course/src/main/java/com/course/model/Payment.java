package com.course.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long userId;
	private Long courseId;
	private LocalDate date;
	private Double amount;
	
	@Enumerated(EnumType.STRING)
	private PaymentMethod paymentMethod;
	
	public enum PaymentMethod {
		CREDIT_CARD,
		DEBIT_CARD,
		UPI,
		NET_BANKING,
		WALLET
	}
	
}