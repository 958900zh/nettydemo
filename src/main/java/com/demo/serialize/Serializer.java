package com.demo.serialize;

import com.demo.serialize.impl.JSONSerializer;

public interface Serializer {

    Serializer DEFAULT = new JSONSerializer();

    Byte getSerializerAlgorithm();

    byte[] serialize(Object object);

    <T> T deserializer(Class<T> tClass, byte[] bytes);
}
