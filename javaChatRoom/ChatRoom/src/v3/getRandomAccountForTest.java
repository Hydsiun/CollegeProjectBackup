
    /**  
    * @Title: getRandomAccountForTest.java
    * @Package v3
    * @Description: TODO
    * @author 31415926535x 
	* @email 2509437152wx@gamil.com
	* @blog cnblogs.com/31415926535x
    * @date 2019-05-01����11:17:36
    * @version v3 
    */
    
package v3;

import java.util.Random;


/**
    * @ClassName: getRandomAccountForTest
    * @Description: TODO(������һ�仰��������������)
    * @author 31415926535x
    * @date 2019-05-01����11:17:36
    *
    */

public class getRandomAccountForTest {
	
	    /**
	    * @Title: getARandomAccountForTest
	    * @Description: TODO ����һ������˻�����֮��Ĳ���
	    * @param @return    ����
	    * @return Account    ��������
	    * @throws
	    */
	    
	public Account getARandomAccountForTest() {
		Random random = new Random();
		int id = random.nextInt(1000);
		int name = random.nextInt(1000);
		return new Account(Integer.toString(id), Integer.toString(name));
		//java��ʹ��.toString() �᷵�� "����@�ַ���" ��ʽ��ֵ
		//return new Account((new Random()).toString(), (new Random()).toString());
	}
}