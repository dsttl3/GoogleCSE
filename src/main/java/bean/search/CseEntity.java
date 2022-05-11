package bean.search;

/**
 * @author dsttl3
 */
public class CseEntity {

	private String cse_tok;
	private String cselibv;

	/**
	 * token
	 * @return
	 */
	public String getCse_tok() {
		return cse_tok;
	}

	public void setCse_tok(String cse_tok) {
		this.cse_tok = cse_tok;
	}

	/**
	 * 版本号
	 * @return
	 */
	public String getCselibv() {
		return cselibv;
	}

	public void setCselibv(String cselibv) {
		this.cselibv = cselibv;
	}

}
