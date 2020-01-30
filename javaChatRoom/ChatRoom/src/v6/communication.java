package v6;

import java.io.DataOutputStream;
import java.net.Socket;

public class communication {
	
	
	    /**
	    * @Title: sendMessage
	    * @Description: TODO �������û��˵ķ�����Ϣ�࣬��������Ϣ�ķ���
	    * @param @param sendMessage
	    * @param @param socket
	    * @param @param accountA
	    * @param @param accountB
	    * @param @return    ����
	    * @return boolean    ��������
	    * @throws
	    */
	    
	public boolean sendMessage(String sendMessage, Socket socket, Account accountA, Account accountB) {
		
		try {
			
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			//���û��������Ϣ����ת���ɱ��ģ���ʹ��UTF����ı��Ĵ���
			dos.writeUTF((new Message(accountA.getId(), accountA.getName(), accountB.getId(), accountB.getName(), (new String(sendMessage.getBytes("UTF-8"), "UTF-8")))).getJsonOfMessage());
			dos.flush();
			
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error in sendMessage: " + e);
			
			return false;
		}
				
		return true;
	}
	
	public boolean sendChatWithOtherAccountId(Account theOtherAccount, Socket socket) {
		try {
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			dos.writeUTF(theOtherAccount.conventAccountToString());
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error in sendChatWithOtherAccountId: " + e);
			return false;
		}
	}
	
}
