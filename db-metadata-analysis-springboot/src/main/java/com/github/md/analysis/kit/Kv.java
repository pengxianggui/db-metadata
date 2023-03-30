/**
 * Copyright (c) 2011-2021, James Zhan 詹波 (jfinal@126.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.md.analysis.kit;

import com.alibaba.fastjson.JSON;
import com.jfinal.kit.StrKit;

import java.util.HashMap;
import java.util.Map;
// import com.jfinal.json.Json;

/**
 * Kv (Key Value)
 * <p>
 * Example：
 * Kv para = Kv.by("id", 123);
 * User user = user.findFirst(getSqlPara("find", para));
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class Kv extends HashMap {

    private static final long serialVersionUID = -6086130186405306902L;

    public Kv() {
    }

    public static Kv by(Object key, Object value) {
        return new Kv().set(key, value);
    }

    public static Kv create() {
        return new Kv();
    }

    public Kv set(Object key, Object value) {
        super.put(key, value);
        return this;
    }

    public Kv setIfNotBlank(Object key, String value) {
        if (StrKit.notBlank(value)) {
            set(key, value);
        }
        return this;
    }

    public Kv setIfNotNull(Object key, Object value) {
        if (value != null) {
            set(key, value);
        }
        return this;
    }

    public Kv set(Map map) {
        super.putAll(map);
        return this;
    }

    public Kv set(Kv kv) {
        super.putAll(kv);
        return this;
    }

    public Kv delete(Object key) {
        super.remove(key);
        return this;
    }

    public <T> T getAs(Object key) {
        return (T) get(key);
    }

    /**
     * 获取key对应的value, 期望的类型为kv。
     * 若值不存在，则创建一个空的kv并且put，若值存在但是类型不是kv, 则：
     * <pre>
     * 1. 若值为null，则用空的kv替换旧值, 并返回空kv
     * 2. 若值为kv, 则直接返回
     * 3. 若值为map, 或map子类，则转为kv并刷新旧值后返回kv
     * 4. 若值为其他类型的值，则抛出异常 {@link ClassCastException}
     * </pre>
     * <b>注意：此方法虽为get, 但是可能会改变内部key对应的value值</b>
     *
     * @param key
     * @return
     */
    public Kv getKvPutIfAbsent(Object key) {
        Kv value;
        Object obj = get(key);

        if (obj == null) {
            value = Kv.create();
            put(key, value);
            return value;
        } else if (obj instanceof Kv) {
            return (Kv) obj;
        } else if (obj instanceof Map) {
            value = Kv.create().set((Map) obj);
            put(key, value);
            return value;
        } else {
            throw new ClassCastException(obj.getClass().toString() + " cannot be cast to " + this.getClass().toString());
        }
    }

    public String getStr(Object key) {
        Object s = get(key);
        return s != null ? s.toString() : null;
    }

    public Integer getInt(Object key) {
        Number n = (Number) get(key);
        return n != null ? n.intValue() : null;
    }

    public Long getLong(Object key) {
        Number n = (Number) get(key);
        return n != null ? n.longValue() : null;
    }

    public Double getDouble(Object key) {
        Number n = (Number) get(key);
        return n != null ? n.doubleValue() : null;
    }

    public Float getFloat(Object key) {
        Number n = (Number) get(key);
        return n != null ? n.floatValue() : null;
    }

    public Number getNumber(Object key) {
        return (Number) get(key);
    }

    public Boolean getBoolean(Object key) {
        return (Boolean) get(key);
    }

    /**
     * key 存在，并且 value 不为 null
     */
    public boolean notNull(Object key) {
        return get(key) != null;
    }

    /**
     * key 不存在，或者 key 存在但 value 为null
     */
    public boolean isNull(Object key) {
        return get(key) == null;
    }

    /**
     * key 存在，并且 value 为 true，则返回 true
     */
    public boolean isTrue(Object key) {
        Object value = get(key);
        return (value instanceof Boolean && ((Boolean) value == true));
    }

    /**
     * key 存在，并且 value 为 false，则返回 true
     */
    public boolean isFalse(Object key) {
        Object value = get(key);
        return (value instanceof Boolean && ((Boolean) value == false));
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }

    public boolean equals(Object kv) {
        return kv instanceof Kv && super.equals(kv);
    }

    public Kv keep(String... keys) {
        if (keys != null && keys.length > 0) {
            Kv newKv = Kv.create();
            for (String k : keys) {
                if (containsKey(k)) {    // 避免将并不存在的变量存为 null
                    newKv.put(k, get(k));
                }
            }

            clear();
            putAll(newKv);
        } else {
            clear();
        }

        return this;
    }
}


