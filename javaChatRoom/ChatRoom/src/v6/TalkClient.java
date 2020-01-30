package v6;


/**
* @ClassName: TalkClient
* @Description: TODO(������һ�仰��������������)
* @author 31415926535x
* @date 2019��5��1��
*
*/

import java.io.*;
import java.net.*;

import com.google.gson.Gson;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;




public class TalkClient extends Application {
	public static Account account;							//Ϊ�˽�����Ϣ�߳��ܹ���ȡ����ǰ�û���id
	public static Account theOtherAccount;
	public static Socket socket = null;
	public reciveMessageThread recive = null;

	
	
	/********** javafx ****************/


	
	@Override
	public void start(Stage primaryStage) {
		
		initTalkClient();
		System.out.println("initTalkClient run...");
		try {
			//��ȡfxml�ļ�
            Parent root = FXMLLoader.load(getClass().getResource("/v6/clientScene.fxml"));

            //���ڵı���
            primaryStage.setTitle("TalkClient");
            
            //�����ڼ��صĳ�������������������ļ�
            primaryStage.setScene(new Scene(root));
            
            primaryStage.setResizable(false);
            
            
            //������show time
            primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public boolean initTalkClient() {
		
		//TODO: Ҫʵ���û������Լ�����һ���û���Ҳ����˵��һ����½����
		//�õ�һ��������û�
		account = getRandomAccountForTest.getARandomAccountForTest();
		account.show();
		
		try {
			
			socket = new Socket("127.0.0.1", 4700);
			
			
			System.out.println((new Message(account.getId().toString(), account.getName(), "", "", "Send own account's id").getJsonOfMessage()));

			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			//��Ϊ���е��ַ������涨��UTF�����뷢�Ͳ��������Դ˴���writeUTF()
			dos.writeUTF(account.conventAccountToString());				//������������Լ����˻���Ϣ����¼�������б����ݿ�
			dos.flush();
		
			//�����շ���Ϣ�����̣߳�ʵ���շ���Ϣ��������
			recive = new reciveMessageThread(socket);
			recive.start();
			
			BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));
			String theOhterClientString = sin.readLine();
			
			System.out.println(theOhterClientString);										//��ȡ��Ҫ��֮������û���Account��json�����ַ���
			theOtherAccount = (new Gson()).fromJson(theOhterClientString, Account.class);	//��json�ַ����������Է���ʵ��
			System.out.println(theOtherAccount.getId() + " " + theOtherAccount.getName());	//��ʾ�Է���id������
			
			
//			theOhterClientString = (new Gson()).toJson(new Message(account.getId().toString(), account.getName(), theOtherAccount.getId(), theOtherAccount.getName(), "Link the other client"));
			System.out.println("send the other client json: " + theOhterClientString);
			
//			os.println(theOhterClientString);				//�������������֮ͨ�ŵ���һ�ͻ��ˣ��������ʽ��һ���ͻ���Account��json
//			os.flush();
			
			//ͬ��ʹ��UTF�����ַ���
			dos.writeUTF(theOhterClientString);												//�������������֮ͨ�ŵ���һ�ͻ��ˣ��������ʽ��һ���ͻ���Account��json
			dos.flush();
			
			//�õ�һ���ַ�������ʾ�û�Ҫ��ϵ���˵�json������exit��Ϣ
			//.......
			//���û�������ĳһ����ʱ���˳���ǰ�ĻỰ�������������������һ����ͨ��
			
			//����Ҫ��ϵ���˵�json
			//.......
			
			return true;
			
		}	
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error in TalkClient's init: " + e);
			recive.stopThisThread();
			
			return false;
		}
	}

	public static void main(String args[]) {
		launch(args);
	}
	
	
	
	
	public static void oldmain(String args[]) {
//		account = new getRandomAccountForTest().getARandomAccountForTest();
		account = getRandomAccountForTest.getARandomAccountForTest();
		account.show();
		
		
		
		launch(args);
		
		
		try{
			//�򱾻���4700�˿ڷ����ͻ�����
			Socket socket=new Socket("127.0.0.1", 4700);
			
			//��ϵͳ��׼�����豸����BufferedReader����
			BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));
