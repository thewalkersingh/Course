package com.course.controller;
import com.course.dto.PaymentDto;
import com.course.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
	private final PaymentService paymentService;
	
	@PostMapping("/{courseId}")
	public Long payment(@PathVariable Long courseId, @RequestBody PaymentDto paymentDto) {
		log.info("Payment request for course {} with amount {}", paymentDto.getCourseId(), paymentDto.getAmount());
		paymentDto.setCourseId(courseId);
		return paymentService.payCourse(paymentDto);
		
	}
	
	@GetMapping("/{id}")
	public PaymentDto payment(@PathVariable Long id) {
		log.info("Payment request for course {}", id);
		return paymentService.getPayById(id);
	}
	
}