
    /**  
    * @Title: ServerThread.java
    * @Package v3
    * @Description: TODO
    * @author 31415926535x 
	* @email 2509437152wx@gamil.com
	* @blog cnblogs.com/31415926535x
    * @date 2019-05-01����11:16:35
    * @version v3 
    */
    
package v3;


    /**
    * @ClassName: ServerThread
    * @Description: TODO(������һ�仰��������������)
    * @author 31415926535x
    * @date 2019-05-01����11:16:35
    *
    */

import java.io.*;
import java.net.*;
public class ServerThread extends Thread{
	Socket socket1 = null; 		//�����뱾�߳���ص����ͻ���Socket����
	Socket socket2 = null; 		//�������ͻ�����֮ͨ�ŵĿͻ��˵�Socket����
	int clientnum; 				//���汾���̵Ŀͻ�����
	public ServerThread(Socket socket,int num) { //���캯��
	     this.socket1 = socket; 	//��ʼ��socket����
		 clientnum = num+1; 		//��ʼ��clientnum����
	}
	public void run() { //�߳�����
	    try{
			//�ɿͻ���1��Socket����õ�����������������Ӧ��BufferedReader����
			BufferedReader is1= new BufferedReader(new InputStreamReader(socket1.getInputStream()));
			//�ɿͻ���1Socket����õ��������������PrintWriter����
			PrintWriter os1 = new PrintWriter(socket1.getOutputStream());
			
			
			
			String accountString = is1.readLine();						//���뵱ǰ�ͻ��˵���Ϣ

			MultiTalkServer.putAccountIntoDB(accountString, socket1);	//������Ϣ���������ݿ�
			
			os1.println("You are now logging in!");
			os1.flush();
			os1.println("Input the id that you wanna talk to...");
			os1.flush();
			
			String theOtherClientString = is1.readLine();				//�õ���ǰ�ͻ�����Ҫͨ�ŵĿͻ���id
			
			socket2 = MultiTalkServer.getSocket(theOtherClientString);	//���Ҫͨ�ŵĿͻ��˵��׽���
			
			
			//��������������̣߳�����ʵ�ֿͻ���A�ķ�������չ���
			sendAndReciveOfServerThread clientASendMessageToClientB = new sendAndReciveOfServerThread(socket1, socket2);
			sendAndReciveOfServerThread clientAReciveMessageFromClientB = new sendAndReciveOfServerThread(socket2, socket1);
			
			clientASendMessageToClientB.start();	
			clientAReciveMessageFromClientB.start();
			
			while(clientAReciveMessageFromClientB.isAlive() && clientASendMessageToClientB.isAlive()) {
				if(!clientAReciveMessageFromClientB.isAlive() || !clientASendMessageToClientB.isAlive()) {
					os1.close();
					is1.close();
					socket1.close();
					socket2.close();
				}
			}
			
		}catch(Exception e){
			System.out.println("Error:"+e);//������ӡ������Ϣ
		}
	}
}

