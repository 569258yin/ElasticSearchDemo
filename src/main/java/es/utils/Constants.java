package es.utils;

/**
 * Created by cao on 14/10/20.
 */
public class Constants {

    public static final long ONE_DAY = (long) 24 * 60 * 60 * 1000;

    public static final long SHARE_PWD = 3825;

    public static final String CHINA_CALLINGCODE = "86";

   public static final String ITEM = "item";

   public static final String INDEX_SPLIT = "_";

    public static final String UTF_8 = "utf-8";

    //=======================================Search Param================================================
    public static final int DEFAULT_SIZE = 10;


    //============================================ES==========================================
    public static final String SEARCH = "_search";

    public static final String AFTER_SEARCH_ITEM = Constants.INDEX_SPLIT+Constants.ITEM+"/"+Constants.ITEM+"/"+Constants.SEARCH;

}
