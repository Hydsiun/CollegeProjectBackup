
    /**  
    * @Title: TalkClient.java
    * @Package v3
    * @Description: TODO
    * @author 31415926535x 
	* @email 2509437152wx@gamil.com
	* @blog cnblogs.com/31415926535x
    * @date 2019-05-01����11:14:43
    * @version v3 
    */
    
package v3;


    /**
    * @ClassName: TalkClient
    * @Description: TODO(������һ�仰��������������)
    * @author 31415926535x
    * @date 2019��5��1��
    *
    */

import java.io.*;
import java.net.*;
public class TalkClient {
	public static void main(String args[]) {
		Account account = new getRandomAccountForTest().getARandomAccountForTest();
		account.show();
		
		
		try{
			//�򱾻���4700�˿ڷ����ͻ�����
			Socket socket=new Socket("127.0.0.1", 4700);
			
			//��ϵͳ��׼�����豸����BufferedReader����
			BufferedReader sin=new BufferedReader(new InputStreamReader(System.in));
			//��Socket����õ��������������PrintWriter����
			PrintWriter os=new PrintWriter(socket.getOutputStream());
			//��Socket����õ�����������������Ӧ��BufferedReader����
			BufferedReader is=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			
			os.println(account.conventAccountToString());	//������������Լ����˻���Ϣ����¼�������б����ݿ�
			os.flush();
			
			System.out.println("Server: " + is.readLine());	//�����½�ɹ���ʾ��Ϣ
			
			System.out.println("Server: " + is.readLine());	//�����Ҫ��֮ͨ�ŵĿͻ��˵���ʾ��Ϣ
			
			String theOhterClientString;					//��¼��ǰ�ͻ�����Ҫ����ͨ�ŵ���һ���ͻ�������
			theOhterClientString = sin.readLine();
			
			os.println(theOhterClientString);				//�������������֮ͨ�ŵ���һ�ͻ���
			os.flush();
			
			
			String readline;
			readline=sin.readLine(); //��ϵͳ��׼�������һ�ַ���
			while(!readline.equals("bye")){//���ӱ�׼���������ַ���Ϊ "bye"��ֹͣѭ��
				//����ϵͳ��׼���������ַ��������Server
				os.println(readline);
				os.flush();//ˢ���������ʹServer�����յ����ַ���
				//��ϵͳ��׼����ϴ�ӡ������ַ���
				System.out.println("Client: " + readline);
				//��Server����һ�ַ���������ӡ����׼�����
				System.out.println("Server: " + is.readLine());				
				readline=sin.readLine(); //��ϵͳ��׼�������һ�ַ���
			} //����ѭ��
			os.close(); //�ر�Socket�����
			is.close(); //�ر�Socket������
			socket.close(); //�ر�Socket
		}catch(Exception e) {
			System.out.println("Error"+e); //�������ӡ������Ϣ
	    }
	}
}