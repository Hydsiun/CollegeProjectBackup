package v4;

/**
* @ClassName: MultiTalkServer
* @Description: TODO(������һ�仰��������������)
* @author 31415926535x
* @date 2019-05-01����11:17:07
*
*/

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Set;

import com.sun.javafx.collections.MappingChange.Map;
public class MultiTalkServer{
static int clientnum=0; //��̬��Ա��������¼��ǰ�ͻ��ĸ���
static HashMap<Account, Socket> databaseMap = new HashMap<Account, Socket>();


    /**
    * @Title: putAccountIntoDB
    * @Description: TODO ��һ���˻��Լ�����ǰ���׽�����Ϊ��ֵ���浽 hashMap ��
    * @param @param accountString	�˻���Ϣ�����ַ�����ʾ��
    * @param @param socket    ����	�׽��ֶ���
    * @return void    ��������
    * @throws
    */
    
public static void putAccountIntoDB(String accountString, Socket socket) {
	databaseMap.put(Account.conventStringToAccount(accountString), socket);		//����ǰ�û����˻�����Ϣ�������׽��ֶ�Ӧ����
}


    /**
    * @Title: getSocket
    * @Description: TODO Ѱ�ҵ�һ���˻�������׽���
    * @param @param theOtherClientString	�˻��ַ�����ʾ
    * @param @return    ����
    * @return Socket    ��������
    * @throws
    */
    
public static Socket getSocket(String theOtherClientString) {
	return databaseMap.get(Account.conventStringToAccount(theOtherClientString));
}

    /**
    * @Title: getAccount
    * @Description: TODO Ѱ��һ��socket��Ӧ�ĵ�ǰ�û�
    * @param @param theOtherClientSocket
    * @param @return    ����
    * @return Account    ��������
    * @throws
    */
    
public static Account getAccount(Socket theOtherClientSocket) {
	Set<Account> st = databaseMap.keySet();				//��ȡ��ǰ���ݿ����е������û�
	System.out.println(st.size());
	for(Account account: st) {							//������û����׽��ֵ��ڴ����ҵ��û����׽��֣���������û�
		if(databaseMap.get(account).equals(theOtherClientSocket)) {
			return account;
		}
	}
	return null;
}


public static void main(String args[]) throws IOException {
	
	//�����������˵����ݻ����ļ���
	fileSystemOperation.mkdir_("", false);
	
	ServerSocket serverSocket=null;
	boolean listening=true;
	try{
		//����һ��ServerSocket�ڶ˿�4700�����ͻ�����
		serverSocket=new ServerSocket(4700); 			
	}catch(IOException e) {
		System.out.println("Could not listen on port:4700.");
		//������ӡ������Ϣ
		System.exit(-1); //�˳�
	}
	
	while(listening){ //ѭ������
	  //�������ͻ����󣬸��ݵõ���Socket����Ϳͻ��������������̣߳�������֮
	  new ServerThread(serverSocket.accept(),clientnum).start();
	  clientnum++; //���ӿͻ�����
	}
	serverSocket.close(); //�ر�ServerSocket
}
}
