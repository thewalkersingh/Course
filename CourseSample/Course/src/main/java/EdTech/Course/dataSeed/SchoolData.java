package EdTech.Course.dataSeed;

import EdTech.Course.model.School;
import EdTech.Course.repository.SchoolRepository;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;

//@Configuration
@RequiredArgsConstructor
public class SchoolData {
	private final SchoolRepository schoolRepository;
	private final Faker faker = new Faker();
	
//	@Bean
	CommandLineRunner seedSchools() {
		return args -> {
			for (int i = 0; i < 50; i++) {
				School school = new School();
				school.setName(faker.university().name());
				school.setSubdomain(faker.internet().domainWord());
//				school.setSubdomain(faker.internet().domainWord() + ".campax.com");
				school.setCity(faker.address().city());
				school.setState(faker.address().state());
				school.setArea(faker.address().streetName());
				school.setPlanType(School.PlanType.valueOf(faker.options().option("FREE", "STANDARD", "PREMIUM")));
				school.setStatus(School.Status.valueOf(faker.options().option("ACTIVE", "INACTIVE")));
				
				schoolRepository.save(school);
			}
			
		};
	}
	
}