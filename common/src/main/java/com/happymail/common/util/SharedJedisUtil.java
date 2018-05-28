package com.happymail.common.util;

import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.extern.log4j.Log4j2;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.BinaryClient.LIST_POSITION;

/**
 * 分片的jedis帮助类
 * @author yu
 *
 */
@Log4j2
public class SharedJedisUtil
{
	public static  ShardedJedisPool pool=SharedRedisPool.getJedisPool();
	   /**
     * <p>通过key获取储存在redis中的value</p>
     * <p>并释放连接</p>
     * @param key
     * @return 成功返回value 失败返回null
     */
    public static String get(String key)
    {
        ShardedJedis jedis = null;
        String value = null;
        try {
            jedis = pool.getResource();
            value = jedis.get(key);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
        return value;
    }
    /**
     * <p>向redis存入key和value,并释放连接资源</p>
     * <p>如果key已经存在 则覆盖</p>
     * @param key
     * @param value
     * @return 成功 返回OK 失败返回 0
     */
    public static String set(String key,String value){
        ShardedJedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.set(key, value);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
            return "0";
        } finally {
            jedis.close();
        }
    }
    /**
     * <p>删除指定的key,也可以传入一个包含key的数组</p>
     * @param keys 一个key  也可以使 string 数组
     * @return 返回删除成功的个数
     */
    public static Long del(String keys){
        ShardedJedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.del(keys);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return 0L;
        } finally {
            jedis.close();
        }
    }
    
    /**
     * <p>通过key向指定的value值追加值</p>
     * @param key
     * @param str
     * @return 成功返回 添加后value的长度 失败 返回 添加的 value 的长度  异常返回0L
     */
    public static  Long append(String key ,String str){
        ShardedJedis jedis = null;
        Long res = null;
        try {
            jedis = pool.getResource();
            res = jedis.append(key, str);
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0L;
        } finally {
        	jedis.close();
        }
        return res;
    }
    /**
     * <p>判断key是否存在</p>
     * @param key
     * @return true OR false
     */
    public static Boolean exists(String key){
        ShardedJedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.exists(key);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return false;
        } finally {
            jedis.close();
        }
    }
    

    /**
     * <p>设置key value,如果key已经存在则返回0,nx==> not exist</p>
     * @param key
     * @param value
     * @return 成功返回1 如果存在 和 发生异常 返回 0
     */
    public static Long setnx(String key ,String value){
        ShardedJedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.setnx(key, value);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return 0L;
        } finally {
            jedis.close();
        }
    }
    
    /**
     * <p>设置key value并制定这个键值的有效期</p>
     * @param key
     * @param value
     * @param seconds 单位:秒
     * @return 成功返回OK 失败和异常返回null
     */
	public static String setex(String key,String value,int seconds){
        ShardedJedis jedis = null;
        String res = null;
        try {
            jedis = pool.getResource();
            res = jedis.setex(key, seconds, value);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        } finally {
            //jedis.close();
        	//pool.returnBrokenResource(jedis);
        	jedis.close();
        }
        return res;
    }
    /**
     * <p>通过key 和offset 从指定的位置开始将原先value替换</p>
     * <p>下标从0开始,offset表示从offset下标开始替换</p>
     * <p>如果替换的字符串长度过小则会这样</p>
     * <p>example:</p>
     * <p>value : bigsea@zto.cn</p>
     * <p>str : abc </p>
     * <P>从下标7开始替换  则结果为</p>
     * <p>RES : bigsea.abc.cn</p>
     * @param key
     * @param str
     * @param offset 下标位置
     * @return 返回替换后  value 的长度
     */
    public static Long setrange(String key,String str,int offset){
        ShardedJedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.setrange(key, offset, str);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return 0L;
        } finally {
            jedis.close();
        }
    }

    
    /**
     * <p>设置key的值,并返回一个旧值</p>
     * @param key
     * @param value
     * @return 旧值 如果key不存在 则返回null
     */
    public static String getset(String key,String value){
        ShardedJedis jedis = null;
        String res = null;
        try {
            jedis = pool.getResource();
            res = jedis.getSet(key, value);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
        return res;
    }

    /**
     * <p>通过下标 和key 获取指定下标位置的 value</p>
     * @param key
     * @param startOffset 开始位置 从0 开始 负数表示从右边开始截取
     * @param endOffset
     * @return 如果没有返回null
     */
    public static String getrange(String key, int startOffset ,int endOffset){
        ShardedJedis jedis = null;
        String res = null;
        try {
            jedis = pool.getResource();
            res = jedis.getrange(key, startOffset, endOffset);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
        return res;
    }

    /**
     * <p>通过key 对value进行加值+1操作,当value不是int类型时会返回错误,当key不存在是则value为1</p>
     * @param key
     * @return 加值后的结果
     */
    public static Long incr(String key){
        ShardedJedis jedis = null;
        Long res = null;
        try {
            jedis = pool.getResource();
            res = jedis.incr(key);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
        return res;
    }

    /**
     * <p>通过key给指定的value加值,如果key不存在,则这是value为该值</p>
     * @param key
     * @param integer
     * @return
     */
    public static Long incrBy(String key,Long integer){
        ShardedJedis jedis = null;
        Long res = null;
        try {
            jedis = pool.getResource();
            res = jedis.incrBy(key, integer);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
        return res;
    }

    /**
     * <p>对key的值做减减操作,如果key不存在,则设置key为-1</p>
     * @param key
     * @return
     */
    public static Long decr(String key) {
        ShardedJedis jedis = null;
        Long res = null;
        try {
            jedis = pool.getResource();
            res = jedis.decr(key);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
        return res;
    }

    /**
     * <p>减去指定的值</p>
     * @param key
     * @param integer
     * @return
     */
    public static Long decrBy(String key,Long integer){
        ShardedJedis jedis = null;
        Long res = null;
        try {
            jedis = pool.getResource();
            res = jedis.decrBy(key, integer);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
        return res;
    }

    /**
     * <p>通过key获取value值的长度</p>
     * @param key
     * @return 失败返回null
     */
    public static Long serlen(String key){
        ShardedJedis jedis = null;
        Long res = null;
        try {
            jedis = pool.getResource();
            res = jedis.strlen(key);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
        return res;
    }

    /**
     * <p>通过key给field设置指定的值,如果key不存在,则先创建</p>
     * @param key
     * @param field 字段
     * @param value
     * @return 如果存在返回0 异常返回null
     */
    public static Long hset(String key,String field,String value) {
        ShardedJedis jedis = null;
        Long res = null;
        try {
            jedis = pool.getResource();
            res = jedis.hset(key, field, value);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
        return res;
    }

    /**
     * <p>通过key给field设置指定的值,如果key不存在则先创建,如果field已经存在,返回0</p>
     * @param key
     * @param field
     * @param value
     * @return
     */
    public static Long hsetnx(String key,String field,String value){
        ShardedJedis jedis = null;
        Long res = null;
        try {
            jedis = pool.getResource();
            res = jedis.hsetnx(key, field, value);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
        return res;
    }

    /**
     * <p>通过key同时设置 hash的多个field</p>
     * @param key
     * @param hash
     * @return 返回OK 异常返回null
     */
    public static String hmset(String key,Map<String, String> hash){
        ShardedJedis jedis = null;
        String res = null;
        try {
            jedis = pool.getResource();
            res = jedis.hmset(key, hash);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
        return res;
    }

    /**
     * <p>通过key 和 field 获取指定的 value</p>
     * @param key
     * @param field
     * @return 没有返回null
     */
    public static String hget(String key, String field){
        ShardedJedis jedis = null;
        String res = null;
        try {
            jedis = pool.getResource();
            res = jedis.hget(key, field);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
        return res;
    }

    /**
     * <p>通过key 和 fields 获取指定的value 如果没有对应的value则返回null</p>
     * @param key
     * @param fields 可以使 一个String 也可以是 String数组
     * @return
     */
    public static List<String> hmget(String key,String...fields){
        ShardedJedis jedis = null;
        List<String> res = null;
        try {
            jedis = pool.getResource();
            res = jedis.hmget(key, fields);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
        return res;
    }

    /**
     * <p>通过key给指定的field的value加上给定的值</p>
     * @param key
     * @param field
     * @param value
     * @return
     */
    public static Long hincrby(String key ,String field ,Long value){
        ShardedJedis jedis = null;
        Long res = null;
        try {
            jedis = pool.getResource();
            res = jedis.hincrBy(key, field, value);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
        return res;
    }

    /**
     * <p>通过key和field判断是否有指定的value存在</p>
     * @param key
     * @param field
     * @return
     */
    public static Boolean hexists(String key , String field){
        ShardedJedis jedis = null;
        Boolean res = false;
        try {
            jedis = pool.getResource();
            res = jedis.hexists(key, field);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
        return res;
    }

    /**
     * <p>通过key返回field的数量</p>
     * @param key
     * @return
     */
    public static Long hlen(String key){
        ShardedJedis jedis = null;
        Long res = null;
        try {
            jedis = pool.getResource();
            res = jedis.hlen(key);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
        return res;

    }

    /**
     * <p>通过key 删除指定的 field </p>
     * @param key
     * @param fields 可以是 一个 field 也可以是 一个数组
     * @return
     */
    public static Long hdel(String key ,String...fields){
        ShardedJedis jedis = null;
        Long res = null;
        try {
            jedis = pool.getResource();
            res = jedis.hdel(key, fields);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
        return res;
    }

    /**
     * <p>通过key返回所有的field</p>
     * @param key
     * @return
     */
    public static Set<String> hkeys(String key){
        ShardedJedis jedis = null;
        Set<String> res = null;
        try {
            jedis = pool.getResource();
            res = jedis.hkeys(key);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
        return res;
    }

    /**
     * <p>通过key返回所有和key有关的value</p>
     * @param key
     * @return
     */
    public static List<String> hvals(String key){
        ShardedJedis jedis = null;
        List<String> res = null;
        try {
            jedis = pool.getResource();
            res = jedis.hvals(key);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
        return res;
    }

    /**
     * <p>通过key获取所有的field和value</p>
     * @param key
     * @return
     */
    public static Map<String, String> hgetall(String key){
        ShardedJedis jedis = null;
        Map<String, String> res = null;
        try {
            jedis = pool.getResource();
            res = jedis.hgetAll(key);
        } catch (Exception e) {
            // TODO
        } finally {
            jedis.close();
        }
        return res;
    }

    /**
     * <p>通过key向list头部添加字符串</p>
     * @param key
     * @param strs 可以使一个string 也可以使string数组
     * @return 返回list的value个数
     */
    public static Long lpush(String key ,String...strs){
        ShardedJedis jedis = null;
        Long res = null;
        try {
            jedis = pool.getResource();
            res = jedis.lpush(key, strs);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
        return res;
    }

    /**
     * <p>通过key向list尾部添加字符串</p>
     * @param key
     * @param strs 可以使一个string 也可以使string数组
     * @return 返回list的value个数
     */
    public static Long rpush(String key ,String...strs){
        ShardedJedis jedis = null;
        Long res = null;
        try {
            jedis = pool.getResource();
            res = jedis.rpush(key, strs);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
        return res;
    }

    /**
     * <p>通过key在list指定的位置之前或者之后 添加字符串元素</p>
     * @param key
     * @param where LIST_POSITION枚举类型
     * @param pivot list里面的value
     * @param value 添加的value
     * @return
     */
    public static Long linsert(String key, LIST_POSITION where,
                        String pivot, String value){
        ShardedJedis jedis = null;
        Long res = null;
        try {
            jedis = pool.getResource();
            res = jedis.linsert(key, where, pivot, value);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
        return res;
    }

    /**
     * <p>通过key设置list指定下标位置的value</p>
     * <p>如果下标超过list里面value的个数则报错</p>
     * @param key
     * @param index 从0开始
     * @param value
     * @return 成功返回OK
     */
    public static String lset(String key ,Long index, String value){
        ShardedJedis jedis = null;
        String res = null;
        try {
            jedis = pool.getResource();
            res = jedis.lset(key, index, value);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
        return res;
    }

    /**
     * <p>通过key从对应的list中删除指定的count个 和 value相同的元素</p>
     * @param key
     * @param count 当count为0时删除全部
     * @param value
     * @return 返回被删除的个数
     */
    public static Long lrem(String key,long count,String value){
        ShardedJedis jedis = null;
        Long res = null;
        try {
            jedis = pool.getResource();
            res = jedis.lrem(key, count, value);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
        return res;
    }

    /**
     * <p>通过key保留list中从strat下标开始到end下标结束的value值</p>
     * @param key
     * @param start
     * @param end
     * @return 成功返回OK
     */
    public static String ltrim(String key ,long start ,long end){
        ShardedJedis jedis = null;
        String res = null;
        try {
            jedis = pool.getResource();
            res = jedis.ltrim(key, start, end);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
        return res;
    }

    /**
     * <p>通过key从list的头部删除一个value,并返回该value</p>
     * @param key
     * @return
     */
    synchronized public static String lpop(String key){
        ShardedJedis jedis = null;
        String res = null;
        try {
            jedis = pool.getResource();
            res = jedis.lpop(key);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
        return res;
    }

    /**
     * <p>通过key从list尾部删除一个value,并返回该元素</p>
     * @param key
     * @return
     */
    synchronized public static String rpop(String key){
        ShardedJedis jedis = null;
        String res = null;
        try {
            jedis = pool.getResource();
            res = jedis.rpop(key);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
        return res;
    }


    /**
     * <p>通过key获取list中指定下标位置的value</p>
     * @param key
     * @param index
     * @return 如果没有返回null
     */
    public static String lindex(String key,long index){
        ShardedJedis jedis = null;
        String res = null;
        try {
            jedis = pool.getResource();
            res = jedis.lindex(key, index);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
        return res;
    }

    /**
     * <p>通过key返回list的长度</p>
     * @param key
     * @return
     */
    public static Long llen(String key){
        ShardedJedis jedis = null;
        Long res = null;
        try {
            jedis = pool.getResource();
            res = jedis.llen(key);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
        return res;
    }

    /**
     * <p>通过key获取list指定下标位置的value</p>
     * <p>如果start 为 0 end 为 -1 则返回全部的list中的value</p>
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static List<String> lrange(String key,long start,long end){
        ShardedJedis jedis = null;
        List<String> res = null;
        try {
            jedis = pool.getResource();
            res = jedis.lrange(key, start, end);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
        return res;
    }

    /**
     * <p>通过key向指定的set中添加value</p>
     * @param key
     * @param members 可以是一个String 也可以是一个String数组
     * @return 添加成功的个数
     */
    public static Long sadd(String key,String...members){
        ShardedJedis jedis = null;
        Long res = null;
        try {
            jedis = pool.getResource();
            res = jedis.sadd(key, members);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
        return res;
    }

    /**
     * <p>通过key删除set中对应的value值</p>
     * @param key
     * @param members 可以是一个String 也可以是一个String数组
     * @return 删除的个数
     */
    public static Long srem(String key,String...members){
        ShardedJedis jedis = null;
        Long res = null;
        try {
            jedis = pool.getResource();
            res = jedis.srem(key, members);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
        return res;
    }

    /**
     * <p>通过key随机删除一个set中的value并返回该值</p>
     * @param key
     * @return
     */
    public static String spop(String key){
        ShardedJedis jedis = null;
        String res = null;
        try {
            jedis = pool.getResource();
            res = jedis.spop(key);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
        return res;
    }





    /**
     * <p>通过key获取set中value的个数</p>
     * @param key
     * @return
     */
    public static Long scard(String key){
        ShardedJedis jedis = null;
        Long res = null;
        try {
            jedis = pool.getResource();
            res = jedis.scard(key);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
        return res;
    }

    /**
     * <p>通过key判断value是否是set中的元素</p>
     * @param key
     * @param member
     * @return
     */
    public static Boolean sismember(String key,String member){
        ShardedJedis jedis = null;
        Boolean res = null;
        try {
            jedis = pool.getResource();
            res = jedis.sismember(key, member);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
        return res;
    }

    /**
     * <p>通过key获取set中随机的value,不删除元素</p>
     * @param key
     * @return
     */
    public static String srandmember(String key){
        ShardedJedis jedis = null;
        String res = null;
        try {
            jedis = pool.getResource();
            res = jedis.srandmember(key);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
        return res;
    }

    /**
     * <p>通过key获取set中所有的value</p>
     * @param key
     * @return
     */
    public static Set<String> smembers(String key){
        ShardedJedis jedis = null;
        Set<String> res = null;
        try {
            jedis = pool.getResource();
            res = jedis.smembers(key);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
        return res;
    }


    /**
     * <p>通过key向zset中添加value,score,其中score就是用来排序的</p>
     * <p>如果该value已经存在则根据score更新元素</p>
     * @param key
     * @param score
     * @param member
     * @return
     */
    public static Long zadd(String key,double score,String member){
        ShardedJedis jedis = null;
        Long res = null;
        try {
            jedis = pool.getResource();
            res = jedis.zadd(key, score, member);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
        return res;
    }

    /**
     * <p>通过key删除在zset中指定的value</p>
     * @param key
     * @param members 可以使一个string 也可以是一个string数组
     * @return
     */
    public static Long zrem(String key,String...members){
        ShardedJedis jedis = null;
        Long res = null;
        try {
            jedis = pool.getResource();
            res = jedis.zrem(key, members);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
        return res;
    }

    /**
     * <p>通过key增加该zset中value的score的值</p>
     * @param key
     * @param score
     * @param member
     * @return
     */
    public static Double zincrby(String key ,double score ,String member){
        ShardedJedis jedis = null;
        Double res = null;
        try {
            jedis = pool.getResource();
            res = jedis.zincrby(key, score, member);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
        return res;
    }

    /**
     * <p>通过key返回zset中value的排名</p>
     * <p>下标从小到大排序</p>
     * @param key
     * @param member
     * @return
     */
    public static Long zrank(String key,String member){
        ShardedJedis jedis = null;
        Long res = null;
        try {
            jedis = pool.getResource();
            res = jedis.zrank(key, member);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
        return res;
    }

    /**
     * <p>通过key返回zset中value的排名</p>
     * <p>下标从大到小排序</p>
     * @param key
     * @param member
     * @return
     */
    public static Long zrevrank(String key,String member){
        ShardedJedis jedis = null;
        Long res = null;
        try {
            jedis = pool.getResource();
            res = jedis.zrevrank(key, member);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
        return res;
    }

    /**
     * <p>通过key将获取score从start到end中zset的value</p>
     * <p>socre从大到小排序</p>
     * <p>当start为0 end为-1时返回全部</p>
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static Set<String> zrevrange(String key ,long start ,long end){
        ShardedJedis jedis = null;
        Set<String> res = null;
        try {
            jedis = pool.getResource();
            res = jedis.zrevrange(key, start, end);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
        return res;
    }

    /**
     * <p>通过key返回指定score内zset中的value</p>
     * @param key
     * @param max
     * @param min
     * @return
     */
    public static Set<String> zrangebyscore(String key,String max,String min){
        ShardedJedis jedis = null;
        Set<String> res = null;
        try {
            jedis = pool.getResource();
            res = jedis.zrevrangeByScore(key, max, min);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
        return res;
    }

    /**
     * <p>通过key返回指定score内zset中的value</p>
     * @param key
     * @param max
     * @param min
     * @return
     */
    public static Set<String> zrangeByScore(String key ,double max,double min){
        ShardedJedis jedis = null;
        Set<String> res = null;
        try {
            jedis = pool.getResource();
            res = jedis.zrevrangeByScore(key,max,min);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
        return res;
    }

    /**
     * <p>返回指定区间内zset中value的数量</p>
     * @param key
     * @param min
     * @param max
     * @return
     */
    public static Long zcount(String key,String min,String max){
        ShardedJedis jedis = null;
        Long res = null;
        try {
            jedis = pool.getResource();
            res = jedis.zcount(key, min, max);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
        return res;
    }

    /**
     * <p>通过key返回zset中的value个数</p>
     * @param key
     * @return
     */
    public static Long zcard(String key){
        ShardedJedis jedis = null;
        Long res = null;
        try {
            jedis = pool.getResource();
            res = jedis.zcard(key);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
        return res;
    }

    /**
     * <p>通过key获取zset中value的score值</p>
     * @param key
     * @param member
     * @return
     */
    public static Double zscore(String key,String member){
        ShardedJedis jedis = null;
        Double res = null;
        try {
            jedis = pool.getResource();
            res = jedis.zscore(key, member);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
        return res;
    }

    /**
     * <p>通过key删除给定区间内的元素</p>
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static Long zremrangeByRank(String key ,long start, long end){
        ShardedJedis jedis = null;
        Long res = null;
        try {
            jedis = pool.getResource();
            res = jedis.zremrangeByRank(key, start, end);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
        return res;
    }

    /**
     * <p>通过key删除指定score内的元素</p>
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static Long zremrangeByScore(String key,double start,double end){
        ShardedJedis jedis = null;
        Long res = null;
        try {
            jedis = pool.getResource();
            res = jedis.zremrangeByScore(key, start, end);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
        return res;
    }
    /**
     * <p>返回满足pattern表达式的所有key</p>
     * <p>keys(*)</p>
     * <p>返回所有的key</p>
     * @param pattern
     * @return
     */
    public static String keys(String pattern){
        ShardedJedis jedis = null;
        String res = null;
        try {
            jedis = pool.getResource();
            res = jedis.getKeyTag(pattern);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
        return res;
    }

    /**
     * <p>通过key判断值得类型</p>
     * @param key
     * @return
     */
    public static String type(String key){
        ShardedJedis jedis = null;
        String res = null;
        try {
            jedis = pool.getResource();
            res = jedis.type(key);
        } catch (Exception e) {

            log.error(e.getMessage(),e);
        } finally {
            jedis.close();
        }
        return res;
    }
}
