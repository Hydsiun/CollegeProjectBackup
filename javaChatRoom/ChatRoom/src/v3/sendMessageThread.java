
    /**  
    * @Title: sendMessageThread.java
    * @Package v3
    * @Description: TODO
    * @author 31415926535x 
	* @email 2509437152wx@gamil.com
	* @blog cnblogs.com/31415926535x
    * @date 2019-05-01����2:09:36
    * @version v3 
    */
    
package v3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
    * @ClassName: sendMessageThread
    * @Description: TODO ������Ϣ�̣߳��������Ϣ�̲߳��д���ʵ��ͨ�ŵ�������
    * @author 31415926535x
    * @date 2019-05-01����2:09:36
    *
    */

public class sendMessageThread extends Thread {
	Socket socket = null;
	
	/**
	* @Title: sendMessageThread
	* @Description: TODO ���췽������¼��ǰ�ͻ����������ͨ�ŵ��׽��֣����������׽��֣�
	* @param     ����
	* @throws
	*/

	public sendMessageThread(Socket socket) {
		// TODO Auto-generated constructor stub
		this.socket = socket;
	}
	
	    /* (non-Javadoc)
	    * 
	    * 
		* @Title: run
	    * @Description: TODO �߳� run() �������壬ʵ�ִӿͻ��˵õ�һ����Ϣ�����͵��������Ĺ���
	    * @param     ����
		* @return	
	    * @throws
	    */
	    
	public void run() {
		try {
			//��ϵͳ��׼�����豸����BufferedReader����
			BufferedReader sin=new BufferedReader(new InputStreamReader(System.in));
			//��Socket����õ��������������PrintWriter����
			PrintWriter os=new PrintWriter(socket.getOutputStream());
			
			String readlineString = sin.readLine();
			
			while(!readlineString.equals("bye")) {
				os.println(readlineString);
				os.flush();
				System.out.println("You said: " + readlineString);
				readlineString = sin.readLine();
			}			
			
			os.close();
			sin.close();
			socket.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error: " + e);
		}
	}
}
