package es.bean.jsonbean;

import java.util.List;

/**
 * Created by kevinyin on 2017/9/11.
 */
public class EsHits {
    private int total;
    private double max_score;
    private List<EsItemHits> hits;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public double getMax_score() {
        return max_score;
    }

    public void setMax_score(double max_score) {
        this.max_score = max_score;
    }

    public List<EsItemHits> getHits() {
        return hits;
    }

    public void setHits(List<EsItemHits> hits) {
        this.hits = hits;
    }
}
