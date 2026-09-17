package com.course.dataseed;

import com.course.model.Course;
import com.course.model.Payment;
import com.course.repository.CourseRepository;
import com.course.repository.PaymentRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;

import java.time.ZoneId;
import java.util.List;
import java.util.Locale;
import java.util.Random;

//@Component
public class PaymentData {
	
	private final Faker faker = new Faker(new Locale("en-GB"));
	private final Random random = new Random();
	
	//	@Bean
	CommandLineRunner seedPayments(
		PaymentRepository paymentRepository,
		CourseRepository courseRepository) {
		
		return args -> {
			if (paymentRepository.count() > 0) {
				return;
			}
			List<Course> courses = courseRepository.findAll();
			for (int i = 0; i < 50; i++) {
				Course course = courses.get(random.nextInt(courses.size()));
				Payment payment = new Payment();
				payment.setUserId(faker.number().numberBetween(1L, 10L));
				payment.setCourseId(course.getId());
				
				payment.setDate(faker.date()
											.birthday()
											.toInstant()
											.atZone(ZoneId.systemDefault())
											.toLocalDate());
				
				payment.setAmount(course.getAmount().doubleValue());
				
				payment.setPaymentMethod(Payment.PaymentMethod.values()[random.nextInt(
					Payment.PaymentMethod.values().length)]);
				
				paymentRepository.save(payment);
			}
		};
	}
	
}