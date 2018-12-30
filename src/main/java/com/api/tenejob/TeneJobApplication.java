package com.api.tenejob;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by p4pupro on 20/12/2018.
 */
@Controller
@SpringBootApplication
@EnableJpaAuditing
public class TeneJobApplication {

	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "Hello!!  go to: " + " http://localhost:8080/api/matching";
	}

	public static void main(String[] args) {
		SpringApplication.run(TeneJobApplication.class, args);
	}

}
