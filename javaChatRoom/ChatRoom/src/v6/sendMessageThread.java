package v6;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
    * @ClassName: sendMessageThread
    * @Description: TODO ������Ϣ�̣߳��������Ϣ�̲߳��д���ʵ��ͨ�ŵ�������
    * @author 31415926535x
    * @date 2019-05-01����2:09:36
    *
    */

public class sendMessageThread extends Thread {
	Socket socket = null;
	private volatile Thread blinker;
	private boolean flag = true; 
	/**
	* @Title: sendMessageThread
	* @Description: TODO ���췽������¼��ǰ�ͻ����������ͨ�ŵ��׽��֣����������׽��֣�
	* @param     ����
	* @throws
	*/

	public sendMessageThread(Socket socket) {
		// TODO Auto-generated constructor stub
		this.socket = socket;
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
	    * @Description: TODO �߳� run() �������壬ʵ�ִӿͻ��˵õ�һ����Ϣ�����͵��������Ĺ���
	    * @param     ����
		* @return	
	    * @throws
	    */
	    
	public void run() {
		Thread thisThread = Thread.currentThread();

		try {
			
			//��ϵͳ��׼�����豸����BufferedReader����
			BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));
			//��Socket����õ��������������PrintWriter����
//			PrintWriter os = new PrintWriter(socket.getOutputStream());
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			
			String readlineString;
			
			while(blinker == thisThread) {
				
				System.out.print("You said: ");
				readlineString = sin.readLine();

				
				if(readlineString.equals("<File>")) {
					readlineString = sin.readLine();										//��ȡҪ������ļ��ľ���·��
					System.out.println("The file that will be sent is: " + readlineString);	//��ӡҪ������ļ��ľ���·��
					(new fileTransferThread(TalkClient.account, TalkClient.theOtherAccount, socket, readlineString)).start();	//����һ���ļ�������߳�
				}
				else {
//					dos.writeUTF((new Message(TalkClient.account.getId(), TalkClient.account.getName(), TalkClient.theOtherAccount.getId(),TalkClient.theOtherAccount.getName(), readlineString)).getJsonOfMessage());
//					dos.flush();
//					dos.write((new Message(TalkClient.account.getId(), TalkClient.account.getName(), TalkClient.theOtherAccount.getId(),TalkClient.theOtherAccount.getName(), readlineString)).getJsonOfMessage().getBytes("UTF8"));
					
					//���û��������Ϣ����ת���ɱ��ģ���ʹ��UTF����ı��Ĵ���
					dos.writeUTF((new Message(TalkClient.account.getId(), TalkClient.account.getName(), TalkClient.theOtherAccount.getId(),TalkClient.theOtherAccount.getName(), (new String(readlineString.getBytes("UTF-8"), "UTF-8")))).getJsonOfMessage());
					dos.flush();
					
					
//					os.println((new Message(TalkClient.account.getId(), TalkClient.account.getName(), TalkClient.theOtherAccount.getId(),TalkClient.theOtherAccount.getName(), readlineString)).getJsonOfMessage());
//					os.flush();
					
					//���û��˳���ǰ������ʱ��������ǰ�ķ�����Ϣ���߳�
					if(readlineString.equals("bye")) {
						System.out.println("send Thread stop");
						stopThisThread();
						break;
					}
				}
			}
			System.out.println("send thread stopped");			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error: --" + e);
		}
	}
}