//			��Socket����õ��������������PrintWriter����
			PrintWriter os = new PrintWriter(socket.getOutputStream());
//			��Socket����õ�����������������Ӧ��BufferedReader����
			BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			
			
			System.out.println((new Message(account.getId().toString(), account.getName(), "", "", "Send own account's id").getJsonOfMessage()));

			//��Ϊ���е��ַ������涨��UTF�����뷢�Ͳ��������Դ˴���writeUTF()
			dos.writeUTF(account.conventAccountToString());				//������������Լ����˻���Ϣ����¼�������б����ݿ�
			dos.flush();
/* 
 * ����Ϊ��������ļ����ݿ�stringתbyte[]���ݶ�ʧ�����ĳ���
//			os.println((new Message(account.getId().toString(), account.getName(), "", "", "Send own account's id").getJsonOfMessage()));	
//			os.println(account.conventAccountToString());				//������������Լ����˻���Ϣ����¼�������б����ݿ�
//			os.flush();
//			dos.write(account.conventAccountToString().getBytes("UTF8"));
//			dos.flush();
*/	
			
			
			//G:\Backup\clientSendIdToServer.java
			//�����շ���Ϣ�����̣߳�ʵ���շ���Ϣ��������
			reciveMessageThread recive = new reciveMessageThread(socket);
			recive.start();
			boolean clientIslogin = true;
			while(clientIslogin) {
				
				String theOhterClientString;				//��¼��ǰ�ͻ�����Ҫ����ͨ�ŵ���һ���ͻ�������
				theOhterClientString = sin.readLine();
				
				if(theOhterClientString.equals("exit")) {
					break;
				}
				
				
				System.out.println(theOhterClientString);										//��ȡ��Ҫ��֮������û���Account��json�����ַ���
				theOtherAccount = (new Gson()).fromJson(theOhterClientString, Account.class);	//��json�ַ����������Է���ʵ��
				System.out.println(theOtherAccount.getId() + " " + theOtherAccount.getName());	//��ʾ�Է���id������
				
				
//				theOhterClientString = (new Gson()).toJson(new Message(account.getId().toString(), account.getName(), theOtherAccount.getId(), theOtherAccount.getName(), "Link the other client"));
				System.out.println("send the other client json: " + theOhterClientString);
				
//				os.println(theOhterClientString);				//�������������֮ͨ�ŵ���һ�ͻ��ˣ��������ʽ��һ���ͻ���Account��json
//				os.flush();
				
				//ͬ��ʹ��UTF�����ַ���
				dos.writeUTF(theOhterClientString);												//�������������֮ͨ�ŵ���һ�ͻ��ˣ��������ʽ��һ���ͻ���Account��json
				dos.flush();
				
				
				sendMessageThread send = new sendMessageThread(socket);		//����һ��������Ϣ���̣߳���������һ���ͻ��˷�����Ϣ
				send.start();
				
				
				Thread mainThread = Thread.currentThread();					//�������������Ϣ�̵߳�flagʹmain�߳���ִͣ�У�����֤��ͨ�ŵ����ʱ����һֱΪ�ӷ���Ϣ
				while(send.getFlag()) {
					mainThread.yield();
				}
	
				System.out.println("You have stopped communiating the other person");
			}
			
			//��ǰ�û�����
			recive.stopThisThread();		//ֹͣ������Ϣ
			os.close();						//�رո�����
			sin.close();
			is.close();
			dos.close();
	//		System.out.println(23333);
			socket.close();					//�ر��׽���
			
			
		}catch(Exception e) {
			System.out.println("Error in TalkClient: "+e); //�������ӡ������Ϣ
	    }
	}
}