package es.utils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by cao on 8/4/15.
 */
public class HttpClientUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);
    private static final CloseableHttpClient httpClient = HttpClients.createDefault();
    private static final RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(10000)
            .setConnectTimeout(10000)
            .setConnectionRequestTimeout(10000)
            .build();

    public static String sendGetRequest(String reqURL) {
        String respContent = "";
        try {
            HttpGet httpGet = new HttpGet(reqURL);
            httpGet.setConfig(requestConfig);
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = null;
            try {
                entity = httpResponse.getEntity();
                StatusLine statusLine = httpResponse.getStatusLine();
                if (statusLine != null && statusLine.getStatusCode() == 200) {
                    respContent = EntityUtils.toString(entity, "UTF-8");
                    entity = null;
                }
            } finally {
                EntityUtils.consume(entity);
                Closer.close(httpResponse);
            }
        } catch (IOException e) {
            logger.error("http get {} 请求失败", reqURL, e);
        }
        return respContent;
    }

    public static String sendGetRequest(String reqURL, Map<String, String> header) {
        String respContent = "";
        try {
            HttpGet httpGet = new HttpGet(reqURL);
            if (MapUtils.isNotEmpty(header)) {
                for (Entry<String, String> entry : header.entrySet()) {
                    httpGet.setHeader(entry.getKey(), entry.getValue());
                }
            }
            httpGet.setConfig(requestConfig);
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = null;
            try {
                entity = httpResponse.getEntity();
                StatusLine statusLine = httpResponse.getStatusLine();
                if (statusLine != null && statusLine.getStatusCode() == 200) {
                    respContent = EntityUtils.toString(entity, "UTF-8");
                    entity = null;
                }
            } finally {
                EntityUtils.consume(entity);
                Closer.close(httpResponse);
            }
        } catch (IOException e) {
            logger.error("http get {} 请求失败", reqURL, e);
        }
        return respContent;
    }

    public static String sendPostJSONRequest(String reqURL, String bodyData) {
        String respContent = "";
        try {
            HttpPost httpPost = new HttpPost(reqURL);
            httpPost.setConfig(requestConfig);
            if (StringUtils.isNotEmpty(bodyData)) {
                httpPost.setEntity(new StringEntity(bodyData, ContentType.create("application/json", Consts.UTF_8)));
            }
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity entity = null;
            try {
                entity = httpResponse.getEntity();
                StatusLine statusLine = httpResponse.getStatusLine();
                if (statusLine != null && statusLine.getStatusCode() == 200) {
                    respContent = EntityUtils.toString(entity, "UTF-8");
                    entity = null;
                }
            } finally {
                EntityUtils.consume(entity);
                Closer.close(httpResponse);
            }
        } catch (IOException e) {
            logger.error("http post {} 请求失败", reqURL, e);
        }
        return respContent;
    }

    public static String sendPostRequest(String reqURL, List<NameValuePair> nvps, String bodyData) {
        String respContent = "";
        try {
            HttpPost httpPost = new HttpPost(reqURL);
            httpPost.setConfig(requestConfig);
            if (CollectionUtils.isNotEmpty(nvps)) {
                httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
            }
            if (StringUtils.isNotEmpty(bodyData)) {
                httpPost.setEntity(new StringEntity(bodyData, ContentType.create("plain/text", Consts.UTF_8)));
            }
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity entity = null;
            try {
                entity = httpResponse.getEntity();
                StatusLine statusLine = httpResponse.getStatusLine();
                if (statusLine != null && statusLine.getStatusCode() == 200) {
                    respContent = EntityUtils.toString(entity, "UTF-8");
                    entity = null;
                }
            } finally {
                EntityUtils.consume(entity);
                Closer.close(httpResponse);
            }
        } catch (IOException e) {
            logger.error("http post {} 请求失败", reqURL, e);
        }
        return respContent;
    }


    public static String sendPostRequest(String reqURL, List<NameValuePair> nvps, String bodyData, Map<String, String> header) {
        String respContent = "";
        try {
            HttpPost httpPost = new HttpPost(reqURL);
            if (MapUtils.isNotEmpty(header)) {
                for (Entry<String, String> entry : header.entrySet()) {
                    httpPost.setHeader(entry.getKey(), entry.getValue());
                }
            }
            httpPost.setConfig(requestConfig);
            if (CollectionUtils.isNotEmpty(nvps)) {
                httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
            }
            if (StringUtils.isNotEmpty(bodyData)) {
                httpPost.setEntity(new StringEntity(bodyData, ContentType.create("plain/text", Consts.UTF_8)));
            }
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity entity = null;
            try {
                entity = httpResponse.getEntity();
                StatusLine statusLine = httpResponse.getStatusLine();
                if (statusLine != null && statusLine.getStatusCode() == 200) {
                    respContent = EntityUtils.toString(entity, "UTF-8");
                    entity = null;
                }
            } finally {
                EntityUtils.consume(entity);
                Closer.close(httpResponse);
            }
        } catch (IOException e) {
            logger.error("http post {} 请求失败", reqURL, e);
        }
        return respContent;
    }

}
