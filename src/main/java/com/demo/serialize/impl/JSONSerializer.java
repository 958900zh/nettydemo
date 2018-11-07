package com.demo.serialize.impl;

import com.alibaba.fastjson.JSON;
import com.demo.serialize.Serializer;
import com.demo.serialize.SerializerAlgorithm;

public class JSONSerializer implements Serializer {
    @Override
    public Byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserializer(Class<T> tClass, byte[] bytes) {
        return JSON.parseObject(bytes, tClass);
    }
}
