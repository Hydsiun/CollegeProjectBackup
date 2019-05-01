
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
	int clientnum; //���汾���̵Ŀͻ�����
	public ServerThread(Socket socket,int num) { //���캯��
	     this.socket1=socket; //��ʼ��socket����
		 clientnum=num+1; //��ʼ��clientnum����
	}
	public void run() { //�߳�����
	    try{
			String line;
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
			
			BufferedReader is2 = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
			PrintWriter os2 = new PrintWriter(socket2.getOutputStream());
			
			
			
			
			//�ӱ�׼�������һ�ַ���
			line = is1.readLine();
			
			//�ڱ�׼����ϴ�ӡ�ӿͻ��˶�����ַ���
			System.out.println("Client1 talk to Client2: " + line);
			
			while(!line.equals("bye")){									//������ַ���Ϊ "bye"����ֹͣѭ��
			   os2.println(line);										//��ͻ���������ַ���
			   os2.flush();												//ˢ���������ʹClient�����յ����ַ���
			   //��ϵͳ��׼����ϴ�ӡ���ַ���
			   System.out.println("Client1 talk to Client2: " + line);
			   
			   line = is2.readLine();									
			   
			   os1.println(line);
			   os1.flush();
			   
			   System.out.println("Client 2 talk to Client1: " + line);
			   
			   
			   line = is1.readLine();
			}//����ѭ��
			os1.close(); //�ر�Socket�����
			is1.close(); //�ر�Socket������
			socket1.close(); //�ر�Socket	
			
			os2.close();
			is2.close();
			socket2.close();
		}catch(Exception e){
			System.out.println("Error:"+e);//������ӡ������Ϣ
		}
	}
}

