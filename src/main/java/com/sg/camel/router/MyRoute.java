package com.sg.camel.router;

import java.util.StringTokenizer;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MyRoute extends RouteBuilder {

	private final String INPUT_PATH = "{{path.inbound}}";

	private final String OUTPUT_PATH = "{{path.outbound}}";

	@Override
	public void configure() throws Exception {
		from(INPUT_PATH).process((exchange) -> {
			Message input = exchange.getIn();
			String data = input.getBody(String.class);

			StringTokenizer str = new StringTokenizer(data, ",");

			String name = str.nextToken();
			String age = str.nextToken();
			String email = str.nextToken();

			StringBuilder result = new StringBuilder();

			result.append("{\"name\":\"");
			result.append(name);
			result.append("\",");

			result.append("\"age\":");
			result.append(age);
			result.append(",");

			result.append("\"email\":\"");
			result.append(email);
			result.append("\"}");

			Message ouput = exchange.getMessage();
			ouput.setBody(result.toString());
		}).to(OUTPUT_PATH + "?fileName=test.json");
	}
}
