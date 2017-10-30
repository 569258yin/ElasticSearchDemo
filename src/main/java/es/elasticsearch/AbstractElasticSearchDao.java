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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;

/**
 * Created by kevinyin on 2017/9/9.
 */
@PropertySource("classpath:configure.properties")
public abstract class AbstractElasticSearchDao implements ElasticSearchDao {

    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static final String DELETE = "DELETE";
    public static final String HEAD = "HEAD";

    public static final String MAPPING = "_mapping";
    public static final String BULK = "_bulk";
    public static final String DELETE_BY_QUERY = "_delete_by_query";
    public static final String ALIASES = "_aliases";
    public static final String ALIAS = "_alias";

    @Value("${elastic_search_host}")
    private String elasticHost;
    @Value("${elastic_search_port}")
    private int elasticPort;

    protected RestClient restClient(){

        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("elastic", "123456"));

        return  RestClient.builder(
                new HttpHost(elasticHost,elasticPort,"http")
        )
                .setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
                    @Override
                    public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
                        return requestConfigBuilder.setConnectTimeout(5000)
                                .setSocketTimeout(60000);
                    }
                })
                //设置IO分发线程数目，参考netty线程模型

                .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    @Override
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

    protected String endPoint(String index, String type) {
        return "/" + index + "/" + type;
    }


}
