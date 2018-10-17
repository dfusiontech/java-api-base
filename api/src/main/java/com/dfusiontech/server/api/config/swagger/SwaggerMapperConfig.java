package com.dfusiontech.server.api.config.swagger;

import com.dfusiontech.server.rest.exception.BadRequestException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.joda.time.DateTime;
import org.springframework.context.ApplicationListener;
import springfox.documentation.schema.configuration.ObjectMapperConfigured;

import java.io.IOException;

public class SwaggerMapperConfig implements ApplicationListener<ObjectMapperConfigured> {

	@Override
	public void onApplicationEvent(ObjectMapperConfigured event) {
		ObjectMapper mapper = event.getObjectMapper();
		mapper.registerModule(customDeserializerModule());
	}

	private SimpleModule customDeserializerModule() {
		return new SimpleModule("SwaggerCustomModule")
			.addSerializer(DateTime.class, new DateTimeSerializer())
			.addDeserializer(DateTime.class, new DateTimeDeserializer());
	}

	public static class DateTimeSerializer extends JsonSerializer<DateTime> {
		@Override
		public void serialize(DateTime value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
			if (value != null) {
				jgen.writeString(value.toString());
			} else {
				jgen.writeNull();
			}
		}
	}

	public class DateTimeDeserializer extends JsonDeserializer<DateTime> {

		@Override
		public DateTime deserialize(JsonParser jsonparser, DeserializationContext deserializationcontext) throws IOException {

			String date = jsonparser.getText();
			try {
				return new DateTime(date);
			} catch (Exception e) {
				throw new BadRequestException(e.getMessage());
			}

		}

	}
}

