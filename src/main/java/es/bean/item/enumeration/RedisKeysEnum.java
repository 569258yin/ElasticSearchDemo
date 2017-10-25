package es.bean.item.enumeration;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by kevinyin on 2017/9/9.
 */
public enum RedisKeysEnum {
    TASK_PREFIX("TASK", "生产任务"),
    PROCEDURETASK_PREFIX("PTASK", "工序任务"),
    TASK_SUMMARY("TASK_SUMMARY", "任务数量"),
    PRODUCTION_STATISTIC("PRODUCTION_STATISTIC", "生产统计"),
    INVENTORY_ID("INVENTORYID", "库存记录编号"),
    PAYMENT_NUMBER("PAYMENTNUMBER", "支付流水号"),
    INVOICE_NUMBER("INVOICENUMBER", "发票流水号"),
    ORDER_NUMBER("ORDERNUMBER", "订单号"),
    PROJECT_NUMBER("PROJECTNUMBER", "项目号"),
    LOGISTICS_STOCK_NUMBER("LOGISTICS_STOCK_NUMBER", "发货出库号"),
    SALES_RETURN_NUMBER("SALES_RETURN_NUMBER", "退货编号"),
    PROCUREMENT_RECEIVE_NUMBER("PROCUREMENT_RECEIVE_NUMBER", "收货编号"),
    PROCUREMENT_ORDER_NUMBER("PROCUREMENTORDERNUMBER", "采购订单号"),
    PRICING_PLAN_NUMBER("PRICING_PLAN_NUMBER", "产品价格方案"),
    BILL_NUMBER("BILLNUMBER", "流转单号"),
    SHOPPING_CART("SHOPPING_CART", "购物车"),
    ITEM_CODE("ITEM_CODE", "item编码"),//标签码
    QRCODE_ID("QRCODE_ID", "二维码ID"),
    SYSTEM_CONFIG("CONFIG", " 系统设置"),
    OUTSOURCE_ORDER_NUMBER("OUTSOURCE_ORDER_NUMBER", "委外单号"),
    SERIAL_NUMBER("SERIAL_NUMBER", "序列号"),
    STOCK_NUMBER("STOCK_NUMBER", "库存批次号");

    private String prefix;
    private String desc;


    private RedisKeysEnum(String prefix, String desc) {
        this.prefix = prefix;
        this.desc = desc;
    }

    public String getPrefix() {
        return prefix;
    }


    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }


    public String getDesc() {
        return desc;
    }


    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String keys(int id) {
        if (id == 0) {
            return StringUtils.EMPTY;
        }
        return this.prefix + String.valueOf(id);
    }

    public String keys(String id) {
        if (StringUtils.isEmpty(id))
            return StringUtils.EMPTY;
        return this.prefix + id;
    }

}
