package slacksender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "rabbitmq" })
public class SlackMessageSender {

	public static void main(String[] args) {
		SpringApplication.run(SlackMessageSender.class, args);

	}
}