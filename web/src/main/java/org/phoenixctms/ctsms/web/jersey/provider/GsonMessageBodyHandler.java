package org.phoenixctms.ctsms.web.jersey.provider;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.phoenixctms.ctsms.js.JsUtil;
import org.phoenixctms.ctsms.web.jersey.wrapper.NoShortcutSerializationWrapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.stream.JsonWriter;

@Provider
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GsonMessageBodyHandler implements MessageBodyReader<Object>, MessageBodyWriter<Object> {

	private enum GsonTypes {
		SERIALIZER, SHORTCUT_SERIALIZER, DESERIALIZER,
	}

	private final static String API_JSON_SERIALIZE_VALUE_CHARSET = "UTF8";
	private final static String API_JSON_DESERIALIZE_VALUE_CHARSET = "UTF8";
	private final static String API_JSON_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	private Gson serializer;
	private Gson shortcutSerializer;
	private Gson deserializer;

	private Gson buildGson(GsonTypes type) {
		GsonBuilder builder = new GsonBuilder();
		// serializer and deserializer common properties:
		builder.setDateFormat(API_JSON_DATETIME_PATTERN);
		if (GsonTypes.DESERIALIZER.equals(type)) { // || type == GsonTypes.FANCY_DESERIALIZER
			// deserializer only
			// https://gist.github.com/miere/3202425
			builder.registerTypeAdapter(Collection.class, new JsonDeserializer<Collection<?>>() {

				@Override
				public Collection<?> deserialize(JsonElement json, Type typeOfT,
						JsonDeserializationContext context) throws JsonParseException {
					Type collectionType = ((ParameterizedType) typeOfT).getActualTypeArguments()[0];
					return parseAsArrayList(json, collectionType, context);
				}

				@SuppressWarnings("unchecked")
				public <T> ArrayList<T> parseAsArrayList(JsonElement collection, T type, JsonDeserializationContext context) {
					ArrayList<T> result = new ArrayList<T>();
					Iterator<JsonElement> it = collection.getAsJsonArray().iterator();
					while (it.hasNext()) {
						result.add((T) context.deserialize(it.next(), (Class<?>) type));
					}
					return result;
				}
			});
			return builder.create();
		} else {
			// serializers:
			builder.serializeNulls();
			builder.setExclusionStrategies(JsUtil.GSON_EXCLUSION_STRATEGIES);
			if (GsonTypes.SHORTCUT_SERIALIZER.equals(type)) {
				builder.registerTypeAdapter(NoShortcutSerializationWrapper.class, new JsonSerializer<NoShortcutSerializationWrapper>() {

					@Override
					public JsonElement serialize(NoShortcutSerializationWrapper src, Type typeOfSrc, JsonSerializationContext context) {
						return getSerializer().toJsonTree(src.getVo()); // use regular serializer
					}
				});
				JsUtil.registerGsonTypeAdapters(builder, JsUtil.GSON_SHORTCUT_SERIALISATIONS);
				return builder.create();
			} else if (GsonTypes.SERIALIZER.equals(type)) {
				builder.registerTypeAdapter(NoShortcutSerializationWrapper.class, new JsonSerializer<NoShortcutSerializationWrapper>() {

					@Override
					public JsonElement serialize(NoShortcutSerializationWrapper src, Type typeOfSrc, JsonSerializationContext context) {
						return context.serialize(src.getVo());
					}
				});
				return builder.create();
			}
		}
		return null;
	}

	private Gson getDeserializer() {
		if (deserializer == null) {
			deserializer = buildGson(GsonTypes.DESERIALIZER);
		}
		return deserializer;
	}

	private Gson getSerializer() {
		if (serializer == null) {
			serializer = buildGson(GsonTypes.SERIALIZER);
		}
		return serializer;
	}

	private Gson getShortcutSerializer() {
		if (shortcutSerializer == null) {
			shortcutSerializer = buildGson(GsonTypes.SHORTCUT_SERIALIZER);
		}
		return shortcutSerializer;
	}

	@Override
	public long getSize(Object t, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return -1;
	}

	@Override
	public boolean isReadable(Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return true;
	}

	@Override
	public boolean isWriteable(Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return true;
	}

	@Override
	public Object readFrom(Class<Object> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
			throws IOException, WebApplicationException {
		InputStreamReader r = new InputStreamReader(entityStream, API_JSON_DESERIALIZE_VALUE_CHARSET);
		return getDeserializer().fromJson(r, genericType); // type);
	}

	@Override
	public void writeTo(Object t, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream) throws IOException,
			WebApplicationException {
		final Writer w = new OutputStreamWriter(entityStream, API_JSON_SERIALIZE_VALUE_CHARSET);
		final JsonWriter jsw = new JsonWriter(w);
		getShortcutSerializer().toJson(t, type, jsw);
		jsw.close();
	}
}