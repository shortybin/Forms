package com.shortybin.forms;

import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Gson工具类
 * Created by linshixina on 2018/5/2.
 */
public class JsonHelper {
    public static final int BUFFER_SIZE = 100 * 1024;  //byte读取最大值

    /**
     * 对象转string
     *
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.toJson(object);
    }

    /**
     * string转对象
     *
     * @param value
     * @param <T>
     * @return
     */
    public static <T> T getObjectByStr(String value, Class<T> clss) {
        Gson gson = new GsonBuilder().create();
        T t = gson.fromJson(value, clss);
        return t;
    }


    /**
     * InputStream转对象
     *
     * @param in
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> T getObjectByIn(InputStream in, Class<T> clz) {
        Gson gson = new GsonBuilder().create();
        T t = gson.fromJson(new InputStreamReader(in), clz);
        return t;
    }


    /**
     * 使用TypeToken转换，可以是list，或者单个对象,TypeToken typetoken=new TypeToken<List<model>>(){}
     *
     * @param value
     * @param <T>
     * @return
     */
    public static <T> T getObjectByStr(String value, TypeToken typeToken) {
        Type type = typeToken.getType();
        T obj = new Gson().fromJson(value, type);
        return obj;
    }

    public static <T> T getObjectByIn(InputStream in, TypeToken typeToken) {
        Type type = typeToken.getType();
        T obj = new Gson().fromJson(new InputStreamReader(in), type);
        return obj;
    }

    /**
     * 解压base64 gzip字符串得到标准的json字符串
     *
     * @param value
     * @return
     */
    public static String getJsonByBase64GzStr(String value) {
        return getJsonByGz(Base64.decode(value, Base64.DEFAULT));
    }


    /**
     * 解压gzip得到标准的json字符串
     *
     * @param bytes
     * @return
     */
    public static String getJsonByGz(byte[] bytes) {
        try {
            GZIPInputStream gzip = new GZIPInputStream(new ByteArrayInputStream(bytes));
            byte[] buf = new byte[BUFFER_SIZE];
            int len = 0;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((len = gzip.read(buf, 0, buf.length)) != -1) {
                baos.write(buf, 0, len);
            }
            gzip.close();
            baos.flush();
            byte[] decode = baos.toByteArray();
            baos.close();
            return new String(decode, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gzip转对象
     *
     * @param bytes
     * @param <T>
     * @return
     */
    public static <T> T getObjectByGz(byte[] bytes, Class<T> clss) {
        try {
            return getObjectByStr(getJsonByGz(bytes), clss);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Gzip转对象
     *
     * @param bytes
     * @param <T>
     * @return
     */
    public static <T> List<T> getObjectByGz(byte[] bytes, TypeToken typeToken) {
        try {
            return getObjectByStr(getJsonByGz(bytes), typeToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gzip转对象
     *
     * @param value
     * @param <T>
     * @return
     */
    public static <T> T getObjectByBase64GzStr(String value, Class<T> clss) {
        return getObjectByStr(getJsonByGz(Base64.decode(value, Base64.DEFAULT)), clss);
    }


    /**
     * Gzip转对象
     *
     * @param value
     * @param <T>
     * @return
     */
    public static <T> List<T> getObjectByBase64GzStr(String value, TypeToken typeToken) {
        return getObjectByStr(getJsonByGz(Base64.decode(value, Base64.DEFAULT)), typeToken);
    }


    /**
     * json压缩，再以base64 加密
     * string转对象
     *
     * @return
     */
    public static byte[] encrypGz(Object obj) {
        try {
            String value = toJson(obj);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(baos);
            gzip.write(value.getBytes("UTF-8"));
            gzip.close();
            byte[] encode = baos.toByteArray();
            baos.flush();
            baos.close();
            return encode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * json压缩，再以base64 加密
     * string转对象
     *
     * @return
     */
    public static String encrypGzToBase64Str(Object obj) {
        return Base64.encodeToString(encrypGz(obj), Base64.DEFAULT);
    }


    /**
     * 根据json字符串返回Map对象
     *
     * @param json
     * @return
     */
    public static Map<String, Object> toMap(String json) {
        return toMap(parseJson(json));
    }


    /**
     * 获取JsonObject
     *
     * @param json
     * @return
     */
    public static JsonObject parseJson(String json) {
        JsonParser parser = new JsonParser();
        JsonObject jsonObj = parser.parse(json).getAsJsonObject();
        return jsonObj;
    }

    /**
     * 将JSONObjec对象转换成Map-List集合
     *
     * @param json
     * @return
     */
    public static Map<String, Object> toMap(JsonObject json) {
        Map<String, Object> map = new HashMap<String, Object>();
        Set<Map.Entry<String, JsonElement>> entrySet = json.entrySet();
        for (Iterator<Map.Entry<String, JsonElement>> iter = entrySet.iterator(); iter.hasNext(); ) {
            Map.Entry<String, JsonElement> entry = iter.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof JsonArray)
                map.put((String) key, toList((JsonArray) value));
            else if (value instanceof JsonObject)
                map.put((String) key, toMap((JsonObject) value));
            else
                map.put((String) key, value);
        }
        return map;
    }

    /**
     * 将JSONArray对象转换成List集合
     *
     * @param json
     * @return
     */
    public static List<Object> toList(JsonArray json) {
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < json.size(); i++) {
            Object value = json.get(i);
            if (value instanceof JsonArray) {
                list.add(toList((JsonArray) value));
            } else if (value instanceof JsonObject) {
                list.add(toMap((JsonObject) value));
            } else {
                list.add(value);
            }
        }
        return list;
    }
}
