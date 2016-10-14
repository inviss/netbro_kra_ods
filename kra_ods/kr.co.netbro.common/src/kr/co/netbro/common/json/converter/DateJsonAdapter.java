package kr.co.netbro.common.json.converter;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class DateJsonAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {

	private final DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM);

	@Override
	public JsonElement serialize(Date date, Type type, JsonSerializationContext context) {
		String dateFormatAsString = format.format(date);
		return new JsonPrimitive(dateFormatAsString);
	}

	@Override
	public Date deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
		if (!(json instanceof JsonPrimitive)) {
			throw new JsonParseException("The date should be a string value");
		}

		try {
			return format.parse(json.getAsString());
		} catch (ParseException e) {
			throw new JsonParseException(e);
		}
	}
}