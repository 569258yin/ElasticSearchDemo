package elacticsearch.restapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;

import java.io.*;

/**
 * Created by kevinyin on 2017/9/9.
 */
public class ClientUtils {

    private static final ObjectMapper mapper= new ObjectMapper();

    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PUT = "PUT";


    private static String host = "";
    private static int port = 9200;


    public static RestClient restClient(){
        return  RestClient.builder(
                new HttpHost(host,port,"http")
        )
                .setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
                    public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
                        return requestConfigBuilder.setConnectTimeout(5000)
                                .setSocketTimeout(60000);
                    }
                })
                //设置IO分发线程数目，参考netty线程模型
                .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpAsyncClientBuilder) {
                        return httpAsyncClientBuilder.setDefaultIOReactorConfig(
                                IOReactorConfig.custom().setIoThreadCount(1).build()
                        );
                    }
                })
                .setMaxRetryTimeoutMillis(60000)
                .build();
    }

    public static void closeClient(RestClient restClient){
        if (restClient != null) {
            try {
                restClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String streamToString(InputStream inputStream){
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "/n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static String objectToStirng(Object obj){
        if (obj == null) {
            return "";
        }
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    public static Object stringToObject(String str,Class clazz){
        try {
            return mapper.readValue(str,clazz);
        } catch (IOException e) {
            return null;
        }
    }
}
