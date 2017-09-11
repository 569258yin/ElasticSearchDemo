package es.jsonbean;

/**
 * Created by kevinyin on 2017/9/11.
 */
public class EsSearchResult {

    private int took;
    private boolean timed_out;
    private EsShards _shards;
    private EsHits hits;

    public int getTook() {
        return took;
    }

    public void setTook(int took) {
        this.took = took;
    }

    public boolean isTimed_out() {
        return timed_out;
    }

    public void setTimed_out(boolean timed_out) {
        this.timed_out = timed_out;
    }

    public EsHits getHits() {
        return hits;
    }

    public void setHits(EsHits hits) {
        this.hits = hits;
    }

    public EsShards get_shards() {
        return _shards;
    }

    public void set_shards(EsShards _shards) {
        this._shards = _shards;
    }
}
