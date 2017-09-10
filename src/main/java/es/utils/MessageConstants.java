package es.utils;

public class MessageConstants {

    public static final String TASK_TITLE = "您有新的任务";

    public static final String BILL_DELETE_TITLE = "流转单将被删除";

    public static final String PROCESSING_TITLE = "您的前一道工序有产出";
    public static final String ISSUE_TITLE = "有人向您反映了问题";

    public static final String UNAUTHORIZED = "无权限访问";

    public static final String INTERNAL_ERROR = "服务器内部错误";
    public static final String ERROR_PARAM = "参数不正确";

    public static final String SAME_NAME_ERROR = "已存在相同的名字";

    public static final String HAS_ORDER_PROCESSING = "此客户有订单正在进行，请结束相关订单后再删除";

    public static final String DATA_ERROR = "内部数据不正确";

    public static final String SUCCESS = "success";

    public static final String INVALID_ACCESS = "无效访问";

    public static final String DELETE_SUCCESS = "流转单删除成功";

    public static final String PUSH_SUCCESS = "删除信息已推送给相关人工，请等待他们的确认";

    public static final String MESSAGE_DELIVERED = "信息推送成功";

    public static final String ISSUE_CREATE_COMMENT_MSG = "创建了一个问题";

    public static final String NEGATIVE_INVENTORY_MESSAGE = "产品出库数量大于库存数,请修改数量或重新选择仓库后再提交";

    public static final String OVER_ACCOUNT_LIMIT = "开通用户数超过限制，请联系业务经理";

    public static String CompleteTaskMessage(String staffName, String procedureName) {
        return String.format("%s已完成%s工序，等待您的审核", staffName, procedureName);
    }

    public static String ReapprovalMessage(String supervisor, String procedureName) {
        return String.format("%s没有通过您%s工序的任务，请核对数据", supervisor, procedureName);
    }

    public static String IssueMessage(String assignee) {
        return String.format("%s遇到了一个问题，等待您解决", assignee);
    }
}
