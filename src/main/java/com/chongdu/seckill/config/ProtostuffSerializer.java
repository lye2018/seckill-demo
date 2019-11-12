package com.chongdu.seckill.config;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.nio.charset.Charset;

/**
 * 序列化方式--protostuff
 * @param <T>
 */
public class ProtostuffSerializer<T> implements RedisSerializer {

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    private ObjectMapper objectMapper = new ObjectMapper();
    private Class<T> clazz;

    public ProtostuffSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

//    public ProtostuffSerializer(JavaType javaType) {
//        this.javaType = javaType;
//    }

    public T deserialize(@Nullable byte[] bytes) throws SerializationException {
//        try {
//            T message = (T) objenesis.newInstance(cls);
//            Schema<T> schema = getSchema(cls);
//            ProtobufIOUtil.mergeFrom(data, message, schema);
//            return message;
//        } catch (Exception e) {
//            throw new IllegalStateException(e.getMessage(), e);
//        }


        T obj = null;
        try {
            obj = clazz.newInstance();
            Schema schema = RuntimeSchema.getSchema(obj.getClass());
            ProtostuffIOUtil.mergeFrom(bytes, obj, schema);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return obj;
    }

    public byte[] serialize(@Nullable Object t) throws SerializationException {
        if (t == null) {
            return new byte[0];
        } else {
            Schema schema = RuntimeSchema.getSchema(t.getClass());
            byte[] bytes = ProtostuffIOUtil.toByteArray(t, schema,
                    LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
            return bytes;
        }
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        Assert.notNull(objectMapper, "'objectMapper' must not be null");
        this.objectMapper = objectMapper;
    }

    protected JavaType getJavaType(Class<?> clazz) {
        return TypeFactory.defaultInstance().constructType(clazz);
    }

}
