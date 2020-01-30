package v6;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.Socket;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
    * @ClassName: sendAndReciveOfServerThread
    * @Description: TODO ����������ɵĹ����� �տͻ���A��Ϣ�ٷ��͵��ͻ���B�Ĺ��ܣ���ת������
    * @author 31415926535x
    * @date 2019-05-01����8:59:20
    *
    */

public class sendAndReciveOfServerThread extends Thread {
	private Socket clientASocket = null;
	private Socket clientBSocket = null;
	private Account accountA;
	private Account accountB;
	
	private volatile Thread blinker;
	private boolean flag = true;


	/**
	* @Title: sendAndReciveOfServerThread
	* @Description: TODO ���췽������¼�¿ͻ���A��B���׽���
	* @param @param clientASocket	A���׽���
	* @param @param clientBSocket   B���׽���
	* @throws
	*/
	    
	public sendAndReciveOfServerThread(Socket clientASocket, Socket clientBSocket, Account accountA, Account accountB) {
		// TODO Auto-generated constructor stub
		this.clientASocket = clientASocket;
		this.clientBSocket = clientBSocket;
		this.accountA = accountA;
		this.accountB = accountB;
		
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
	    * @Description: TODO ʵ�ַ������ӿͻ���A��������Ϣ��ת�����ͻ���B�Ĺ��ܣ��Կͻ���AΪ���壬��ͨ�ſɿ��� A->������->B �Լ� B->������->A ���������ͨ�ţ����Է������˿������߳̾Ϳ�����
	    * @param     ����
	    */
	    
	public void run() {
		@SuppressWarnings("static-access")
		Thread thisThread = this.currentThread();
		
		try {
			
//			BufferedReader is1 = new BufferedReader(new InputStreamReader(clientASocket.getInputStream()));		//���տͻ���A������Ϣ
//			PrintWriter os2 = new PrintWriter(clientBSocket.getOutputStream());									//�����յ���Ϣת����B
			
			DataInputStream dis = new DataInputStream(clientASocket.getInputStream());
			DataOutputStream dos = new DataOutputStream(clientBSocket.getOutputStream());
			
			
//			BufferedReader is2 = new BufferedReader(new InputStreamReader(clientBSocket.getInputStream()));
//			PrintWriter os1 = new PrintWriter(clientASocket.getOutputStream());
			
			String readlineString;
			while(blinker == thisThread) {
				
//				readlineString = is1.readLine();
//				readlineString = dis.readUTF();
//				byte[] readlineByte = new byte[4096];
//				dis.read(readlineByte);
				
				
				//ʹ��UTF�������ʽ��ȡһ��������Ϣ
				readlineString = dis.readUTF();
//				System.out.println(readlineString);
				
//				readlineString = new String(readlineByte, "UTF8");
				
				
				//���ַ�����ʽ�ı���ת����json���͵�ʵ����������֮��Ĳ�ͬkey��value��ȡ
				JsonObject jsonObject = (JsonObject) new JsonParser().parse(readlineString);
				
				
				//����ǰ��������ͨ����Ϣʱ
				if(jsonObject.get("type").getAsString().equals("message")) {
					
//					dos.writeUTF(readlineString);
//					dos.flush();
//					dos.write(readlineByte);
					
					
					//���������ķ��͵���һ���ͻ���
					dos.writeUTF(readlineString);
					dos.flush();
					
					
//					os2.println(readlineString);
//					os2.flush();
					
					//����������ʾͨ�ŵļ�¼���˴����Խ���¼���浽���ݿ�
					System.out.println(jsonObject.get("date").getAsString() + " " + jsonObject.get("fromAccountId").getAsString() + "said to " + jsonObject.get("toAccountId").getAsString() + ": " + jsonObject.get("message").getAsString());
					
					//������һ���û����ٺ���һ���û�����ʱ���Ͽ���һ���߳�
					if(jsonObject.get("message").getAsString().equals("bye")) {
						System.out.println("stop send and recive thread");
						stopThisThread();
						break;
					}
				}
				else {	//����ǰ�������ļ�����ı���ʱ
					
					//����һ���ڷ��������µ��ļ����ݴ��ļ�
					File file = new File(serverGlobalSettingsInfos.getServerDirString() + accountA.getId() + "\\" + jsonObject.get("fileName").getAsString());
					//��ӡ�ļ��ľ���·��
					System.out.println(serverGlobalSettingsInfos.getServerDirString() + accountA.getId() + "\\" + jsonObject.get("fileName").getAsString());
					//����һ���ļ����������true��ʾд��ķ�ʽ�����ļ�ĩ׷������
					FileOutputStream wf = new FileOutputStream(file, true);
					//�ɱ��Ļ�ȡ����ǰ�����д�����ļ����ݵĴ�С������֮���ļ���д��Ĵ�С
					int n = jsonObject.get("size").getAsInt();
					
					
					System.out.println(n);
//					System.out.println("data size: " + jsonObject.get("data").getAsString().getBytes("unicode").length);
//					System.out.println("data: " + jsonObject.get("data").getAsString());
//					wf.write(jsonObject.get("data").getAsString().getBytes(serverGlobalSettingsInfos.getEncoding()), 0, n);
//					wf.write(jsonObject.get("data").getAsString().getBytes(), 0, n);
					
					
					//�������е�����ת��ΪISO-8859-1���ֽ����飬Ȼ��д���ļ�
					wf.write(jsonObject.get("data").getAsString().getBytes("ISO-8859-1"), 0, n);
					wf.flush();
					wf.close();
					System.out.println("now has recived: " + jsonObject.get("nowLength").getAsLong() + " " + jsonObject.get("nowLength").getAsLong() * 100 / jsonObject.get("totalLength").getAsLong());
					
					//������������Ѿ����յ���ȫ�����ļ�������һ���ɷ������˴��͵���һ���ͻ����ļ��Ĵ����ļ����߳�
					if(jsonObject.get("flag").getAsBoolean()) {
						System.out.println("Parpering to send file to client b");
						(new fileTransferThread(accountA, accountB, clientBSocket, serverGlobalSettingsInfos.getServerDirString() + accountA.getId() + "\\" + jsonObject.get("fileName").getAsString())).start();
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Erroe in sendAndReciveThread: " + e);
		}	
	}
}

