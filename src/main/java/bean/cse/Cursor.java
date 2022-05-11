package bean.cse;

/**
 * @author dsttl3
 */
public class Cursor {

    private String searchResultTime;
    private String resultCount;

    /**
     *
     * @return 搜索耗时
     */
    public String getSearchResultTime() {
        return searchResultTime;
    }

    public void setSearchResultTime(String searchResultTime) {
        this.searchResultTime = searchResultTime;
    }

    /**
     *
     * @return 结果统计
     */
    public String getResultCount() {
        return resultCount;
    }

    public void setResultCount(String resultCount) {
        this.resultCount = resultCount;
    }
}
