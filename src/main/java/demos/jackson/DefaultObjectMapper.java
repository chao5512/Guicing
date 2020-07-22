/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package demos.jackson;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.guava.GuavaModule;

/**
 *
 */
public class DefaultObjectMapper extends ObjectMapper {
    public DefaultObjectMapper() {
        this((JsonFactory) null);
    }

    public DefaultObjectMapper(DefaultObjectMapper mapper) {
        super(mapper);
    }

    public DefaultObjectMapper(JsonFactory factory) {
        super(factory);
        registerModule(new DruidDefaultSerializersModule());
        // 增加Guava类型的支持,主要支持guava的collection
        registerModule(new GuavaModule());
        registerModule(new GranularityModule());
        //registerModule(new AggregatorsModule());
        //registerModule(new StringComparatorModule());
        //registerModule(new SegmentizerModule());

        //忽略位置的json字段，用于json字段多于对应的对象时
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 设置为false后，beans中只有用注解标识的getter方法才会被认为是getter方法，而不是以get开头的方法
        configure(MapperFeature.AUTO_DETECT_GETTERS, false);
        // See https://github.com/FasterXML/jackson-databind/issues/170
        // configure(MapperFeature.AUTO_DETECT_CREATORS, false);
        configure(MapperFeature.AUTO_DETECT_FIELDS, false);
        // boolean型的getter方法
        configure(MapperFeature.AUTO_DETECT_IS_GETTERS, false);
        configure(MapperFeature.AUTO_DETECT_SETTERS, false);
        configure(MapperFeature.ALLOW_FINAL_FIELDS_AS_MUTATORS, false);
        //输出是否缩进
        configure(SerializationFeature.INDENT_OUTPUT, false);
        configure(SerializationFeature.FLUSH_AFTER_WRITE_VALUE, false);
    }

    @Override
    public ObjectMapper copy() {
        return new DefaultObjectMapper(this);
    }
}
