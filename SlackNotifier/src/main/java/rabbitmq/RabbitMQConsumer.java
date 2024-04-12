package rabbitmq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import util.dto.DtoSlack;

@Service
public class RabbitMQConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConsumer.class);
    
	private static final String SLACK_API_URL = "https://hooks.slack.com/services/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";
	private static final String SLACK_API_TOKEN = "xapp-1-A06LFGUC6PR-6703838500886-444c5b1679fcc406fecc42587b60f66dee44ebe54e29bb973ce8ce4227121ecd";
	private static final String CHANNEL_ID = "6709910302498.6695572414807";

    @RabbitListener(queues = {"${rabbitmq.queue.slack.name}"})
    public void consume(String slack) throws JsonMappingException, JsonProcessingException{
    	ObjectMapper objectMapper = new ObjectMapper();

		// Deserijalizacija JSON stringa u objekat
		DtoSlack dtoSlack = objectMapper.readValue(slack,
				new TypeReference<DtoSlack>() {
				});

		
		OkHttpClient client = new OkHttpClient();

		MediaType mediaType = MediaType.parse("application/json");
		
		RequestBody body = RequestBody.create(mediaType,
				"{\"channel\":\"" + CHANNEL_ID + "\",\"text\":\"" + dtoSlack.getMessage() + "\"}");
		Request request = new Request.Builder().url(SLACK_API_URL).post(body)
				.header("Authorization", "Bearer " + SLACK_API_TOKEN).build();

		try {
			Response response = client.newCall(request).execute();
			System.out.println("Message sent successfully: " + response.body().string());
		} catch (Exception e) {
			System.err.println("Error sending message: " + e.getMessage());
		}
    }
}