
    /**  
    * @Title: TalkClient.java
    * @Package v3
    * @Description: TODO
    * @author 31415926535x 
	* @email 2509437152wx@gamil.com
	* @blog cnblogs.com/31415926535x
    * @date 2019-05-01����11:14:43
    * @version v3 
    */
    
package v3;


    /**
    * @ClassName: TalkClient
    * @Description: TODO(������һ�仰��������������)
    * @author 31415926535x
    * @date 2019��5��1��
    *
    */

import java.io.*;
import java.net.*;


public class TalkClient {
	public static void main(String args[]) {
		Account account = new getRandomAccountForTest().getARandomAccountForTest();
		account.show();
		
		
		try{
			//�򱾻���4700�˿ڷ����ͻ�����
			Socket socket=new Socket("127.0.0.1", 4700);
			
			//��ϵͳ��׼�����豸����BufferedReader����
			BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));
			//��Socket����õ��������������PrintWriter����
			PrintWriter os = new PrintWriter(socket.getOutputStream());
			//��Socket����õ�����������������Ӧ��BufferedReader����
			BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			
			os.println(account.conventAccountToString());	//������������Լ����˻���Ϣ����¼�������б����ݿ�
			os.flush();
			
			System.out.println("Server: " + is.readLine());	//�����½�ɹ���ʾ��Ϣ
			
			
			//�����շ���Ϣ�����̣߳�ʵ���շ���Ϣ��������
			reciveMessageThread recive = new reciveMessageThread(socket);
			recive.start();
			
			boolean clientIslogin = true;
			while(clientIslogin) {
				System.out.println(23333);
				
//				System.out.println("Server: " + is.readLine());	//�����Ҫ��֮ͨ�ŵĿͻ��˵���ʾ��Ϣ

				String theOhterClientString;					//��¼��ǰ�ͻ�����Ҫ����ͨ�ŵ���һ���ͻ�������
				
				theOhterClientString = sin.readLine();
				
//				System.out.println(sin.readLine());
//				theOhterClientString = sin.readLine();
//				while(theOhterClientString == null) {
//					theOhterClientString = sin.readLine();
//				}
				
				
				System.out.println("bbbbbbbbbbb" + theOhterClientString);
				os.println(theOhterClientString);				//�������������֮ͨ�ŵ���һ�ͻ���
				os.flush();
				
//				clientSendIdToServer client = new clientSendIdToServer(socket);
//				client.start();
//				System.out.println(233);
//				while(client.isAlive()) {
//					recive.yield();
//				}
				
				sendMessageThread send = new sendMessageThread(socket);
				send.start();

//				if(theOhterClientString.equals("exit")) {
//					send.stopThisThread();
//					break;
//				}
				
//				send.start();
				
				Thread mainThread = Thread.currentThread();
				while(send.getFlag()) {
					mainThread.yield();
				}

				System.out.println("once talk stopped");
			}
			
			
			recive.stopThisThread();
			os.close();
			is.close();
			sin.close();
			System.out.println(23333);
			socket.close();
			
			
			
			
		}catch(Exception e) {
			System.out.println("Error"+e); //�������ӡ������Ϣ
	    }
	}
}