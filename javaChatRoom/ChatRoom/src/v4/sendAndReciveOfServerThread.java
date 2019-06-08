package v4;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.sun.org.apache.bcel.internal.generic.NEW;

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
	private volatile Thread blinker;
	private boolean flag = true;
	/**
	* @Title: sendAndReciveOfServerThread
	* @Description: TODO(������һ�仰�����������������)
	* @param     ����
	* @throws
	*/

	/**
	* @Title: sendAndReciveOfServerThread
	* @Description: TODO ���췽������¼�¿ͻ���A��B���׽���
	* @param @param clientASocket	A���׽���
	* @param @param clientBSocket   B���׽���
	* @throws
	*/
	    
	public sendAndReciveOfServerThread(Socket clientASocket, Socket clientBSocket) {
		// TODO Auto-generated constructor stub
		this.clientASocket = clientASocket;
		this.clientBSocket = clientBSocket;
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
		Thread thisThread = this.currentThread();
		
		try {
			
			BufferedReader is1 = new BufferedReader(new InputStreamReader(clientASocket.getInputStream()));		//���տͻ���A������Ϣ
			PrintWriter os2 = new PrintWriter(clientBSocket.getOutputStream());									//�����յ���Ϣת����B
			
//			BufferedReader is2 = new BufferedReader(new InputStreamReader(clientBSocket.getInputStream()));
//			PrintWriter os1 = new PrintWriter(clientASocket.getOutputStream());
			
			String readlineString = is1.readLine();
			while(blinker == thisThread) {
				os2.println(readlineString);
				os2.flush();
				System.out.println("Client1 talk ot Client2: " + readlineString);
				
				
				
				//�����ļ��Ľ���
				//����⵽�ļ�����ʱ��
				if(readlineString.equals("<File>")) {
					readlineString = is1.readLine();													//����Է��ļ���·��
					readlineString = readlineString.substring(readlineString.lastIndexOf("\\") + 1);	//�õ��ļ���
					readlineString = serverGlobalSettingsInfos.getServerDirString()
									+ MultiTalkServer.getAccount(clientASocket).getId()
									+ "\\" + readlineString;											//�õ��������˱�����ļ��ľ���·��
					System.out.println(readlineString);													//��ʾ�õ��ľ���·��
					File file2 = new File(readlineString);												//�����ļ��Ķ���
					FileOutputStream wf = new FileOutputStream(file2);									//�����ļ���д����
					DataInputStream dis1 = new DataInputStream(clientASocket.getInputStream());			//�����ļ��Ľ���������
					int n = serverGlobalSettingsInfos.getServerBufferSize(), count;																	//�����ļ��Ļ�����
					long length = dis1.readLong();														//�����ļ��Ĵ�С������������ݴ�С���ڴ˴�Сʱ�����ٶ������ݣ�תΪ������Ϣ
					long nowLength = 0;
					byte buffer[] = new byte[n];
//					readlineString = is1.readLine();
					fileSystemOperation.getInfo(file2);													//��ʾ�������ļ�����Ϣ
					
					while(nowLength < length) {																//�����ļ������ŵ��ļ��У�����ǰ������ļ��ۼƴ�С�봫��ǰ���͵�ֵ���ʱ�㲻�ٽ����ļ�����Ϊ�ļ��������
						count = dis1.read(buffer, 0, n);												//�õ�һ���ֵ��ļ����Լ�����Ĵ�С
						wf.write(buffer, 0, count);														//д�ļ�(��ǰд��Ĵ�СΪcount)
						wf.flush();
						nowLength += count;
//						System.out.print(new String(buffer));
						System.out.print(nowLength + " ");
						System.out.println(nowLength * 100 /length);
					}					
					System.out.println("tranfer done!");
					
					wf.close();																			//�ر��ļ�д����
					
					//�����ļ��ķ���
					
					
					
					
					FileInputStream rf = new FileInputStream(file2);									//�򿪸ղ��ݴ���ļ�
					DataOutputStream dos2 = new DataOutputStream(clientBSocket.getOutputStream());		//��һ�����ݴ�����
					
					readlineString = readlineString.substring(readlineString.lastIndexOf("\\") + 1);	//�����ļ���
					os2.println(readlineString);
					os2.flush();
					
					dos2.writeLong(file2.length());														//�����ļ���С
					dos2.flush();
					
					nowLength = 0;
					while(nowLength < length) {															//�����ļ�
						count = rf.read(buffer, 0, n);
						nowLength += count;
						dos2.write(buffer, 0, count);
						dos2.flush();
					}
					wf.close();
					rf.close();
					System.out.println("File tranfer has done!");
				}
				
				
				if(readlineString.equals("bye")) {
					System.out.println("stoppppppppppppp");
					stopThisThread();
					break;
				}
				readlineString = is1.readLine();
			}
			
//��Ϊ���رջᵼ���׽��ֵĹرգ��������ﲻ��
//			is1.close();
//			os2.close();
//			clientASocket.close();
//			clientBSocket.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Erroe: " + e);
		}	
	}
}
