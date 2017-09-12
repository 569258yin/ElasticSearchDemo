package es.bean.es;

import es.utils.Constants;

/**
 * Created by kevinyin on 2017/9/12.
 */
public class EsSearchRange {

    private Integer size;
    private Integer from;

    private final static EsSearchRange DEFAULT_RANG = factoryPageSize(0,Constants.DEFAULT_SIZE);

    public static EsSearchRange getDefaultRang(){ return DEFAULT_RANG; }

    public static EsSearchRange factoryPageSize(int from,int size) {
        if (from < 0) {
            from = 0;
        }
        if (size < 0){
            size = Constants.DEFAULT_SIZE;
        }
        EsSearchRange range = new EsSearchRange();
        range.setFrom(from);
        range.setSize(size);
        return range;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }
}
