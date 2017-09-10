package es.utils;

import com.google.common.base.Charsets;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by david on 15/12/2.
 */
public class RevManifestUtils {
    private static LoadingCache<String, Map<String, Object>> cahceBuilder;
    private final static String FILE_NAME = "rev-manifest.json";

    static {
        cahceBuilder = CacheBuilder.newBuilder().build(
                new CacheLoader<String, Map<String, Object>>() {
                    @Override
                    public Map<String, Object> load(String key)
                            throws Exception {
                        File manifest = new File(RevManifestUtils.class
                                .getClassLoader().getResource("").getPath()
                                + FILE_NAME);
                        String json = "";
                        try {
                            json = Files.toString(manifest, Charsets.UTF_8);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Map<String, Object> map = JsonUtils.jsonDecodeMap(json);
                        return map;
                    }
                });
    }

    public static String getHashedName(String key) {
        String value = "";
        try {
            Map<String, Object> map = cahceBuilder.get(FILE_NAME);
            Object val = map.get(key);
            value = val != null ? val.toString() : "";
        } catch (ExecutionException e) {
            System.out.println("error happened");
            e.printStackTrace();
        }
        return value;
    }

    private static void clearCache() {
        cahceBuilder.invalidateAll();
    }

    /**
     * 刷新本地revManifest文件缓存
     *
     * @param revManifestJson json类型数据
     * @throws IOException
     */
    public static void updateRevManifest(String revManifestJson) throws IOException {

        // file和revManifestJson不能同时为空，获取json字符串
        String newData = revManifestJson;

        // 将json转为map 需要修改的数据
        Map<String, Object> newDataMap = JsonUtils.jsonDecodeMap(newData);

        // 读取旧文件数据
        File manifest = new File(RevManifestUtils.class.getClassLoader()
                .getResource("").getPath()
                + FILE_NAME);
        Map<String, Object> oldDataMap = null;
        if (!manifest.exists() || manifest.length() == 0) {
            oldDataMap = new HashMap<>();
        } else {
            oldDataMap = JsonUtils.jsonDecodeMap(Files.toString(manifest, Charsets.UTF_8));
        }

        // 增加或添加数据到旧数据中
        for (String key : newDataMap.keySet()) {
            oldDataMap.put(key, newDataMap.get(key));
        }

        // 将数据写入文件中
        Files.write(JsonUtils.generateJson(oldDataMap).getBytes(), manifest);
        // 刷新缓存
        clearCache();
    }
}
