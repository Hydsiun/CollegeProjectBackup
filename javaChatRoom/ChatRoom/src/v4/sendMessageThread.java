package v4;

import java.io.BufferedReader;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
			PrintWriter os = new PrintWriter(socket.getOutputStream());
			
			String readlineString;
			
			while(blinker == thisThread) {
				System.out.print("You said: ");
				readlineString = sin.readLine();
				os.println(readlineString);
				os.flush();
				
				//�����ļ�����
				//����⵽ʱ�ļ�����ʱ
				if(readlineString.equals("<File>")) {
					readlineString = sin.readLine();										//���뱾��Ҫ������ļ���·��������·����
					os.println(readlineString);												//���͵���������
					os.flush();
					File file1 = new File(readlineString);									//�����ļ��Ķ���
					fileSystemOperation.getInfo(file1);										//��ʾ�ļ���Ϣ
					FileInputStream rf = new FileInputStream(file1);						//���������ļ�����
					DataOutputStream dos = new DataOutputStream(socket.getOutputStream());	//��������socket�����ݷ�����
					int count, n = userGlobalSettingsInfos.getUserBufferSize();														//����һ��������
					byte buffer[] = new byte[n];
					
					System.out.println("File is tranfering...");
					
					//�����ļ��Ĵ�С�����Է���ȡ�������С������֮���ֹͣ�����ļ������ֱ�ӹر����Ļ����ᵼ��socket�Ĺرգ������޷��ڶ��ν��д���
					dos.writeLong(file1.length());
					dos.flush();
					
					while((count = rf.read(buffer, 0, n)) != -1) {							//��ȡ�����ļ������ͣ��������ʹ���ж��Ƿ�����ļ�ĩ����ֹ���뷢��
						dos.write(buffer, 0, count);
						dos.flush();
//						System.out.println(new String(buffer));
						System.out.println(count);
					}
					
					System.out.println("File End");
//					dos.close();
//					socket.shutdownOutput();												//ֱ�ӹرշ��������߹ر����з��������޷��������
					rf.close();																//�ر��ʶ�������ò������ر���Ӱ�죿����
				}
				
				
				//
				
				if(readlineString.equals("bye")) {
					System.out.println("send Thread stop");
					stopThisThread();
					break;
				}
//				readlineString = sin.readLine();
			}
			System.out.println("send thread stopped");
//			os.close();
//			sin.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error: --" + e);
		}
	}
}
