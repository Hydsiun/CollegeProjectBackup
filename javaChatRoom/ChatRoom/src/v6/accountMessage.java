package v6;

import java.util.List;

import com.google.gson.Gson;

public class accountMessage {
	@SuppressWarnings("unused")
	private final String type = "account";
	private List<Account> accounts;
	public accountMessage(List<Account> accounts) {
		// TODO Auto-generated constructor stub
		this.accounts = accounts;
	}
	
	    
	
	    /**
	    * @Title: getAccountMessageJson
	    * @Description: TODO ��һ��accountMessageʵ���õ�һ��json
	    * @param @return    ����
	    * @return String    ��������
	    * @throws
	    */
	    
	public String getAccountMessageJson() {
		return (new Gson()).toJson(this);
	}
	
	    /**
	    * @Title: getAccountsFromJosn
	    * @Description: TODO ��һ��accountMessage��json �л�ȡ���е�accounts
	    * @param @param json
	    * @param @return    ����
	    * @return List<Account>    ��������
	    * @throws
	    */
	    
	public static List<Account> getAccountsFromJosn(String json) {
		return (new Gson().fromJson(json, accountMessage.class)).accounts;
	}
}
