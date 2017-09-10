package elacticsearch.restapi;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.ResponseListener;
import org.elasticsearch.client.RestClient;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * Created by kevinyin on 2017/9/9.
 */
public class TestRestfulAPI {


    @Test
    public void testConnetct(){
        RestClient restClient = ClientUtils.restClient();
        Assert.assertNotNull(restClient);
        ClientUtils.closeClient(restClient);
    }

    @Test
    public void testRequest() throws IOException {
        RestClient restClient = ClientUtils.restClient();
        Map<String,String> params = new HashMap<String, String>();
        Response response = restClient.performRequest("GET","/twitter/tweet/_search",params);
        System.out.println(EntityUtils.toString(response.getEntity()));
        ClientUtils.closeClient(restClient);
    }

    @Test
    public void testIndex() throws IOException {
        Message message = new Message("Tom",new Date(),"Hi,Tiny");

        HttpEntity entity = new NStringEntity(ClientUtils.objectToStirng(message), ContentType.APPLICATION_JSON);
        RestClient restClient = ClientUtils.restClient();
        Response response = restClient.performRequest(ClientUtils.PUT,"/twitter/tweet/1",
                Collections.<String, String>emptyMap(),entity);
        System.out.println(EntityUtils.toString(response.getEntity()));
        ClientUtils.closeClient(restClient);
    }

    @Test
    public void tesrRequestAsync() throws InterruptedException {
        RestClient restClient = ClientUtils.restClient();
        int numRequests = 10;
        final CountDownLatch latch = new CountDownLatch(numRequests);
        HttpEntity[] entities = new NStringEntity[numRequests];
        for (int i = 0; i < numRequests; i++) {
            Message message = new Message("Test"+i,new Date(),"msg"+i);
            entities[i] = new NStringEntity(ClientUtils.objectToStirng(message),ContentType.APPLICATION_JSON);
        }

        for (int i = 0; i < numRequests; i++) {
            restClient.performRequestAsync(
                    ClientUtils.PUT,
                    "/twitter/tweet/" + i,
                    Collections.<String, String>emptyMap(),
                    //assume that the documents are stored in an entities array
                    entities[i],
                    new ResponseListener() {
                        public void onSuccess(Response response) {
                            latch.countDown();
                        }

                        public void onFailure(Exception e) {
                            latch.countDown();
                        }
                    }
            );
        }
        //wait for all requests to be completed
        latch.await();
        System.out.println("SUCCESS");
    }

}

