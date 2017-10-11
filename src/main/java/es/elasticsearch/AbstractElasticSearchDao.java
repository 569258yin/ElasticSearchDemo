package es.elasticsearch;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;

import java.io.IOException;

/**
 * Created by kevinyin on 2017/9/9.
 */
public abstract class AbstractElasticSearchDao implements ElasticSearchDao {

    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static final String DELETE = "DELETE";

    public static final String MAPPING = "_mapping";
    public static final String BULK = "_bulk";



    protected RestClient restClient(){

        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("elastic", "123456"));

        return  RestClient.builder(
                new HttpHost("localhost",9200,"http"),
                new HttpHost("localhost",9201,"http")
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
                        return httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider).setDefaultIOReactorConfig(
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

    protected String toLowerCaseIndex(String index){
        return StringUtils.lowerCase(index);
    }



}
