package com.course.service;
import com.course.dto.PaymentDto;
import com.course.model.AppUser;
import com.course.model.Course;
import com.course.model.Payment;
import com.course.repository.CourseRepository;
import com.course.repository.PaymentRepository;
import com.course.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {
	private final PaymentRepository paymentRepository;
	private final CourseRepository courseRepository;
	private final UserRepository userRepository;
	
	public Long payCourse(PaymentDto paymentDto) {
		Optional<Course> course = courseRepository.findById(paymentDto.getCourseId());
		Optional<AppUser> user = userRepository.findById(paymentDto.getUserId());
		Long paymentId;
		if (course.isPresent() && user.isPresent()) {
			Payment payment = new Payment();
			payment.setCourseId(paymentDto.getCourseId());
			payment.setAmount(paymentDto.getAmount());
			payment.setUserId(user.get().getId());
			payment.setDate(LocalDate.now());
			payment.setPaymentMethod(Payment.PaymentMethod.valueOf(String.valueOf(paymentDto.getPaymentMethod())));
			paymentRepository.save(payment);
			paymentId = payment.getId();
			log.info("Payment with Id: {}", paymentId);
		} else throw new RuntimeException("course or User not found");
		return paymentId;
	}
	
	public PaymentDto getPayById(Long id) {
		Optional<Payment> payment = paymentRepository.findById(id);
		PaymentDto paymentDto = new PaymentDto();
		if (payment.isPresent()) {
			paymentDto.setAmount(payment.get().getAmount());
			paymentDto.setCourseId(payment.get().getCourseId());
			paymentDto.setUserId(payment.get().getUserId());
			paymentDto.setDate(payment.get().getDate());
			paymentDto.setPaymentMethod(payment.get().getPaymentMethod().toString());
		} else throw new RuntimeException("Payment not found");
		return paymentDto;
	}
	
}