package v4;

import java.io.BufferedReader;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Date;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Single;

/**
    * @ClassName: reciveMessageThread
    * @Description: TODO ������Ϣ�̣߳��뷢����Ϣ�̲߳��д���ʵ��ͨ�ŵ�������
    * @author 31415926535x
    * @date 2019-05-01����2:10:02
    *
    */

public class reciveMessageThread extends Thread {
	Socket socket = null;
	private Thread blinker;
	    /**
	    * @Title: reciveMessageThread
	    * @Description: TODO ������Ϣ�Ĳ����߳�
	    * @param @param messageString	��Ϣ����
	    * @param @param socket    		�ȴ�������Ϣһ�����׽��֣����������˵��׽��֣��ͻ��˵ȴ�������ת������Ϣ��
	    * @throws
	    */
	    
	public reciveMessageThread(Socket socket) {
		// TODO Auto-generated constructor stub
		this.socket = socket;
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
			//��Socket����õ�����������������Ӧ��BufferedReader����
			BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			String readlineString;
			
			while(blinker == thisThread) {
				readlineString = is.readLine();
				System.out.println("He said: " + readlineString);
				
				//�����ļ��Ľ���
				//����⵽�ļ�����ʱ
				if(readlineString.equals("<File>")) {
					System.out.println("File start");
					readlineString = is.readLine();								//�õ��ļ���
//					System.out.println("id:" + MultiTalkServer.getAccount(socket).getId());
					System.out.println(readlineString);
																				//�õ��ļ��ľ���·��
					readlineString = userGlobalSettingsInfos.getUserDirString()
									+ TalkClient.account.getId() + "\\"
									+ readlineString;
					System.out.println(readlineString);
					
					File file2 = new File(readlineString);						//����һ���ļ�����
					
					
					FileOutputStream wf = new FileOutputStream(file2);			//�����ļ�д����
					DataInputStream dis = new DataInputStream(socket.getInputStream());	//�����ļ�������
					fileSystemOperation.getInfo(file2);
					
					long length = dis.readLong();								//�õ��ļ���С
					long nowlength = 0;											//��ǰ�ļ���С
					int count, n = userGlobalSettingsInfos.getUserBufferSize();
					byte buffer[] = new byte[n];
					System.out.println(length + ".......");
					while(nowlength < length) {
						count = dis.read(buffer, 0, n);
						nowlength += count;
						wf.write(buffer, 0, count);
						wf.flush();
						System.out.print(nowlength + " " + length + " ");
						System.out.println(nowlength * 100 / length);			//�����ļ�����
					}
					wf.close();
					System.out.println("Tranfer done!");
				}
				
				//
				
			}
			
//			is.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error: " + e);
		}
	}
}
