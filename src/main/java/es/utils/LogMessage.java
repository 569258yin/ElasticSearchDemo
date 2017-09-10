package es.utils;

import java.text.MessageFormat;

/**
 * Created by cao on 15/5/12.
 */
public class LogMessage {
    public static final int ORDER_LOG_TYPE = 1;
    public static final int PROCUREMENT_ORDER_LOG_TYPE = 3;

    //  订单操作日志类型
    public static class OrderOperationType {
        public static final String ORDER_CREATE = "创建订单";
        public static final String MODIFY_ORDER = "修改订单";
        public static final String DELETE_ORDER = "修改订单";
        public static final String ABANDON_ORDER = "废弃订单";
        public static final String ORDER_AUDIT = "订单审核";
        public static final String FINANCE_AUDIT = "财务审核";
        public static final String OUT_INVENTORY_AUDIT = "发货出库审核";
        public static final String RECEIVE_AUDIT = "收货审核";
    }

    //  采购订单操作日志类型
    public static class ProcurementOrderOperationType {
        public static final String SOURCE = "采购订单来源";
        public static final String ORDER_CREATE = "创建采购订单";
        public static final String MODIFY_ORDER = "修改采购订单";
        public static final String ABANDON_ORDER = "废弃采购订单";
        public static final String DELETE_ORDER = "删除采购订单";
        public static final String ORDER_AUDIT = "采购订单审核";
        public static final String FINANCE_AUDIT = "财务审核";
        public static final String RECEIVE_AUDIT = "收货审核";
    }

    //  Bom操作日志类型
    public static class BOMOperationType {
        public static final String CREATE_BOM = "创建物料清单";
        public static final String MODIFY_BOM = "修改物料清单";
        public static final String DELETE_BOM = "删除物料清单";
    }

    //  Bom操作日志
    public static class BomOperationLog {
        public static final String CREATE_BOM = "创建物料清单";
        public static final String MODIFY_BOM = "修改物料清单";
        public static final String DELETE_BOM = "删除物料清单";
    }

    //  发票操作日志类型
    public static class InvoiceOperationType {
        public static final String CREATE_INVOICE = "创建发票";
        public static final String MODIFY_INVOICE = "修改发票";
        public static final String AUDIT_INVOICE = "审核发票";
        public static final String ABANDON_INVOICE = "废弃发票";
        public static final String DELETE_INVOICE = "删除发票";
    }

    //  发票操作日志
    public static class InvoiceOperationLog {
        public static final MessageFormat CREATE_INVOICE = new MessageFormat("创建发票 \"{0}\"");
        public static final MessageFormat MODIFY_INVOICE = new MessageFormat("修改发票 \"{0}\"");
        public static final MessageFormat AUDIT_INVOICE = new MessageFormat("审核发票 \"{0}\"");
        public static final MessageFormat ABANDON_INVOICE = new MessageFormat("废弃发票 \"{0}\"");
        public static final MessageFormat DELETE_INVOICE = new MessageFormat("删除发票 \"{0}\"");
    }

    //  订单操作日志
    public static class OrderOperationLog {
        public static final String ORDER_CREATE_LOG = "已提交订单, 等待订单审核";
        public static final String ORDER_AUDIT_PASS = "订单审核通过";
        public static final String ORDER_AUDIT_BACK = "订单审核退回, 重新审核";
        public static final String FINANCE_AUDIT_PASS = "财务审核通过";
        public static final String FINANCE_AUDIT_BACK = "财务审核退回, 重新审核";
        public static final String OUT_INVENTORY_AUDIT_PASS = "发货出库审核通过";
        public static final String OUT_INVENTORY_AUDIT_BACK = "发货出库退回, 重新审核";
        public static final String RECEIVE_AUDIT_PASS = "收货审核通过";
        public static final String RECEIVE_AUDIT_BACK = "收货审核退回, 重新审核";
        public static final String MODIFY_ORDER_OUT_INVENTORY_LOG = "发货出库单作废";
        public static final String MODIFY_ORDER_PRODUCT_LOG = "修改订单商品";
        public static final String MODIFY_ORDER_INFO_LOG = "修改订单信息";
        public static final String ABANDON_ORDER__LOG = "订单作废";
        public static final String DELETE_ORDER_LOG = "订单删除";
    }

