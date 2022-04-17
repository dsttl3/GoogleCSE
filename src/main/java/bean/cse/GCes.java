package bean.cse;

import java.util.List;

public class GCes {

    private Cursor cursor;

    private List<Result> results;

    /**
     *
     * @return 返回结果列表
     */
    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    /**
     *
     * @return 搜索结果信息
     */
    public Cursor getCursor() {
        return cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }
}
