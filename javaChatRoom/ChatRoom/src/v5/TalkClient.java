package v5;

/**
* @ClassName: TalkClient
* @Description: TODO(������һ�仰��������������)
* @author 31415926535x
* @date 2019��5��1��
*
*/

import java.io.*;
import java.net.*;

import com.google.gson.Gson;


public class TalkClient {
	public static Account account;							//Ϊ�˽�����Ϣ�߳��ܹ���ȡ����ǰ�û���id
	public static Account theOtherAccount;
	public static void main(String args[]) {
//		account = new getRandomAccountForTest().getARandomAccountForTest();
		account = getRandomAccountForTest.getARandomAccountForTest();
		account.show();
		
		try{
			//�򱾻���4700�˿ڷ����ͻ�����
			Socket socket=new Socket("127.0.0.1", 4700);
			
			//��ϵͳ��׼�����豸����BufferedReader����
			BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));
//			��Socket����õ��������������PrintWriter����
			PrintWriter os = new PrintWriter(socket.getOutputStream());
//			��Socket����õ�����������������Ӧ��BufferedReader����
			BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			
			
			System.out.println((new Message(account.getId().toString(), account.getName(), "", "", "Send own account's id").getJsonOfMessage()));

			//��Ϊ���е��ַ������涨��UTF�����뷢�Ͳ��������Դ˴���writeUTF()
			dos.writeUTF(account.conventAccountToString());				//������������Լ����˻���Ϣ����¼�������б����ݿ�
			dos.flush();
/* 
 * ����Ϊ��������ļ����ݿ�stringתbyte[]���ݶ�ʧ�����ĳ���
//			os.println((new Message(account.getId().toString(), account.getName(), "", "", "Send own account's id").getJsonOfMessage()));	
//			os.println(account.conventAccountToString());				//������������Լ����˻���Ϣ����¼�������б����ݿ�
//			os.flush();
//			dos.write(account.conventAccountToString().getBytes("UTF8"));
//			dos.flush();
*/	
			
			
			//G:\Backup\clientSendIdToServer.java
			//�����շ���Ϣ�����̣߳�ʵ���շ���Ϣ��������
			reciveMessageThread recive = new reciveMessageThread(socket);
			recive.start();
			boolean clientIslogin = true;
			while(clientIslogin) {
	
				String theOhterClientString;				//��¼��ǰ�ͻ�����Ҫ����ͨ�ŵ���һ���ͻ�������
				theOhterClientString = sin.readLine();
				
				if(theOhterClientString.equals("exit")) {
					break;
				}
				
				
				System.out.println(theOhterClientString);										//��ȡ��Ҫ��֮������û���Account��json�����ַ���
				theOtherAccount = (new Gson()).fromJson(theOhterClientString, Account.class);	//��json�ַ����������Է���ʵ��
				System.out.println(theOtherAccount.getId() + " " + theOtherAccount.getName());	//��ʾ�Է���id������
				
				
//				theOhterClientString = (new Gson()).toJson(new Message(account.getId().toString(), account.getName(), theOtherAccount.getId(), theOtherAccount.getName(), "Link the other client"));
				System.out.println("send the other client json: " + theOhterClientString);
				
//				os.println(theOhterClientString);				//�������������֮ͨ�ŵ���һ�ͻ��ˣ��������ʽ��һ���ͻ���Account��json
//				os.flush();
				
				//ͬ��ʹ��UTF�����ַ���
				dos.writeUTF(theOhterClientString);												//�������������֮ͨ�ŵ���һ�ͻ��ˣ��������ʽ��һ���ͻ���Account��json
				dos.flush();
				
				
				sendMessageThread send = new sendMessageThread(socket);		//����һ��������Ϣ���̣߳���������һ���ͻ��˷�����Ϣ
				send.start();
	
				
				Thread mainThread = Thread.currentThread();					//�������������Ϣ�̵߳�flagʹmain�߳���ִͣ�У�����֤��ͨ�ŵ����ʱ����һֱΪ�ӷ���Ϣ
				while(send.getFlag()) {
					mainThread.yield();
				}
	
				System.out.println("You have stopped communiating the other person");
			}
			
			//��ǰ�û�����
			recive.stopThisThread();		//ֹͣ������Ϣ
			os.close();						//�رո�����
			sin.close();
			is.close();
			dos.close();
	//		System.out.println(23333);
			socket.close();					//�ر��׽���
			
			
		}catch(Exception e) {
			System.out.println("Error in TalkClient: "+e); //�������ӡ������Ϣ
	    }
	}
}