    //  采购订单操作日志
    public static class ProcurementOrderOperationLog {
        public static final String ORDER_CREATE_LOG = "创建采购单";
        public static final String ORDER_AUDIT_PASS = "采购订单审核通过";
        public static final String ORDER_AUDIT_BACK = "采购订单审核退回, 重新审核";
        public static final String FINANCE_AUDIT_PASS = "采购财务审核通过";
        public static final String FINANCE_AUDIT_BACK = "采购财务审核退回, 重新审核";
        public static final String RECEIVE_AUDIT_PASS = "采购收货审核通过";
        public static final String RECEIVE_AUDIT_BACK = "采购收货审核退回, 重新审核";
        public static final String MODIFY_ORDER_DELIVERY_LOG = "采购收货单作废";
        public static final String MODIFY_ORDER_PRODUCT_LOG = "修改采购订单商品";
        public static final String MODIFY_ORDER_INFO_LOG = "修改采购订单信息";
        public static final String ABANDON_ORDER__LOG = "采购订单作废";
        public static final String DELETE_ORDER_LOG = "采购单删除";
        public static final String FROM_REORDER_LOG = "采购单返单";
        public static final String FROM_WAREHOUSE_LOG = "仓库申请采购";
        public static final String FROM_SHOPPINGLIST_LOG = "申请采购清单";
        public static final String FROM_SCHEDULE_LOG = "物料需求计划申请采购清单";
        public static final String FROM_APPEND_SCHEDULE_LOG = "加入物料需求计划";
        public static final String FROM_CREATE_SCHEDULE_LOG = "创建物料需求计划";
        public static final String FROM_ORDER_LOG = "订单";
    }

    //  项目操作日志类型
    public static class ProjectOperationType {
        public static final String MODIFY_PROJECT = "1"; //项目信息修改
        public static final String MODIFY_PROJECT_STAGE = "2"; //项目信息修改
        public static final String ARCHIVE_PROJECT = "3"; //项目归档
        public static final String ACTIVE_PROJECT = "4"; //项目激活
        public static final String DELETE_PROJECT = "5"; //项目删除
        public static final String DELETE_PROJECT_TASK = "6"; //任务删除
        public static final String CREATE_PROJECT = "7"; //任务删除
    }

    //  任务操作日志类型
    public static class ProjectTaskOperationType {
        public static final String MODIFY_PARENT_TASK = "1"; //任务信息修改
        public static final String ACTIVE_PARENT_TASK = "2"; //任务重做
        public static final String FINISH_PARENT_TASK = "3"; //任务完成
        public static final String MODIFY_SUB_TASK = "4"; //子任务信息修改
        public static final String ACTIVE_SUB_TASK = "5"; //子任务重做
        public static final String FINISH_SUB_TASK = "6"; //子任务完成
        public static final String DELETE_SUB_TASK = "7"; //子任务删除
        public static final String DELETE_TASK_COMMENT = "8"; //删除任务评论
        public static final String UPDATE_PARENT_TIMER = "9"; //任务计时器启动
        public static final String UPDATE_SUB_TIMER = "10"; //子任务计时器启动
    }

    //  项目操作日志
    public static class ProjectOperationLog {
        public static final MessageFormat MODIFY_PROJECT = new MessageFormat("修改项目 \"{0}\"  信息, \"{1}\" 修改为 \"{2}\"");
        public static final MessageFormat MODIFY_PROJECT_INFO = new MessageFormat("修改项目 \"{0}\"  信息");
        public static final MessageFormat MODIFY_PROJECT_STAGE = new MessageFormat("修改列表名称为 \"{0}\"");
        public static final String ARCHIVE_PROJECT = "归档项目";
        public static final String ACTIVE_PROJECT = "激活项目";
        public static final String DELETE_PROJECT = "删除项目";
        public static final String CREATE_PROJECT = "创建项目";
        public static final MessageFormat DELETE_PROJECT_TASK = new MessageFormat("删除项目任务 \"{0}\"");
    }

