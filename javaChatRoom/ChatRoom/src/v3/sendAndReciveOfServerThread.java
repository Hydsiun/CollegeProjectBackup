
    /**  
    * @Title: sendAndReciveOfServerThread.java
    * @Package v3
    * @Description: TODO
    * @author 31415926535x 
	* @email 2509437152wx@gamil.com
	* @blog cnblogs.com/31415926535x
    * @date 2019-05-01����8:59:20
    * @version v3 
    */
    
package v3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
    * @ClassName: sendAndReciveOfServerThread
    * @Description: TODO ����������ɵĹ����� �տͻ���A��Ϣ�ٷ��͵��ͻ���B�Ĺ��ܣ���ת������
    * @author 31415926535x
    * @date 2019-05-01����8:59:20
    *
    */

public class sendAndReciveOfServerThread extends Thread {
	private Socket clientASocket = null;
	private Socket clientBSocket = null;
	private volatile Thread blinker;
	private boolean flag = true;
	/**
	* @Title: sendAndReciveOfServerThread
	* @Description: TODO(������һ�仰�����������������)
	* @param     ����
	* @throws
	*/

	/**
	* @Title: sendAndReciveOfServerThread
	* @Description: TODO ���췽������¼�¿ͻ���A��B���׽���
	* @param @param clientASocket	A���׽���
	* @param @param clientBSocket   B���׽���
	* @throws
	*/
	    
	public sendAndReciveOfServerThread(Socket clientASocket, Socket clientBSocket) {
		// TODO Auto-generated constructor stub
		this.clientASocket = clientASocket;
		this.clientBSocket = clientBSocket;
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
	    * @Description: TODO ʵ�ַ������ӿͻ���A��������Ϣ��ת�����ͻ���B�Ĺ��ܣ��Կͻ���AΪ���壬��ͨ�ſɿ��� A->������->B �Լ� B->������->A ���������ͨ�ţ����Է������˿������߳̾Ϳ�����
	    * @param     ����
	    */
	    
	public void run() {
		Thread thisThread = this.currentThread();
		
		try {
			
			BufferedReader is1 = new BufferedReader(new InputStreamReader(clientASocket.getInputStream()));		//���տͻ���A������Ϣ
			PrintWriter os2 = new PrintWriter(clientBSocket.getOutputStream());									//�����յ���Ϣת����B
			
//			BufferedReader is2 = new BufferedReader(new InputStreamReader(clientBSocket.getInputStream()));
//			PrintWriter os1 = new PrintWriter(clientASocket.getOutputStream());
			
			String readlineString = is1.readLine();
			while(blinker == thisThread) {
				os2.println("He said: " + readlineString);
				os2.flush();
				System.out.println("Client1 talk ot Client2: " + readlineString);
				if(readlineString.equals("bye")) {
					System.out.println("stoppppppppppppp");
					stopThisThread();
					break;
				}
				readlineString = is1.readLine();
			}
			
//��Ϊ���رջᵼ���׽��ֵĹرգ��������ﲻ��
//			is1.close();
//			os2.close();
//			clientASocket.close();
//			clientBSocket.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Erroe: " + e);
		}	
	}
}
