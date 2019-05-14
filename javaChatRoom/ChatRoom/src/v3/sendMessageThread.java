
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
	private volatile Thread blinker;
	private boolean flag = true; 
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
	
	public boolean getFlag() {
		return flag;
	}
	public void start() {
		blinker = new Thread(this);
		blinker.start();
	}
	
	
	public void stopThisThread() {
		blinker = null;
		flag = false;
	}
	    /**
	    * 
	    * 
		* @Title: run
	    * @Description: TODO �߳� run() �������壬ʵ�ִӿͻ��˵õ�һ����Ϣ�����͵��������Ĺ���
	    * @param     ����
		* @return	
	    * @throws
	    */
	    
	public void run() {
		Thread thisThread = Thread.currentThread();

		try {
			
			//��ϵͳ��׼�����豸����BufferedReader����
			BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));
			//��Socket����õ��������������PrintWriter����
			PrintWriter os = new PrintWriter(socket.getOutputStream());
			
			String readlineString = sin.readLine();
			
			while(blinker == thisThread) {
				os.println(readlineString);
				os.flush();
				System.out.println("You said: " + readlineString);
				if(readlineString.equals("bye")) {
					System.out.println("send Thread stop");
					stopThisThread();
					break;
				}
				readlineString = sin.readLine();
			}
			System.out.println("send thread stopped");
//			os.close();
//			sin.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error: --" + e);
		}
	}
}
