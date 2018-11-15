package com.wj.redis;

import com.wj.utils.PageData;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @创建人 wj
 * @创建时间 2018/11/14
 * @描述
 */
public interface RedisDao {

    boolean isEmpty(Object obj);

    /**
     * 构建缓存key值
     *
     * @param key 缓存key
     * @return
     */
    String buildKey(String key);

    /**
     * 返回缓存的前缀
     *
     * @return CACHE_PREFIX_FLAG
     */
    String getCachePrefix();

    /**
     * 设置缓存的前缀
     *
     * @param cachePrefix
     */
    void setCachePrefix(String cachePrefix);

    /**
     * 关闭缓存
     *
     * @return true:成功 false:失败
     */
    boolean close();

    /**
     * 打开缓存
     *
     * @return true:存在 false:不存在
     */
    boolean openCache();

    /**
     * 检查缓存是否开启
     *
     * @return true:已关闭 false:已开启
     */
    boolean isClose();

    /**
     * 判断key值是否存在
     *
     * @param key 缓存的key
     * @return true:存在 false:不存在
     */
    boolean hasKey(String key);

    /**
     * 匹配符合正则的key
     *
     * @param patternKey
     * @return key的集合
     */
    Set<String> keys(String patternKey);

    /**
     * 根据key删除缓存
     *
     * @param key
     * @return true:成功 false:失败
     */
    boolean del(String... key);

    /**
     * 根据key删除缓存
     *
     * @param key
     * @return true:成功 false:失败
     */
    boolean delPattern(String key);

    /**
     * 删除一组key值
     *
     * @param keys
     * @return true:成功 false:失败
     */
    boolean del(Set<String> keys);


    /**
     * 设置过期时间
     *
     * @param key     缓存key
     * @param seconds 过期秒数
     * @return true:成功 false:失败
     */
    boolean setExp(String key, long seconds);

    /**
     * 查询过期时间
     *
     * @param key 缓存key
     * @return 秒数
     */
    Long getExpire(String key);

    /**
     * 缓存存入key-value
     *
     * @param key   缓存键
     * @param value 缓存值
     * @return true:成功 false:失败
     */
    boolean setString(String key, String value);

    /**
     * 缓存存入key-value
     *
     * @param key     缓存键
     * @param value   缓存值
     * @param seconds 秒数
     * @return true:成功 false:失败
     */
    boolean setString(String key, String value, long seconds);

    /**
     * 根据key取出String value
     *
     * @param key 缓存key值
     * @return String    缓存的String
     */
    String getString(String key);

    /**
     * 去的缓存中的最大值并+1
     *
     * @param key 缓存key值
     * @return long    缓存中的最大值+1
     */
    long incr(String key);

    /**
     * 缓存中存入序列化的Object对象
     *
     * @param key 缓存key
     * @param obj 存入的序列化对象
     * @return true:成功 false:失败
     */
    boolean set(String key, Object obj);

    /**
     * 缓存中存入序列化的Object对象
     *
     * @param key 缓存key
     * @param obj 存入的序列化对象
     * @return true:成功 false:失败
     */
    boolean setObj(String key, Object obj, long seconds);

    /**
     * 取出缓存中存储的序列化对象
     *
     * @param key   缓存key
     * @param clazz 对象类
     * @return <T>	序列化对象
     */
    <T> T getObj(String key, Class<T> clazz);

    /**
     * 存入Map数组
     *
     * @param <T>
     * @param key 缓存key
     * @param map 缓存map
     * @return true:成功 false:失败
     */
    <T> boolean setMap(String key, Map<String, T> map);

    /**
     * 取出缓存的map
     *
     * @param key 缓存key
     * @return map    缓存的map
     */
    @SuppressWarnings("rawtypes")
    Map getMap(String key);

    /**
     * 查询缓存的map的集合大小
     *
     * @param key 缓存key
     * @return int    缓存map的集合大小
     */
    long getMapSize(String key);


