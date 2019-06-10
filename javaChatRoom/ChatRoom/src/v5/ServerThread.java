package v5;

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
private Socket socket1 = null; 		//�����뱾�߳���ص����ͻ���Socket����
private Socket socket2 = null; 		//�������ͻ�����֮ͨ�ŵĿͻ��˵�Socket����
private Account accountA;
private Account accountB;
int clientnum; 				//���汾���̵Ŀͻ�����
public ServerThread(Socket socket,int num) { //���캯��
     this.socket1 = socket; 	//��ʼ��socket����
	 clientnum = num+1; 		//��ʼ��clientnum����
}
public void run() { //�߳�����
    try{
		//�ɿͻ���1��Socket����õ�����������������Ӧ��BufferedReader����
//		BufferedReader is1= new BufferedReader(new InputStreamReader(socket1.getInputStream()));
		//�ɿͻ���1Socket����õ��������������PrintWriter����
//		PrintWriter os1 = new PrintWriter(socket1.getOutputStream());
		
		DataOutputStream dos = new DataOutputStream(socket1.getOutputStream());
		DataInputStream dis = new DataInputStream(socket1.getInputStream());
		
//		String accountString = is1.readLine();						//���뵱ǰ�ͻ��˵���Ϣ
		String accountString;
//		byte[] accountByte = new byte[4096];
//		int count = dis.read(accountByte);
		accountString = dis.readUTF();
//		accountString = new String(accountByte, "UTF8");
		System.out.println(accountString);
		accountA = Account.conventStringToAccount(accountString);
		MultiTalkServer.putAccountIntoDB(accountA, socket1);	//������Ϣ���������ݿ�
		
//		os1.println((new Message("", "Server", accountA.getId(), accountA.getName(), "You logged in!")).getJsonOfMessage());
////		os1.println("Server: You logged in!");						//�����û����ӳɹ�������
//		os1.flush();
		
//		dos.writeUTF((new Message("", "Server", accountA.getId(), accountA.getName(), "You logged in!")).getJsonOfMessage());
//		dos.flush();
//		dos.write((new Message("", "Server", accountA.getId(), accountA.getName(), "You logged in!")).getJsonOfMessage().getBytes("UTF8"));
		dos.writeUTF((new Message("", "Server", accountA.getId(), accountA.getName(), "You logged in!")).getJsonOfMessage());
		dos.flush();
		
		boolean clientIsLogin = true;
		while(clientIsLogin) {

/*
 *	����Ϊ���ֳ��� 
//			os1.println("\n\t\t\t<Tips>: \n\nServer: Input the id that you wanna talk to..\nOr \"exit\" if you wanna to exit");
//			os1.println((new Message("", "Server", accountA.getId(), accountA.getName(), "\n\t\t\t<Tips>: \n\nServer: Input the id that you wanna talk to..\nOr \"exit\" if you wanna to exit")).getJsonOfMessage());
//			os1.flush();
			
//			dos.writeUTF((new Message("", "Server", accountA.getId(), accountA.getName(), "\n\t\t\t<Tips>: \n\nServer: Input the id that you wanna talk to..\nOr \"exit\" if you wanna to exit")).getJsonOfMessage());;
//			dos.flush();
//			dos.write((new Message("", "Server", accountA.getId(), accountA.getName(), "\n\t\t\t<Tips>: \n\nServer: Input the id that you wanna talk to..\nOr \"exit\" if you wanna to exit")).getJsonOfMessage().getBytes("UTF8"));
*/
			//��ʾ�û�����Ҫ��֮ͨ�ŶԷ����ǳƺ�id
			dos.writeUTF((new Message("", "Server", accountA.getId(), accountA.getName(), "\n\t\t\t<Tips>: \n\nServer: Input the id that you wanna talk to..\nOr \"exit\" if you wanna to exit")).getJsonOfMessage());
			dos.flush();
			

//			String theOtherClientString = is1.readLine();
//			String theOtherClientString = dis.readUTF();
//			byte[] theOtherClientByte = accountByte;
//			dis.read(theOtherClientByte);
			
			
			//��ȡ��һ���ͻ��˵�json���������˳���Ϣexit
			String theOtherClientString = dis.readUTF();
//			String theOtherClientString = new String(theOtherClientByte, "UTF8");
			System.out.println("the other client's json is: " + theOtherClientString);
//			if(((JsonObject)new JsonParser().parse(theOtherClientString)).get("message").getAsString().equals("exit")) {
//				break;
//			}
			//���û�ѡ���뿪ʱ��ֹ������ز���
			if(theOtherClientString.equals("exit")) {
				break;
			}
			
			accountB = Account.conventStringToAccount(theOtherClientString);
			
			//�����ǳƻ�ȡ�Է����׽���
			socket2 = MultiTalkServer.getSocket(accountB);
			
			//��������Է�����һ������Ϣ���̣߳����ڽ����û��յ�����Ϣ����ת������һ�û�
			sendAndReciveOfServerThread clientASendMessageToClientB = new sendAndReciveOfServerThread(socket1, socket2, accountA, accountB);
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

