package es.redis.impl;

import com.google.common.collect.Maps;
import es.item.bean.enumeration.RedisKeysEnum;
import es.redis.RedisDao;
import es.utils.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.SerializationUtils;

import javax.annotation.Resource;
import java.util.*;
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
        if (StringUtils.isEmpty(key))
            return null;
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
        if (StringUtils.isEmpty(key) || MapUtils.isEmpty(maps))
            return;
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
        if (StringUtils.isEmpty(key))
            return MapUtils.EMPTY_MAP;
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
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(field))
            return StringUtils.EMPTY;
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
        if (StringUtils.isEmpty(key))
            return 0;
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.incr(key.getBytes());
            }
        });
    }


    @Override
    public long incrBy(final String key, final long value) {
        if (StringUtils.isEmpty(key))
            return 0;
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
        if (StringUtils.isEmpty(key))
            return 0;
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

    @Override
    public String generateDaliyNumber(RedisKeysEnum redisKey, String tenantID) {
        if (redisKey == null || StringUtils.isEmpty(tenantID)) {
            return StringUtils.EMPTY;
        }
        StringBuilder builder = new StringBuilder();
        String date;
        if (redisKey == RedisKeysEnum.INVOICE_NUMBER) {
            date = DateUtils.getFormatNoConnectorDateTime(new Date());
        } else {
            date = DateUtils.getFormatNoConnectorSimpleDate(new Date());
        }
        builder.append(tenantID).append(date).append(redisKey.getPrefix());
        String key = builder.toString();
        if (StringUtils.isEmpty((String) this.get(key))) {
            incr(key);
            expire(key, DateUtil.SECONDS_PER_DAY);
        }
        long incrementNumber = NumberUtils.toLong((String) this.get(key), 1);
        StringBuilder stringBuilder = new StringBuilder();
        switch (redisKey) {
            case BILL_NUMBER:
                stringBuilder.append("B-").append(date).append("-").append(
                        StringUtils.leftPad(String.valueOf(incrementNumber), 3, "0"));
                break;
            case INVENTORY_ID:
                stringBuilder.append("L-").append(date).append("-").append(
                        StringUtils.leftPad(String.valueOf(incrementNumber), 3, "0"));
                break;
            case PAYMENT_NUMBER:
                stringBuilder.append(date).append(StringUtils.leftPad(String.valueOf(incrementNumber), 8, "0"));
                break;
            case INVOICE_NUMBER:
                stringBuilder.append(date).append(StringUtils.leftPad(String.valueOf(incrementNumber), 4, "0"));
                break;
            case ORDER_NUMBER:
                stringBuilder.append("O-").append(date).append("-").append(
                        StringUtils.leftPad(String.valueOf(incrementNumber), 3, "0"));
                break;
            case PROJECT_NUMBER:
                stringBuilder.append("PJ-").append(date).append("-").append(
                        StringUtils.leftPad(String.valueOf(incrementNumber), 3, "0"));
                break;
            case PROCUREMENT_ORDER_NUMBER:
                stringBuilder.append("P-").append(date).append("-").append(
                        StringUtils.leftPad(String.valueOf(incrementNumber), 3, "0"));
                break;
            case OUTSOURCE_ORDER_NUMBER:
                stringBuilder.append("W-").append(date).append("-").append(
                        StringUtils.leftPad(String.valueOf(incrementNumber), 3, "0"));
                break;
        }
        return stringBuilder.toString();
    }

    @Override
    public String generateAdditionalBillNumber(String billNumber, String tenantID) {
        if (StringUtils.isEmpty(billNumber) || StringUtils.isEmpty(tenantID)) {
            return StringUtils.EMPTY;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(tenantID).append(billNumber).append(RedisKeysEnum.BILL_NUMBER.getPrefix());
        String key = builder.toString();
        if (StringUtils.isEmpty((String) this.get(key))) {
            incr(key);
        }
        long incrementNumber = NumberUtils.toLong((String) this.get(key), 1);
        String number;
        if (incrementNumber == 1) {
            number = billNumber + "追";
        } else {
            number = billNumber + "追" + incrementNumber;
        }

        return number;
    }


    @Override
    public String generateProjectNumber(String tenantId) {
        if (StringUtils.isEmpty(tenantId))
            return StringUtils.EMPTY;
        Date now = new Date();
        this.del(tenantId + DateUtils.getFormatNoConnectorSimpleDate(DateUtils.previousDay(now)) + RedisKeysEnum.PROJECT_NUMBER.getPrefix());
        String date = DateUtils.getFormatNoConnectorSimpleDate(now);
        StringBuilder builder = new StringBuilder();
        builder.append(tenantId).append(date).append(RedisKeysEnum.PROJECT_NUMBER.getPrefix());
        long incrementOrderNumber = this.incr(builder.toString());
        expire(builder.toString(), DateUtil.SECONDS_PER_DAY);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("PJ-").append(date).append("-").append(
                StringUtils.leftPad(String.valueOf(incrementOrderNumber), 3, "0"));
        return stringBuilder.toString();
    }

    @Override
    public String generateLogisticsStockNumber(String tenantId) {
        if (StringUtils.isEmpty(tenantId))
            return StringUtils.EMPTY;
        Date now = new Date();
        this.del(tenantId + DateUtils.getFormatNoConnectorSimpleDate(DateUtils.previousDay(now)) + RedisKeysEnum.LOGISTICS_STOCK_NUMBER.getPrefix());
        String date = DateUtils.getFormatNoConnectorSimpleDate(now);
        StringBuilder builder = new StringBuilder();
        builder.append(tenantId).append(date).append(RedisKeysEnum.LOGISTICS_STOCK_NUMBER.getPrefix());
        long incrementOrderNumber = this.incr(builder.toString());
        expire(builder.toString(), DateUtil.SECONDS_PER_DAY);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("I-").append(date).append("-").append(
                StringUtils.leftPad(String.valueOf(incrementOrderNumber), 3, "0"));
        return stringBuilder.toString();
    }

    @Override
    public String generateSalesReturnNumber(String tenantId) {
        if (StringUtils.isEmpty(tenantId))
            return StringUtils.EMPTY;
        Date now = new Date();
        this.del(tenantId + DateUtils.getFormatNoConnectorSimpleDate(DateUtils.previousDay(now)) + RedisKeysEnum.SALES_RETURN_NUMBER.getPrefix());
        String date = DateUtils.getFormatNoConnectorSimpleDate(now);
        StringBuilder builder = new StringBuilder();
        builder.append(tenantId).append(date).append(RedisKeysEnum.SALES_RETURN_NUMBER.getPrefix());
        long incrementOrderNumber = this.incr(builder.toString());
        expire(builder.toString(), DateUtil.SECONDS_PER_DAY);
        return "R-" + date + "-" + StringUtils.leftPad(String.valueOf(incrementOrderNumber), 3, "0");
    }

    @Override
    public String generateReceiveNumber(String tenantId) {
        if (StringUtils.isEmpty(tenantId))
            return StringUtils.EMPTY;
        Date now = new Date();
        this.del(tenantId + DateUtils.getFormatNoConnectorSimpleDate(DateUtils.previousDay(now)) + RedisKeysEnum.PROCUREMENT_RECEIVE_NUMBER.getPrefix());
        String date = DateUtils.getFormatNoConnectorSimpleDate(now);
        StringBuilder builder = new StringBuilder();
        builder.append(tenantId).append(date).append(RedisKeysEnum.PROCUREMENT_RECEIVE_NUMBER.getPrefix());
        long incrementOrderNumber = this.incr(builder.toString());
        expire(builder.toString(), DateUtil.SECONDS_PER_DAY);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("R-").append(date).append("-").append(
                StringUtils.leftPad(String.valueOf(incrementOrderNumber), 3, "0"));
        return stringBuilder.toString();
    }

    @Override
    public String generateInventoryID(String tenantID) {
        if (StringUtils.isEmpty(tenantID))
            return StringUtils.EMPTY;
        Date now = new Date();
        this.del(tenantID + DateUtils.getFormatNoConnectorSimpleDate(DateUtils.previousDay(now)) + RedisKeysEnum.INVENTORY_ID.getPrefix());
        String date = DateUtils.getFormatNoConnectorSimpleDate(now);
        StringBuilder builder = new StringBuilder();
        builder.append(tenantID).append(date).append(RedisKeysEnum.INVENTORY_ID.getPrefix());
        String key = builder.toString();
        long incrementOrderNumber = this.incr(key);
        this.expire(key, DateUtil.SECONDS_PER_DAY);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("L-").append(date).append("-").append(
                StringUtils.leftPad(String.valueOf(incrementOrderNumber), 3, "0"));
        return stringBuilder.toString();
    }

    @Override
    public String generatePaymentNumber(String tenantID) {
        if (StringUtils.isEmpty(tenantID)) {
            return StringUtils.EMPTY;
        }
        Date now = new Date();
        this.del(tenantID + DateUtils.getFormatNoConnectorSimpleDate(DateUtils.previousDay(now)) + RedisKeysEnum.PAYMENT_NUMBER.getPrefix());
        String date = DateUtils.getFormatNoConnectorSimpleDate(now);
        StringBuilder builder = new StringBuilder();
        builder.append(tenantID).append(date).append(RedisKeysEnum.PAYMENT_NUMBER.getPrefix());
        long incrementNumber = this.incr(builder.toString());
        expire(builder.toString(), DateUtil.SECONDS_PER_DAY);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date).append(StringUtils.leftPad(String.valueOf(incrementNumber), 8, "0"));
        return stringBuilder.toString();
    }


    @Override
    public String generatePricingPlanNumber(String tenantID) {
        if (StringUtils.isEmpty(tenantID)) {
            return StringUtils.EMPTY;
        }
        Date now = new Date();
        this.del(tenantID + DateUtils.getFormatNoConnectorSimpleDate(DateUtils.previousDay(now)) + RedisKeysEnum.PRICING_PLAN_NUMBER.getPrefix());
        String date = DateUtils.getFormatNoConnectorSimpleDate(now);
        StringBuilder builder = new StringBuilder();
        builder.append(tenantID).append(date).append(RedisKeysEnum.PAYMENT_NUMBER.getPrefix());
        long incrementNumber = this.incr(builder.toString());
        expire(builder.toString(), DateUtil.SECONDS_PER_DAY);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date).append("-").append(StringUtils.leftPad(String.valueOf(incrementNumber), 2, "0"));
        return stringBuilder.toString();
    }

    @Override
    public String generateOutsourceOrderNumber(String tenantID) {
        if (StringUtils.isEmpty(tenantID)) {
            return StringUtils.EMPTY;
        }
        Date now = new Date();
        this.del(tenantID + DateUtils.getFormatNoConnectorSimpleDate(DateUtils.previousDay(now)) + RedisKeysEnum.OUTSOURCE_ORDER_NUMBER.getPrefix());
        String date = DateUtils.getFormatNoConnectorSimpleDate(now);
        StringBuilder builder = new StringBuilder();
        builder.append(tenantID).append(date).append(RedisKeysEnum.OUTSOURCE_ORDER_NUMBER.getPrefix());
        long incrementNumber = this.incr(builder.toString());
        expire(builder.toString(), DateUtil.SECONDS_PER_DAY);
        return ("W-").concat(date).concat("-").concat(StringUtils.leftPad(String.valueOf(incrementNumber), 3, "0"));
    }

    @Override
    public String generateStockNumber(String tenantID) {
        if (StringUtils.isEmpty(tenantID))
            return StringUtils.EMPTY;
        Date now = new Date();
        this.del(tenantID + DateUtils.getFormatNoConnectorSimpleDate(DateUtils.previousDay(now)) + RedisKeysEnum.STOCK_NUMBER.getPrefix());
        String date = DateUtils.getFormatNoConnectorSimpleDate(now);
        StringBuilder builder = new StringBuilder();
        builder.append(tenantID).append(date).append(RedisKeysEnum.STOCK_NUMBER.getPrefix());
        long incrementNumber = this.incr(builder.toString());
        expire(builder.toString(), DateUtil.SECONDS_PER_DAY);
        return ("IB-").concat(date).concat("-").concat(StringUtils.leftPad(String.valueOf(incrementNumber), 3, "0"));
    }

    @Override
    public String generateItemCode(String type, String tenantID) {
        return null;
    }

    @Override
    public List<String> generateItemCode(String type, int quantity, String tenantID) {
        return null;
    }

    @Override
    public List<String> generateQRCodeID(int quantity, String tenantID) {
        return null;
    }

    @Override
    public List<String> generateSN(String billNumber, int quantity, String tenantID) {
        return null;
    }

    /****************
     * DO NOT DELETE
     *************************/


}
