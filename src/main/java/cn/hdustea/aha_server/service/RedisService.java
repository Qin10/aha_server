package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.vo.UserContribPointVo;
import cn.hdustea.aha_server.vo.UserRoughInfoVo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis操作工具类
 *
 * @author 王赛超、STEA_YY
 */
@Service
public class RedisService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    // =============================common============================

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     */
    public void expire(String key, long time) {
        if (time > 0) {
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
    }

    /**
     * 删除缓存
     *
     * @param key 一个或多个键
     */
    @SuppressWarnings("unchecked")
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    // ============================String=============================

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 获取到的对象
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存写入
     *
     * @param key   键
     * @param value 值
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     */
    public void set(String key, Object value, long time) {
        if (time > 0) {
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        } else {
            set(key, value);
        }
    }

    public void incr(String key, double by) {
        redisTemplate.opsForValue().increment(key, by);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     */
    public Map<Object, Object> hGetMap(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public void hDel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return 增加后的数值
     */
    @SuppressWarnings("UnusedReturnValue")
    public double hIncr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * 获取zSet元组
     *
     * @param key   键
     * @param start 开始项
     * @param end   结束项
     * @return ZSet元组
     */
    public Set<ZSetOperations.TypedTuple<Object>> zGetTuple(String key, long start, long end) {
        return redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
    }

    /**
     * 获取zSet里value对应的排名
     *
     * @param key   键
     * @param value 值
     * @return 排名
     */
    public Long zGetRank(String key, Object value) {
        return redisTemplate.opsForZSet().reverseRank(key, value);
    }

    /**
     * 获取zSet里value对应的分数
     *
     * @param key   键
     * @param value 值
     * @return 分数
     */
    public Double zGetScore(String key, Object value) {
        return redisTemplate.opsForZSet().score(key, value);
    }

    /**
     * zSet写入
     *
     * @param key   键
     * @param value 值
     * @param score 分数
     */
    public void zSet(String key, Object value, double score) {
        redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * list入队
     *
     * @param key   键
     * @param value 值
     */
    public void lPush(String key, Object value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * list出队
     *
     * @param key 键
     * @return 出队的对象
     */
    public Object lPop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     * zSet元组转换为排名VO
     *
     * @param tuples zSet元组
     * @return 用户排名VO
     */
    public List<UserContribPointVo> tupleToUserContribPoint(Set<ZSetOperations.TypedTuple<Object>> tuples) {
        List<UserContribPointVo> userContribPointVos = new ArrayList<>();
        long currentRank = 1;
        for (ZSetOperations.TypedTuple<Object> tuple : tuples) {
            UserContribPointVo userContribPointVo = new UserContribPointVo();
            userContribPointVo.setUser((UserRoughInfoVo) tuple.getValue());
            userContribPointVo.setContribPoint(tuple.getScore() != null ? BigDecimal.valueOf(tuple.getScore()) : null);
            userContribPointVo.setRank(currentRank);
            currentRank++;
            userContribPointVos.add(userContribPointVo);
        }
        return userContribPointVos;
    }
}