package v5;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;
import java.util.Date;

public class fileTransferThread extends Thread {
	private Socket socket = null;		//��ǰ�����̵߳��׽���
	private String fileName = null;		//�ļ���
	private long length = 0;			//�ļ����ܴ�С
	private long nowLength = 0;			//��ǰ������ļ���С
	private Account accountA;			//�ͻ���A�������ߣ�
	private Account accountB;			//�ͻ���B�������ߣ�
	private Date date;					//����ʱ��
	private File file;					//�������ļ�
	
	private int n = userGlobalSettingsInfos.getUserBufferSize();	//ÿһ�η��͵��������������������С
	private int count;					//�˴λ�ȡ����������С
	private byte[] buffer = new byte[n];//������
	
	
	
	private volatile Thread blinker;
	
	
	
	    /**
	     * ����һ���µ�ʵ�� fileTransferThread.�ɿͻ���A�������ļ�fileDir���͵��ͻ���B�����η��ͣ�ÿһ�����͵��׽���Ϊsocket��
	     *
	     * @param accountA		������
	     * @param accountB		������
	     * @param socket		�˴η��͵��׽���
	     * @param fileDir		�ļ��ľ���·��
	     */
	    
	public fileTransferThread(Account accountA, Account accountB, Socket socket, String fileDir) {
		// TODO Auto-generated constructor stub
		System.out.println("fileTransferThread constructoring: " + fileDir);
		this.accountA = accountA;
		this.accountB = accountB;
		this.socket = socket;
		this.date = new Date();
		this.file = new File(fileDir);
		this.length = this.file.length();
		System.out.println("The file's length is: " + length);
		this.fileName = fileDir.substring(fileDir.lastIndexOf("\\") + 1);		//�ɾ���·����ȡ�ļ���
//		this.fileName = this.fileName.substring(this.fileName.lastIndexOf("\\") + 1);
	}
	public void start() {
		blinker = new Thread(this);
		blinker.start();
	}
	public void stopThisThread() {
		blinker = null;
	}
	public void run() {
		Thread thisThread = Thread.currentThread();
		
		try {
			
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
//			PrintWriter os = new PrintWriter(socket.getOutputStream());
			FileInputStream rf = new FileInputStream(file);
			
			System.out.println("Now it is starting fileTransferThread");
			fileSystemOperation.getInfo(file);
			
			while(blinker == thisThread) {
				
				nowLength = 0;
				while(nowLength < length) {
					
					System.out.println("Now it is sending the file: " + fileName);
					
					//��ȡһ�����ݿ�
					count = rf.read(buffer, 0, n);
					nowLength += count;
					
//					os.println((new Message(accountA.getId(), accountA.getName(), accountB.getId(), accountB.getName(), date, fileName, count, nowLength, length, (new String(buffer, userGlobalSettingsInfos.getEncoding())))).getJsonOfMessage());
//					os.flush();
//					dos.writeUTF((new Message(accountA.getId(), accountA.getName(), accountB.getId(), accountB.getName(), date, fileName, count, nowLength, length, (new String(buffer, userGlobalSettingsInfos.getEncoding())))).getJsonOfMessage());
//					dos.flush();
					
					//�ȶ����ݿ�ת���ɵ��ֽڵ��ַ������ͣ�Ȼ����UTF�����json���Ĵ����ʹ˴ε����ݿ飬��������ת���ɵ��ֽڵ�byte[]�ֽ����飬����׷�ӵ��ļ�ĩ
					dos.writeUTF((new Message(accountA.getId(), accountA.getName(), accountB.getId(), accountB.getName(), date, fileName, count, nowLength, length, (new String(buffer, "ISO-8859-1"))).getJsonOfMessage()));
					dos.flush();
				}
				System.out.println(fileName + " has sent");
				rf.close();	
				stopThisThread();
			}
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error in fileTransferThread: " + e);
		}
	}
}