    //  任务操作日志
    public static class ProjectTaskOperationLog {
        public static final MessageFormat MODIFY_PARENT_TASK = new MessageFormat("修改 \"{0}\" 任务的 \"{1}\" 信息, \"{2}\" 修改为 \"{3}\"");
        public static final MessageFormat START_PARENT_TIMER = new MessageFormat("启动 \"{0}\" 任务的计时器");
        public static final MessageFormat STOP_PARENT_TIMER = new MessageFormat("停止 \"{0}\" 任务的计时器");
        public static final MessageFormat ACTIVE_PARENT_TASK = new MessageFormat("重新激活任务 \"{0}\"");
        public static final MessageFormat FINISH_PARENT_TASK = new MessageFormat("完成任务 \"{0}\"");
        public static final MessageFormat MODIFY_SUB_TASK = new MessageFormat("修改 \"{0}\" 子任务的 \"{1}\" 信息, \"{2}\" 修改为 \"{3}\"");
        public static final MessageFormat START_SUB_TIMER = new MessageFormat("启动 \"{0}\" 子任务的计时器");
        public static final MessageFormat STOP_SUB_TIMER = new MessageFormat("停止 \"{0}\" 子任务的计时器");
        public static final MessageFormat ACTIVE_SUB_TASK = new MessageFormat("重新激活任务 \"{0}\" 的子任务 \"{1}\"");
        public static final MessageFormat FINISH_SUB_TASK = new MessageFormat("完成任务 \"{0}\" 的子任务 \"{1}\"");
        public static final MessageFormat DELETE_SUB_TASK = new MessageFormat("删除任务 \"{0}\" 的子任务 \"{1}\"");
        public static final String DELETE_TASK_COMMENT = "删除任务评论";
        public static final MessageFormat ADD_TASK_MANAGER = new MessageFormat("修改 \"{0}\" 任务的 \"任务负责人\" 信息, 新增 \"{1}\"");
        ;
        public static final MessageFormat DELETE_TASK_MANAGER = new MessageFormat("修改 \"{0}\" 任务的 \"任务负责人\" 信息, 删除 \"{1}\"");
        ;
    }

    public static class MachineManagementType {
        public static final String ADD_MACHINE = "设备信息新增";
        public static final String UPDATE_MACHINE = "设备信息修改";
        public static final String AUDIT_MACHINE = "设备信息审批";
        public static final String PENDING_EDIT_MACHINE = "设备信息待修改";
    }

    public static class MachineManagementLog {
        public static final String ADD_MACHINE = "新增设备";
        public static final MessageFormat RENAME_MACHINE = new MessageFormat("修改设备名称: 将设备名称由\"{0}\"改为\"{1}\"");
        public static final MessageFormat MODIFY_PROCEDURE = new MessageFormat("修改应用工序: 将工序由\"{0}\"改为\"{1}\"");
        public static final MessageFormat CHANGE_MACHINE_STATE = new MessageFormat("修改设备状态: 将设备状态由\"{0}\"改为\"{1}\"");
        public static final String APPROVE = "设备信息审批通过";
        public static final String REJECT = "设备信息审批不通过";
    }

    public static class StockBatchManagementType {
        public static final String MODIFY_SHELF_LIFE = "修改保质期";
    }

    //  发票操作日志
    public static class StockBatchManagementLog {
        public static final MessageFormat MODIFY_SHELF_LIFE = new MessageFormat("产品\"{0}\" 修改保质期: 由\"{1}\"改为\"{2}\"");
    }

}
