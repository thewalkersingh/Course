package EdTech.Course.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
	
	@Value("${server.port:8081}")
	private int serverPort;
//	private static final String SECURITY_SCHEME_NAME = "bearerAuth";
	
	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
					 .info(new Info()
								 .title("Campax User Service API")
								 .description("Handles user registration, authentication, profiles and appointments")
								 .version("1.0.0")
								 .contact(new Contact()
												 .name("Campax")
												 .email("dev@campax.com")))
					 .servers(List.of(
						 new Server()
							 .url("http://localhost:" + serverPort)
							 .description("Local development server")
					 ));
	}
	
}