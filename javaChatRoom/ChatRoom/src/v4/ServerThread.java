package v4;

/**
* @ClassName: ServerThread
* @Description: TODO(������һ�仰��������������)
* @author 31415926535x
* @date 2019-05-01����11:16:35
*
*/

import java.io.*;
import java.net.*;
import java.util.Date;
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
		os1.println("Server: You logged in!");						//�����û����ӳɹ�������
		os1.flush();
		
		boolean clientIsLogin = true;
		while(clientIsLogin) {
			
			//��ʾ�û�����Ҫ��֮ͨ�ŶԷ����ǳƺ�id
			os1.println("\n\t\t\t<Tips>: \n\nServer: Input the id that you wanna talk to..\nOr \"exit\" if you wanna to exit");
			os1.flush();

			//���û�ѡ���뿪ʱ��ֹ������ز���
			String theOtherClientString = is1.readLine();
			System.out.println(theOtherClientString + "------");
			if(theOtherClientString.equals("exit")) {
				break;
			}
			
			//�����ǳƻ�ȡ�Է����׽���
			socket2 = MultiTalkServer.getSocket(theOtherClientString);
			
			//��������Է�����һ������Ϣ���̣߳����ڽ����û��յ�����Ϣ����ת������һ�û�
			sendAndReciveOfServerThread clientASendMessageToClientB = new sendAndReciveOfServerThread(socket1, socket2);
			clientASendMessageToClientB.start();
			
			//��ͻ���һ������ʱ���߳���ͣ��ִֻ�з���Ϣ�߳�
			Thread thisThread = Thread.currentThread();
			while(clientASendMessageToClientB.getFlag()) {
				thisThread.yield();
			}
			System.out.println("client1 stoped");
		}
		
	}catch(Exception e){
		System.out.println("Error:" + e);//������ӡ������Ϣ
	}
}
}

