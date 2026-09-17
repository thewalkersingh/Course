package EdTech.Course.service;

import EdTech.Course.dto.PaymentDto;
import EdTech.Course.dto.ResponseMessage;
import EdTech.Course.model.Course;
import EdTech.Course.model.Payment;
import EdTech.Course.model.Student;
import EdTech.Course.repository.CourseRepository;
import EdTech.Course.repository.PaymentRepository;
import EdTech.Course.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class PaymentService {
	private final PaymentRepository paymentRepository;
	private final StudentRepository studentRepository;
	private final CourseRepository courseRepository;
	
	public PaymentService(PaymentRepository paymentRepository, StudentRepository studentRepository,
		CourseRepository courseRepository) {
		this.paymentRepository = paymentRepository;
		this.studentRepository = studentRepository;
		this.courseRepository = courseRepository;
	}
	
	public ResponseMessage makePayment(PaymentDto paymentDto) {
		Long studentId = paymentDto.getStudentId();
		Long courseId = paymentDto.getCourseId();
		if (checkStudentAndCourseIsValid(studentId, courseId)) {
			Payment payment = new Payment();
			payment.setStudentId(paymentDto.getStudentId());
			payment.setCourseId(paymentDto.getCourseId());
			payment.setDate(paymentDto.getDate());
			payment.setAmount(paymentDto.getAmount());
			payment.setDescription(paymentDto.getDescription());
			payment.setPaymentMethod(paymentDto.getPaymentMethod());
		}
		return new ResponseMessage("Your Payment is Done we will notify you once it's Approved");
	}
	
	public PaymentDto getPaymentById(String id) {
		Optional<Payment> paymentDetails = paymentRepository.findById(id);
		PaymentDto paymentDto = new PaymentDto();
		paymentDto.setAmount(paymentDetails.get().getAmount());
		paymentDto.setDescription(paymentDetails.get().getDescription());
		paymentDto.setStudentId(paymentDetails.get().getStudentId());
		paymentDto.setCourseId(paymentDetails.get().getCourseId());
		paymentDto.setPaymentMethod(paymentDetails.get().getPaymentMethod());
		paymentDto.setDate(paymentDetails.get().getDate());
		log.info("Payment details: " + paymentDetails.get());
		return paymentDto;
	}
	
	// verify if student is valid
	private boolean checkStudentAndCourseIsValid(Long studentId, Long courseId) {
		Student student = studentRepository.findById(studentId).orElse(null);
		Course course = courseRepository.findById(courseId).orElse(null);
		
		return student != null && course != null;
	}
	
}