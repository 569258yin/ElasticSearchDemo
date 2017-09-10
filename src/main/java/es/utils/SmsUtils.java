package es.utils;

import com.google.common.collect.Lists;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.List;
import java.util.Map;


public class SmsUtils {

    private static final String API_KEY = "bd82d3d4af010d903d08ffb8d139f0be";

    private static final String BASE_URI = "http://yunpian.com";

    private static final String VERSION = "v1";

    private static final String DEFAULT_ENCODING = "UTF-8";

    private static final String SEND_SMS_URI = BASE_URI + "/" + VERSION + "/sms/send.json";

    private static final String SEND_TPL_SMS_URI = BASE_URI + "/" + VERSION + "/sms/tpl_send.json";

    private static final String DEFAULT_TEMPLATE_ID = "675827";

    private static final String DEFAULT_TEMPLATE_PARAM = "#code#=";

    public static boolean sendValidationMessage(String mobile, String code) {

        List<NameValuePair> nameValuePairs = Lists.newArrayListWithExpectedSize(4);
        nameValuePairs.add(new BasicNameValuePair("apikey", API_KEY));
        nameValuePairs.add(new BasicNameValuePair("tpl_id", DEFAULT_TEMPLATE_ID));
        nameValuePairs.add(new BasicNameValuePair("tpl_value", DEFAULT_TEMPLATE_PARAM + code));
        nameValuePairs.add(new BasicNameValuePair("mobile", mobile));
        String resultString = HttpClientUtils.sendPostRequest(SEND_TPL_SMS_URI, nameValuePairs, null);
        Map<String, Object> resultMap = JsonUtils.jsonDecodeMap(resultString);
        return resultMap.containsKey("msg") && resultMap.get("msg").equals("OK");
    }

    public static boolean sendDeliveryMessage(String mobile, String tenantName, String orderNumber,
                                              String deliveryCompany, String deliveryOrderNumber, String contacts, String contactsMobile) {
        StringBuilder builder = new StringBuilder();
        List<NameValuePair> nameValuePairs = Lists.newArrayListWithExpectedSize(4);

        nameValuePairs.add(new BasicNameValuePair("apikey", API_KEY));
        nameValuePairs.add(new BasicNameValuePair("tpl_id", "1068841"));
        builder.append("#client#=").append(tenantName).append("&#order_id#=").append(orderNumber).
                append("&#delivery_company#=").append(deliveryCompany).append("&#delivery_order#=").append(deliveryOrderNumber).
                append("&#contacts#=").append(contacts).append("&#mobile#=").append(contactsMobile);
        nameValuePairs.add(new BasicNameValuePair("tpl_value", builder.toString()));
        nameValuePairs.add(new BasicNameValuePair("mobile", mobile));
        String resultString = HttpClientUtils.sendPostRequest(SEND_TPL_SMS_URI, nameValuePairs, null);
        Map<String, Object> resultMap = JsonUtils.jsonDecodeMap(resultString);
        return resultMap.containsKey("msg") && resultMap.get("msg").equals("OK");
    }

    public static boolean sendMergeDeliveryMessage(String mobile, String tenantName,
                                                   String deliveryCompany, String deliveryOrderNumber, String contacts, String contactsMobile) {
        StringBuilder builder = new StringBuilder();
        List<NameValuePair> nameValuePairs = Lists.newArrayListWithExpectedSize(4);

        nameValuePairs.add(new BasicNameValuePair("apikey", API_KEY));
        nameValuePairs.add(new BasicNameValuePair("tpl_id", "1825040"));
        builder.append("#client#=").append(tenantName).
                append("&#delivery_company#=").append(deliveryCompany).append("&#delivery_order#=").append(deliveryOrderNumber).
                append("&#contacts#=").append(contacts).append("&#mobile#=").append(contactsMobile);
        nameValuePairs.add(new BasicNameValuePair("tpl_value", builder.toString()));
        nameValuePairs.add(new BasicNameValuePair("mobile", mobile));
        String resultString = HttpClientUtils.sendPostRequest(SEND_TPL_SMS_URI, nameValuePairs, null);
        Map<String, Object> resultMap = JsonUtils.jsonDecodeMap(resultString);
        return resultMap.containsKey("msg") && resultMap.get("msg").equals("OK");
    }

    public static boolean sendInviteMessage(String company, String phone, String password) {
        List<NameValuePair> nameValuePairs = Lists.newArrayListWithExpectedSize(4);
        nameValuePairs.add(new BasicNameValuePair("apikey", API_KEY));
        nameValuePairs.add(new BasicNameValuePair("tpl_id", "1359127"));
        StringBuilder builder = new StringBuilder();
        builder.append("#company#=").append(company).append("&#phone#=").append(phone).append("&#password#=").append(password);
        nameValuePairs.add(new BasicNameValuePair("tpl_value", builder.toString()));
        nameValuePairs.add(new BasicNameValuePair("mobile", phone));
        String resultString = HttpClientUtils.sendPostRequest(SEND_TPL_SMS_URI, nameValuePairs, null);
        Map<String, Object> resultMap = JsonUtils.jsonDecodeMap(resultString);
        return resultMap.containsKey("msg") && resultMap.get("msg").equals("OK");
    }
}