    /**
     * 根据key以及hashKey取出对应的Object对象
     *
     * @param key     缓存key
     * @param hashKey 对应map的key
     * @return object    map中的对象
     */
    Object getMapKey(String key, String hashKey);

    /**
     * 取出缓存中map的所有key值
     *
     * @param key 缓存key
     * @return Set<String> map的key值合集
     */
    Set<Object> getMapKeys(String key);

    /**
     * 删除map中指定的key值
     *
     * @param key     缓存key
     * @param hashKey map中指定的hashKey
     * @return true:成功 false:失败
     */
    boolean delMapKey(String key, String hashKey);

    /**
     * 存入Map数组
     *
     * @param <T>
     * @param key     缓存key
     * @param map     缓存map
     * @param seconds 秒数
     * @return true:成功 false:失败
     */
    <T> boolean setMapExp(String key, Map<String, T> map, long seconds);

    /**
     * map中加入新的key
     *
     * @param <T>
     * @param key     缓存key
     * @param hashKey map的Key值
     * @param value   map的value值
     * @return true:成功 false:失败
     */
    <T> boolean addMap(String key, String hashKey, T value);

    /**
     * 缓存存入List
     *
     * @param <T>
     * @param key  缓存key
     * @param list 缓存List
     * @return true:成功 false:失败
     */
    <T> boolean setList(String key, List<T> list);

    /**
     * 根据key值取出对应的list合集
     *
     * @param key 缓存key
     * @return List<Object> 缓存中对应的list合集
     */
    <V> List<V> getList(String key);

    /**
     * 根据key值截取对应的list合集
     *
     * @param key   缓存key
     * @param start 开始位置
     * @param end   结束位置
     * @return
     */
    void trimList(String key, int start, int end);

    /**
     * 取出list合集中指定位置的对象
     *
     * @param key   缓存key
     * @param index 索引位置
     * @return Object    list指定索引位置的对象
     */
    Object getIndexList(String key, int index);

    /**
     * Object存入List
     *
     * @param prefix   缓存key
     * @param value List中的值
     * @return true:成功 false:失败
     */
    boolean addList(KeyPrefix prefix, Object value);

    /**
     * 缓存存入List
     *
     * @param <T>
     * @param prefix     缓存key,秒数
     * @param list    缓存List
     * @return true:成功 false:失败
     */
    <T> boolean setList(KeyPrefix prefix,  List<T> list);

    /**
     * set集合存入缓存
     *
     * @param <T>
     * @param key 缓存key
     * @param set 缓存set集合
     * @return true:成功 false:失败
     */
    <T> boolean setSet(String key, Set<T> set);

    /**
     * set集合中增加value
     *
     * @param key   缓存key
     * @param value 增加的value
     * @return true:成功 false:失败
     */
    boolean addSet(String key, Object value);

    /**
     * set集合存入缓存
     *
     * @param <T>
     * @param key     缓存key
     * @param set     缓存set集合
     * @param seconds 秒数
     * @return true:成功 false:失败
     */
    <T> boolean setSet(String key, Set<T> set, long seconds);

    /**
     * 取出缓存中对应的set合集
     *
     * @param <T>
     * @param key 缓存key
     * @return Set<Object> 缓存中的set合集
     */
    <T> Set<T> getSet(String key);

    /**
     * 有序集合存入数值
     *
     * @param key   缓存key
     * @param value 缓存value
     * @param score 评分
     * @return
     */
    boolean addZSet(String key, Object value, double score);

    /**
     * 从有序集合中删除指定值
     *
     * @param key   缓存key
     * @param value 缓存value
     * @return
     */
    boolean removeZSet(String key, Object value);

    /**
     * 从有序集合中删除指定位置的值
     *
     * @param key   缓存key
     * @param start 起始位置
     * @param end   结束为止
     * @return
     */
    boolean removeZSet(String key, long start, long end);

    /**
     * 从有序集合中获取指定位置的值
     *
     * @param key   缓存key
     * @param start 起始位置
     * @param end   结束为止
     * @return
     */
    <T> Set<T> getZSet(String key, long start, long end);

}
