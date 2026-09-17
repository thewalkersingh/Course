package EdTech.Course.repository;

import EdTech.Course.model.Payment;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
	Optional<Payment> findById(@NonNull String id);
	
}