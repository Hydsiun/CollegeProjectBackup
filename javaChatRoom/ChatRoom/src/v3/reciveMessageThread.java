
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
	private Thread blinker;
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
	
	public void start() {
		blinker = new Thread(this);
		blinker.start();
	}
	
	public void stopThisThread() {
		blinker = null;
	}
	
	public void run() {
		Thread thisThread = Thread.currentThread();
		
		try {
			//��Socket����õ�����������������Ӧ��BufferedReader����
			BufferedReader is=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			String readlineString = is.readLine();
			
			while(blinker == thisThread) {
				System.out.println("He said: " + readlineString);
//				if(readlineString.equals("bye")) {
//					break;
//				}
				readlineString = is.readLine();
			}
			
//			is.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error: " + e);
		}
	}
}
