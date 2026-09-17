package EdTech.Course.controller;

import EdTech.Course.dto.PaymentDto;
import EdTech.Course.dto.ResponseMessage;
import EdTech.Course.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/payment")
public class PaymentController {
	private final PaymentService paymentService;
	
	public PaymentController(PaymentService paymentService) {
		this.paymentService = paymentService;
	}
	
	@PostMapping("/pay")
	public ResponseMessage makePayment(@RequestBody PaymentDto paymentDto) {
		return paymentService.makePayment(paymentDto);
	}
	
	@GetMapping("/{id}")
	public PaymentDto getPaymentById(@PathVariable String id) {
		log.info("Fetching Pay Details of ID: "+id);
		return paymentService.getPaymentById(id);
	}
	
}