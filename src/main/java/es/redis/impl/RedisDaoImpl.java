package es.redis.impl;

import com.google.common.collect.Maps;
import es.redis.RedisDao;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.SerializationUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by cao on 14/11/17.
 */
@Repository("redisDao")
public class RedisDaoImpl implements RedisDao {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean exists(final String key) {
        if (StringUtils.isEmpty(key)) {
            return false;
        }
        return (boolean) redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.exists(key.getBytes());
            }
        });
    }


    @Override
    public boolean expire(final String key, final long seconds) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.expire(key.getBytes(), seconds);
            }
        });
    }

    @Override
    public Object get(final String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        return redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] value = connection.get(key.getBytes());
                Object obj = null;
                try {
                    obj = SerializationUtils.deserialize(value);
                } catch (IllegalArgumentException e) {
                    obj = new String(value);
                }
                return obj;
            }
        });
    }

    @Override
    public boolean hSet(final String key, final String field, final String value) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(field) || StringUtils.isEmpty(value))
            return false;
        return (boolean) redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.hSet(key.getBytes(), field.getBytes(), value.getBytes());
            }
        });
    }

    @Override
    public boolean hSet(final String key, final String field, final Object value) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(field) || value == null)
            return false;
        return (boolean) redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] valueBytes = null;
                if (value instanceof Boolean || value instanceof Integer || value instanceof Double || value instanceof String) {
                    valueBytes = String.valueOf(value).getBytes();
                } else {
                    valueBytes = SerializationUtils.serialize(value);
                }
                return connection.hSet(key.getBytes(), field.getBytes(), valueBytes);
            }
        });
    }

    @Override
    public boolean sAdd(final String key, final List<String> values) {
        if (StringUtils.isEmpty(key) || CollectionUtils.isEmpty(values))
            return false;
        return (boolean) redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                for (String value : values) {
                    connection.sAdd(key.getBytes(), value.getBytes());
                }
                return true;
            }
        });
    }

    @Override
    public void hMSet(final String key, final Map<String, Object> maps) {
        if (StringUtils.isEmpty(key) || MapUtils.isEmpty(maps)) {
            return;
        }
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                Map<byte[], byte[]> hashes = Maps.newHashMapWithExpectedSize(maps.size());
                for (Entry<String, Object> entry : maps.entrySet()) {
                    if (entry.getValue() == null) {
                        continue;
                    }
                    if (entry.getValue() instanceof Boolean || entry.getValue() instanceof Integer
                            || entry.getValue() instanceof Double) {
                        hashes.put(entry.getKey().getBytes(), String.valueOf(entry.getValue()).getBytes());
                    } else if (entry.getValue() instanceof String) {
                        hashes.put(entry.getKey().getBytes(), ((String) entry.getValue()).getBytes());
                    } else {
                        hashes.put(entry.getKey().getBytes(), SerializationUtils.serialize(entry.getValue()));
                    }
                }
                connection.hMSet(key.getBytes(), hashes);
                return hashes;
            }
        });
    }

    @Override
    public Map<String, Object> hGetAll(final String key) {
        if (StringUtils.isEmpty(key)) {
            return MapUtils.EMPTY_MAP;
        }
        return (Map<String, Object>) redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                Map<byte[], byte[]> hashes = connection.hGetAll(key.getBytes());
                Map<String, Object> maps = Maps.newHashMapWithExpectedSize(hashes.size());
                for (Entry<byte[], byte[]> entry : hashes.entrySet()) {
                    Object obj = null;
                    try {
                        obj = SerializationUtils.deserialize(entry.getValue());
                    } catch (IllegalArgumentException e) {
                        obj = new String(entry.getValue());
                    }
                    maps.put(new String(entry.getKey()), obj);
                }
                return maps;
            }
        });
    }

    @Override
    public Object hGet(final String key, final String field) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(field)) {
            return StringUtils.EMPTY;
        }
        return redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] hashes = connection.hGet(key.getBytes(), field.getBytes());
                Object obj = null;
                try {
                    obj = SerializationUtils.deserialize(hashes);
                } catch (IllegalArgumentException e) {
                    obj = new String(hashes);
                }
                return obj;
            }
        });
    }

    @Override
    public long incr(final String key) {
        if (StringUtils.isEmpty(key)) {
            return 0;
        }
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.incr(key.getBytes());
            }
        });
    }


    @Override
    public long incrBy(final String key, final long value) {
        if (StringUtils.isEmpty(key)) {
            return 0;
        }
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.incrBy(key.getBytes(), value);
            }
        });
    }

    //返回成功删除的个数
    @Override
    public long del(final String key) {
        if (StringUtils.isEmpty(key)) {
            return 0;
        }
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.del(key.getBytes());
            }
        });
    }

    @Override
    public long hdel(final String key, final String field) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(field)) {
            return 0;
        }
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.hDel(key.getBytes(), field.getBytes());
            }
        });
    }
}
