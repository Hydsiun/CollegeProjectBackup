package v4;

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
	
	public Account(String id, String name, boolean userOrServer) {
		this.id = id;
		this.name = name;
		generateOwnDir(userOrServer);
	}
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void show() {
		System.out.println(id + "-" + name);
	}
	
	    
	
	    /* (non-Javadoc)
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
	
	
	    /* 
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
	    * @Description: TODO ��һ���˻�����ת��Ϊһ���ַ�����ʽ
	    * @param     ����
	    * @return String    ��������
	    * @throws
	    */
	    
	public String conventAccountToString() {
		String tmpString = this.id + "-" + this.name;
		return tmpString;
	}
	
	    /**
	    * @Title: conventStringToAccount
	    * @Description: TODO ��һ���ַ�����ʽ����Ϣת����һ���˻�����
	    * @param @param theOtherClientString
	    * @param @return    ����
	    * @return Account    ��������
	    * @throws
	    */
	    
	public static Account conventStringToAccount(String theOtherClientString) {
		int begin0 = 0;			//���ַ�����ԭAccount
		int end0 = theOtherClientString.indexOf('-');
		int begin1 = end0 + 1;
		int end1 = theOtherClientString.length();
		return new Account(theOtherClientString.substring(begin0, end0), theOtherClientString.substring(begin1, end1), false);
	}
	
	
	/*******************v4 ��������**********************/
	
	
	    /**
	    * @Title: generateOwnDir
	    * @Description: TODO Ϊ�û�����һ���ļ���
	    * @param     ����
	    * @return void    ��������
	    * @throws
	    */
	    
	public void generateOwnDir(boolean userOrServer) {
		fileSystemOperation.mkdir_(id, userOrServer);
		if(!userOrServer) {
			System.out.println("\t\t\t<Server>");
		}
		System.out.println();
		System.out.println("User " + id + "'s dir has been mkdired!");
		if(userOrServer) {
			System.out.println("Now you can transfer and recive files in this dir!");
		}
		System.out.println();
	}
	
	

}