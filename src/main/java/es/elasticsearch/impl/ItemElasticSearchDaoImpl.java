package es.elasticsearch.impl;

import es.elasticsearch.AbstractItemElasticSearchDao;
import es.utils.Constants;
import es.utils.EsDealResultUtils;
import es.utils.EsJsonUtils;
import es.item.bean.Item;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Created by kevinyin on 2017/9/9.
 */
@Repository("elasticSearchDao")
public class ItemElasticSearchDaoImpl extends AbstractItemElasticSearchDao {

    private static final Logger logger = LoggerFactory.getLogger(ItemElasticSearchDaoImpl.class);

    @Override
    public boolean createIndexType(String index, String type, String json) {

        return false;
    }

    @Override
    public boolean createIndexMapping(String index, String json) {
        RestClient restClient = null;
        try {
            restClient = restClient();
            index = toLowerCaseIndex(index);
            HttpEntity entity = new NStringEntity(json, ContentType.APPLICATION_JSON);
            Response response = restClient.performRequest(PUT,"/"+index, Collections.emptyMap(),entity);
            String result = EntityUtils.toString(response.getEntity(),Constants.UTF_8);
            logger.info("create Mapping result: "+result);
            return EsDealResultUtils.dealResponseResult(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (restClient != null) {
                closeClient(restClient);
            }
        }
        return false;
    }

    @Override
    public int insertData(String index, String type, String json) {
        RestClient restClient = null;
        try {
            restClient = restClient();
            index = toLowerCaseIndex(index);
            HttpEntity entity = new NStringEntity(json, ContentType.APPLICATION_JSON);
            Response response = restClient.performRequest(PUT,"/"+index, Collections.emptyMap(),entity);
            String result = EntityUtils.toString(response.getEntity(),Constants.UTF_8);
            logger.info("create Mapping result: "+result);
            return 1;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (restClient != null) {
                closeClient(restClient);
            }
        }
        return 0;
    }

    @Override
    public int multiDealData(String index, String type, String json) {
        RestClient restClient = null;
        try {
            restClient = restClient();
            HttpEntity entity = new NStringEntity(json, ContentType.APPLICATION_JSON);
            Response response = restClient.performRequest(POST,"/"+index+"/" +type +"/"+BULK, Collections.emptyMap(),entity);
            String result = EntityUtils.toString(response.getEntity(),Constants.UTF_8);
            logger.info("multiDealData  result: "+result);
            return 1;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (restClient != null) {
                closeClient(restClient);
            }
        }
        return 0;
    }

    @Override
    public boolean createNgramAyanalzer(String index) {
        return false;
    }

    @Override
    public List<String> search(String endpoint, String json) {
        RestClient restClient = null;
        try {
            restClient = restClient();
            HttpEntity entity = new NStringEntity(json, ContentType.APPLICATION_JSON);
            Response response = restClient.performRequest(GET,endpoint, Collections.emptyMap(),entity);
            String result = EntityUtils.toString(response.getEntity(), Constants.UTF_8);
            logger.info("search at ES result: "+result);
            return EsDealResultUtils.dealSearchItemResult(result);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (restClient != null) {
                closeClient(restClient);
            }
        }
        return null;
    }
}