package v6;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jfoenix.controls.JFXTextArea;

import javafx.application.Platform;
import javafx.fxml.FXML;

/**
    * @ClassName: reciveMessageThread
    * @Description: TODO ������Ϣ�̣߳��뷢����Ϣ�̲߳��д���ʵ��ͨ�ŵ�������
    * @author 31415926535x
    * @date 2019-05-01����2:10:02
    *
    */

public class reciveMessageThread extends Thread {
	/************** javafx ***********/
	@FXML private JFXTextArea reciveTextArea = new JFXTextArea();
	
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
			
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			String readlineString;
			
			while(blinker == thisThread) {
				
				//��UTF����ʽ��ȡһ������
				readlineString = dis.readUTF();

				//����һ��jsonObject�Ķ������ڻ�ȡ�����е���Ϣ
				JsonObject jsonObject = (JsonObject) new JsonParser().parse(readlineString);
				
//				System.out.println(jsonObject.get("message").getAsString());
				
				//����ǰ��������ͨ����Ϣʱ
				if(jsonObject.get("type").getAsString().equals("message")) {
					//������ʾ�Է���������Ϣ
					(new clientController()).reciveMessage(jsonObject.get("date").getAsString() + "\t" + jsonObject.get("fromAccountId").getAsString() + "-" + jsonObject.get("fromAccountName").getAsString() + " said: " + jsonObject.get("message").getAsString());
					
					System.out.println(jsonObject.get("date").getAsString() + "\t" + jsonObject.get("fromAccountId").getAsString() + "-" + jsonObject.get("fromAccountName").getAsString() + " said: " + jsonObject.get("message").getAsString());
				}
				else if(jsonObject.get("type").getAsString().equals("file")) {
					//����ǰ������һ���ļ���������ݱ���ʱ
					//���ش���һ���ļ�����
					File file = new File(userGlobalSettingsInfos.getUserDirString() + TalkClient.account.getId() + "\\" + jsonObject.get("fileName").getAsString());
					//����һ���ļ������
					FileOutputStream wf = new FileOutputStream(file, true);
					//��ȡ��ǰ���ݿ�Ĵ�С
					int n = jsonObject.get("size").getAsInt();
					
					
//					wf.write(jsonObject.get("data").getAsString().getBytes(userGlobalSettingsInfos.getEncoding()), 0, n);
//					wf.write(jsonObject.get("data").getAsString().getBytes(), 0, n);
					
					//�����ݿ�ת�룬���byte[]���͵����ݣ�д���ļ�
					wf.write(jsonObject.get("data").getAsString().getBytes("ISO-8859-1"), 0, n);
					wf.flush();
					wf.close();
					System.out.println("recive file");
				}
				else if(jsonObject.get("type").getAsString().equals("account")) {
					System.out.println(readlineString);
					//����õ����ǵ�ǰ�������ߵ��û�����Ϣ��json����ʾ������Ҫ����json�õ�һ��Accounts��list
					//����֮���Accounts�б��һ������
					
					List<Account> accounts = accountMessage.getAccountsFromJosn(readlineString);
//					clientController.flushAccountsListView(accounts);
					(new clientController()).flushAccountsListView(accounts);
//					clientController.flushAccountsListView(accounts);
				}
				
			}
			
			is.close();
			dis.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error in reciveMessageThread: " + e);
		}
	}
}
