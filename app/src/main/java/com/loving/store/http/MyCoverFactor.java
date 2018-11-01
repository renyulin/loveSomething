package com.loving.store.http;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class MyCoverFactor extends Converter.Factory {

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return new UserRequestBodyConverter<>();
    }

    public class UserRequestBodyConverter<T> implements Converter<T, RequestBody> {
        private Gson mGson = new Gson();

        @Override
        public RequestBody convert(T value) throws IOException {
            String string = mGson.toJson(value);
            return RequestBody.create(MediaType.parse("application/json; charset=UTF-8"), string);
        }
    }
}
