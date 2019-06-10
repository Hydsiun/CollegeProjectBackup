package v5;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @ClassName: Account
 * @Description: TODO ����һ���˻������к��е���Ϣ���ǳơ�id�������ļ�Ŀ¼ϵͳ
 * @author 31415926535x
 * @date 2019-05-01����11:19:13
 *
 */

public class Account {
	private String id = null;		//�˻���id,��ΪΨһ��ʶ�룬���ڵ��û�֮���ͨ��
	private String name = null;		//�˻����ǳ�
	
	
	    /**
	     * ����һ���µ�ʵ�� Account.
	     *
	     * @param id
	     * @param name
	     * @param type 				���������û�����ʱ����Account��������ͣ���Ӧ2��1��0
	     */
	    
	public Account(String id, String name, int type) {
		this.id = id;
		this.name = name;
		generateOwnDir(type);
	}
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void show() {
		System.out.println((new Gson()).toJson(this));
	}
	
	    
	
	    /** (non-Javadoc)
	    * 
	    * 
		* @Title: hashCode
	    * @Description: TODO ������Ϊhashmap�ļ�ʱҪ���� hashcode() �� equals()
	    * @param     ����
		* @return	int
	    * @throws
	    */
	    
	@Override
	public int hashCode() {
		final int prime = 1007;
		int ret = 1;
		ret = prime * ret + id.hashCode();
		ret = prime * ret + name.hashCode();
		return ret;
	}
	
	
    /**
    * 
    * 
	* @Title: equals
    * @Description: TODO ������Ϊhashmap�ļ�ʱҪ���� hashcode() �� equals()
    * @param     ����
	* @return	true or flase
    * @throws
    */
	    
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		Account otherAccount = (Account)obj;
		if(id == null) {
			if(otherAccount.id != null)
				return false;
		}
		else if(!id.equals(otherAccount.id))
			return false;
		
		if(name == null) {
			if(otherAccount.name != null)
				return false;
		}
		else if(!name.equals(otherAccount.name))
			return false;
		return true;
	}
	
	    /**
	    * @Title: conventAccountToString
	    * @Description: TODO ��һ���˻�����ת��Ϊһ��json�ַ�����ʽ
	    * @param     ����
	    * @return String    ��������
	    * @throws
	    */
	    
	public String conventAccountToString() {
		return (new Gson()).toJson(this);
	}
	
	    /**
	    * @Title: conventStringToAccount
	    * @Description: TODO ��һ��json�ַ�����ʽ����Ϣת����һ���˻�����
	    * @param @param theOtherClientString
	    * @param @return    ����
	    * @return Account    ��������
	    * @throws
	    */
	    
	public static Account conventStringToAccount(String theOtherClientString) {
		System.out.println("conventStringToAccount");
		JsonObject jsonObject = (JsonObject) new JsonParser().parse(theOtherClientString);
		System.out.println(theOtherClientString);
		System.out.println(jsonObject.get("id").getAsString() + " " + jsonObject.get("name").getAsString());
		return (new Account(jsonObject.get("id").getAsString(), jsonObject.get("name").getAsString(), 2));
	}
	
	
	/*******************v4 ��������**********************/
	
	
	    /**
	    * @Title: generateOwnDir
	    * @Description: TODO Ϊ�û�����һ���ļ���
	    * @param     ����
	    * @return void    ��������
	    * @throws
	    */
	    
	public void generateOwnDir(int type) {
		if(type == 0) {
			return;
		}
		fileSystemOperation.mkdir_(id, type);
		if(type == 2) {
			System.out.println("\t\t\t<Server>");
		}
		System.out.println();
		System.out.println("User " + id + "'s dir has been mkdired!");
		if(type == 1) {
			System.out.println("Now you can transfer and recive files in this dir!");
		}
		System.out.println();
	}
	
	

}