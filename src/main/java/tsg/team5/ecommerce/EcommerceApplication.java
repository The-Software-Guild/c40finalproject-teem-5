package tsg.team5.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@Configuration
public class EcommerceApplication {
	//test comment
	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}

}
