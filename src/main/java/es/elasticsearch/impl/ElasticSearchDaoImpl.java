package es.elasticsearch.impl;

import es.elasticsearch.AbstractElasticSearchDao;
import es.utils.Constants;
import es.utils.EsDealResultUtils;
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
public class ElasticSearchDaoImpl extends AbstractElasticSearchDao {

    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchDaoImpl.class);

    /**
     * 创建索引和类型
     *
     * @param index 索引
     * @param type  类别
     * @param json
     * @return
     */
    @Override
    public boolean createIndexType(String index, String type, String json) {
        RestClient restClient = null;
        try {
            restClient = restClient();
            HttpEntity entity = new NStringEntity(json, ContentType.APPLICATION_JSON);
            Response response = restClient.performRequest(PUT, "/" + index + "/" + type, Collections.emptyMap(), entity);
            String result = EntityUtils.toString(response.getEntity(), Constants.UTF_8);
            logger.info("delete Mapping result: " + result);
            return EsDealResultUtils.dealResponseResult(result);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (restClient != null) {
                closeClient(restClient);
            }
        }
        return false;
    }

    /**
     * 删除索引
     *
     * @param index
     * @return
     */
    @Override
    public boolean deleteIndex(String index) {
        RestClient restClient = null;
        try {
            restClient = restClient();
            Response response = restClient.performRequest(DELETE, "/" + index, Collections.emptyMap());
            String result = EntityUtils.toString(response.getEntity(), Constants.UTF_8);
            logger.info("delete Mapping result: " + result);
            return EsDealResultUtils.dealResponseResult(result);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (restClient != null) {
                closeClient(restClient);
            }
        }
        return false;
    }

    /**
     * 根据mapping设置创建索引
     *
     * @param index
     * @param json
     * @return
     */
    @Override
    public boolean createIndexMapping(String index, String json) {
        RestClient restClient = null;
        try {
            restClient = restClient();
            index = toLowerCaseIndex(index);
            HttpEntity entity = new NStringEntity(json, ContentType.APPLICATION_JSON);
            Response response = restClient.performRequest(PUT, "/" + index, Collections.emptyMap(), entity);
            String result = EntityUtils.toString(response.getEntity(), Constants.UTF_8);
            logger.info("create Mapping result: " + result);
            return EsDealResultUtils.dealResponseResult(result);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (restClient != null) {
                closeClient(restClient);
            }
        }
        return false;
    }

    /**
     * 单条插入数据
     *
     * @param index
     * @param type
     * @param json
     * @return
     */
    @Override
    public int insertData(String index, String type, String json) {
        RestClient restClient = null;
        try {
            restClient = restClient();
            index = toLowerCaseIndex(index);
            HttpEntity entity = new NStringEntity(json, ContentType.APPLICATION_JSON);
            Response response = restClient.performRequest(PUT, "/" + index, Collections.emptyMap(), entity);
            String result = EntityUtils.toString(response.getEntity(), Constants.UTF_8);
            logger.info("create Mapping result: " + result);
            return 1;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (restClient != null) {
                closeClient(restClient);
            }
        }
        return 0;
    }

    /**
     * 批量操作数据
     *
     * @param index
     * @param type
     * @param json
     * @return
     */
    @Override
    public boolean bulkDealData(String index, String type, String json) {
        RestClient restClient = null;
        try {
            restClient = restClient();
            HttpEntity entity = new NStringEntity(json, ContentType.APPLICATION_JSON);
            Response response = restClient.performRequest(POST, "/" + index + "/" + type + "/" + BULK, Collections.emptyMap(), entity);
            String result = EntityUtils.toString(response.getEntity(), Constants.UTF_8);
            logger.info("bulkDealData  result: " + result);
            return EsDealResultUtils.dealBulkInsertResult(result);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (restClient != null) {
                closeClient(restClient);
            }
        }
        return false;
    }

    @Override
    public String getById(String index, String type, Object id) {
        RestClient restClient = null;
        try {
            restClient = restClient();
            Response response = restClient.performRequest(GET, endPoint(index,type)+"/"+id, Collections.emptyMap());
            String result = EntityUtils.toString(response.getEntity(), Constants.UTF_8);
            logger.info("getById at ES result: " + result);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (restClient != null) {
                closeClient(restClient);
            }
        }
        return null;
    }

    @Override
    public boolean deleteById(String index, String type, Object id) {
        RestClient restClient = null;
        try {
            restClient = restClient();
            Response response = restClient.performRequest(DELETE, endPoint(index,type)+"/"+id, Collections.emptyMap());
            String result = EntityUtils.toString(response.getEntity(), Constants.UTF_8);
            logger.info("deleteById at ES result: " + result);
            return EsDealResultUtils.dealDeleteData(result);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (restClient != null) {
                closeClient(restClient);
            }
        }
        return false;
    }

    @Override
    public boolean deleteByQuery(String index, String type, String json) {
        RestClient restClient = null;
        try {
            restClient = restClient();
            HttpEntity entity = new NStringEntity(json, ContentType.APPLICATION_JSON);
            Response response = restClient.performRequest(DELETE, endPoint(index,type)+"/"+DELETE_BY_QUERY, Collections.emptyMap(),entity);
            String result = EntityUtils.toString(response.getEntity(), Constants.UTF_8);
            logger.info("deleteByQuery at ES result: " + result);
            return EsDealResultUtils.dealDeleteDataByQuery(result);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (restClient != null) {
                closeClient(restClient);
            }
        }
        return false;
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
            Response response = restClient.performRequest(GET, endpoint, Collections.emptyMap(), entity);
            String result = EntityUtils.toString(response.getEntity(), Constants.UTF_8);
            logger.info("search at ES result: " + result);
            return EsDealResultUtils.dealSearchItemResult(result);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (restClient != null) {
                closeClient(restClient);
            }
        }
        return null;
    }
}
