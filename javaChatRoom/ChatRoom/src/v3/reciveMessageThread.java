
    /**  
    * @Title: reciveMessageThread.java
    * @Package v3
    * @Description: TODO
    * @author 31415926535x 
	* @email 2509437152wx@gamil.com
	* @blog cnblogs.com/31415926535x
    * @date 2019-05-01����2:10:02
    * @version v3 
    */
    
package v3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
    * @ClassName: reciveMessageThread
    * @Description: TODO ������Ϣ�̣߳��뷢����Ϣ�̲߳��д���ʵ��ͨ�ŵ�������
    * @author 31415926535x
    * @date 2019-05-01����2:10:02
    *
    */

public class reciveMessageThread extends Thread {
	Socket socket = null;

	    /**
	    * @Title: reciveMessageThread
	    * @Description: TODO ������Ϣ�Ĳ����߳�
	    * @param @param messageString	��Ϣ����
	    * @param @param socket    		�ȴ�������Ϣһ�����׽��֣����������˵��׽��֣��ͻ��˵ȴ�������ת������Ϣ��
	    * @throws
	    */
	    
	public reciveMessageThread(Socket socket) {
		// TODO Auto-generated constructor stub
		this.socket = socket;
	}
	public void run() {
		try {
			//��Socket����õ�����������������Ӧ��BufferedReader����
			BufferedReader is=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			String readlineString = is.readLine();
			
			while(!readlineString.equals("bye")) {
				System.out.println("He said: " + is.readLine());
			}
			
			is.close();
			socket.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error: " + e);
		}
	}
}
