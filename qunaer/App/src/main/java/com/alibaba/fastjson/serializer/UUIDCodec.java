package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import java.lang.reflect.Type;
import java.util.UUID;

public class UUIDCodec implements ObjectDeserializer, ObjectSerializer {
    public static final UUIDCodec instance = new UUIDCodec();

    public void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type) {
        if (obj == null) {
            jSONSerializer.writeNull();
        } else {
            jSONSerializer.write(((UUID) obj).toString());
        }
    }

    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        String str = (String) defaultJSONParser.parse();
        if (str == null) {
            return null;
        }
        return UUID.fromString(str);
    }

    public int getFastMatchToken() {
        return 4;
    }
}
