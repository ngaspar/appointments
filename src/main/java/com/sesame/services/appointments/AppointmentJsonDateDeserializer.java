package com.sesame.services.appointments;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * JSON date deserializer override - used to parse date strings in JSON objects
 * when deserializing to their corresponding wrapper objects, according to the
 * specified pattern.
 * 
 * @author ngaspar
 * @version 1.0
 */
public class AppointmentJsonDateDeserializer extends JsonDeserializer<Date> {
	@Override
	public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
			throws IOException, JsonProcessingException {
		SimpleDateFormat format = new SimpleDateFormat(BusinessRules.DATETIME_PATTERN);
		// format.setLenient(true);
		String date = jsonParser.getText();
		try {
			return format.parse(date);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
}