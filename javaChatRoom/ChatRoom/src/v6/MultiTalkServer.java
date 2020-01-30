package v6;

/**
* @ClassName: MultiTalkServer
* @Description: TODO(������һ�仰��������������)
* @author 31415926535x
* @date 2019-05-01����11:17:07
*
*/

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class MultiTalkServer{
	static int clientnum=0; //��̬��Ա��������¼��ǰ�ͻ��ĸ���
	static HashMap<Account, Socket> databaseMap = new HashMap<Account, Socket>();
	static List<Account> accounts = new ArrayList<Account>();
	static List<Socket> sockets = new ArrayList<Socket>();
	    /**
	    * @Title: putAccountIntoDB
	    * @Description: TODO ��һ���˻��Լ�����ǰ���׽�����Ϊ��ֵ���浽 hashMap ��
	    * @param @param accountString	�˻���Ϣ�����ַ�����ʾ��
	    * @param @param socket    ����	�׽��ֶ���
	    * @return void    ��������
	    * @throws
	    */
	    
	public static void putAccountIntoDB(Account account, Socket socket) {
		databaseMap.put(account, socket);		//����ǰ�û����˻�����Ϣ�������׽��ֶ�Ӧ����
		saveAccount(account);
		saveSocket(socket);
	}
	
	
	    /**
	    * @Title: getSocket
	    * @Description: TODO Ѱ�ҵ�һ���˻�������׽���
	    * @param @param theOtherClientString	�˻��ַ�����ʾ
	    * @param @return    ����
	    * @return Socket    ��������
	    * @throws
	    */
	    
	public static Socket getSocket(Account account) {
		return databaseMap.get(account);
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
	
	
	    /**
	    * @Title: saveAccount
	    * @Description: TODO ����ÿһ�����ߵ��û�
	    * @param @param account    ����
	    * @return void    ��������
	    * @throws
	    */
	    
	public static void saveAccount(Account account) {
		accounts.add(account);
	}
	
	    /**
	    * @Title: saveSocket
	    * @Description: TODO ����ÿһ�����ߵ��û�
	    * @param @param socket    ����
	    * @return void    ��������
	    * @throws
	    */
	    
	public static void saveSocket(Socket socket) {
		sockets.add(socket);
	}
	
	    /**
	    * @Title: deleteAccount
	    * @Description: TODO ɾ��ĳһ�������û�
	    * @param @param account    ����
	    * @return void    ��������
	    * @throws
	    */
	    
	public void deleteAccount(Account account) {
		for(int i = 0; i <= accounts.size() - 1; ++i) {
			if(accounts.get(i).equals(account)) {
				accounts.remove(i);
				break;
			}
		}
	}
	
	    /**
	    * @Title: deleteSocket
	    * @Description: TODO ɾ��ĳһ�������û�
	    * @param @param socket    ����
	    * @return void    ��������
	    * @throws
	    */
	    
	public void deleteSocket(Socket socket) {
		for(int i = 0; i <= sockets.size() - 1; ++i) {
			if(sockets.get(i).equals(socket)) {
				sockets.remove(i);
				break;
			}
		}
	}
	
	    /**
	    * @Title: getAccounts
	    * @Description: TODO �õ����������û��б�
	    * @param @return    ����
	    * @return List<Account>    ��������
	    * @throws
	    */
	    
	public List<Account> getAccounts() {
		return this.accounts;
	}
	
	public static void main(String args[]) throws IOException {
		
		//�����������˵����ݻ����ļ���
		fileSystemOperation.mkdir_("", 2);
		
		ServerSocket serverSocket=null;
		boolean listening = true;
		try{
			//����һ��ServerSocket�ڶ˿�4700�����ͻ�����
			serverSocket = new ServerSocket(4700); 			
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
