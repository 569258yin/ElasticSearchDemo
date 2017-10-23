package es.redis;

import es.item.bean.enumeration.RedisKeysEnum;

import java.util.List;
import java.util.Map;

/**
 * Created by cao on 14/11/17.
 */
public interface RedisDao {

    public boolean exists(final String key);

    public boolean expire(final String key, final long seconds);

    public Object get(final String key);

    public long del(final String key);

    Map<String, Object> hGetAll(String key);

    Object hGet(String key, String field);

    public long incr(final String key);

    public long incrBy(final String key, final long value);

    public long hdel(final String key, final String field);

    public boolean sAdd(final String key, final List<String> values);

    public boolean hSet(final String key, final String field, final String value);

    public boolean hSet(final String key, final String field, final Object value);

    public void hMSet(final String key, Map<String, Object> maps);
}
