package v5;

import java.util.Date;
import com.google.gson.Gson;



    /**
    * @ClassName: Message
    * @Description: TODO �����࣬�����κ�һ�����ģ���������ʽ��һ������ͨ����Ϣ"message"��һ����"file"��ǰ�߻�洢��Ϣ��ԭ�ģ����߻�洢�ļ���һ�����ݿ飻ÿһ�����Ķ����������ߡ������ߵ���Ϣ�Լ����͵�ʱ�����Ϣ
    * @author 31415926535x
	* @mail 2509437152wx@gmail.com
    * @date 2019��6��8������4:41:24
    *
    */
    
public class Message {
	private String type = "message";			//���ĵ����ͣ�"message"��ʾһ�����Ϣ���ͣ�"file"��ʾ�ļ������һ�����ݿ�
	private String fromAccountId = "000";		//�����ߵ�ID
	private String fromAccountName = null;		//�����ߵ��ǳ�
	private String toAccountId = "000";			//�����ߵ�ID
	private String toAccountName = null;		//�����ߵ��ǳ�
	private String message = "";				//��Ϣ���ĵ����ݣ������͵���Ϣ����
	private Date date = null;					//����ʱ��ʱ����Ϣ
	
	private String fileName = null;				//�ļ���
	private int size = 512;						//��ǰ�����а������ļ�������
	private long nowLength = 0;					//��ǰ�ļ��Ѿ�����Ĵ�С
	private long totalLength = 0;				//��ǰ�ļ���ԭʼ��С
	private boolean flag = false;				//��ǰ�ļ��Ƿ������
	private String data = null;					//��ǰ�ļ���һ���������ݿ�
		    
	
	    /**
	     * ����һ���µ�ʵ�� Message.
	     *
	     * @param fromAccountId		��Ϣ�ķ����ߵ�ID
	     * @param fromAccountName	��Ϣ�ķ����ߵ��ǳ�
	     * @param toAccountId		��Ϣ�Ľ����ߵ�ID
	     * @param toAccountName		��Ϣ�Ľ����ߵ��ǳ�
	     * @param message			��Ϣ������
	     */
	    
	public Message(String fromAccountId, String fromAccountName, String toAccountId, String toAccountName, String message) {
		this.type = "message";
		this.fromAccountId = fromAccountId;
		this.fromAccountName = fromAccountName;
		this.toAccountId = toAccountId;
		this.toAccountName = toAccountName;
		this.message = message;
		this.date = new Date();
	}
	
	    /**
	     * ����һ����Ϊ�ļ��ı��ģ��ڶ��ļ���byte[]ȡ�����ݺ�Ҫ�������ת����ISO-8859-1���ַ�������������ƴ�ӵ��ļ�ĩβ����֤������ļ�������
	     * ������������£�
	     * buffer �Ǵ��ļ��л�ȡ��һ�������͵����ݿ�
	     * (new String(buffer, "ISO-8859-1)�����Ե��ֽڵ���ʽת�����ַ���
	     * Ȼ���message��ϱ���ΪUTF�͵ı���
	     * ���շ�ͨ��UTF��ʽ��ȡ���ĺ󣬿���ͨ����ȡ"data"�ֶε��ַ�����ת���ɡ�ISO-8859-1"��byte[]��ƴ�ӵ��ļ�ĩ����
	     * eg: wf.write(jsonObject.get("data").getAsString().getBytes("ISO-8859-1"), 0, n);
	     *
	     * @param fromAccountId			�����ߵ�ID
	     * @param toAccountId			�����ߵ�ID
	     * @param fileName				������ļ���
	     * @param bufferSize			����Ļ�����
	     * @param nowLength				����ĵ�ǰ��С
	     * @param totalLength			�ļ����ܴ�С
	     * @param data					������ļ������ݿ�
	     */
	    
	public Message(String fromAccountId, String fromAccountName, String toAccountId, String toAccountName, Date date, String fileName, int size, long nowLength, long totalLength, String data) {
		// TODO Auto-generated constructor stub
		this(fromAccountId, fromAccountName, toAccountId, toAccountName, "");		//������������һ�����صĹ����������ڵ�һ��
		this.date = date;
		this.type = "file";
		
		this.fileName = fileName;
		this.size = size;
		this.nowLength = nowLength;
		this.totalLength = totalLength;
		this.data = data;
		
		if(nowLength < totalLength) {
			flag = false;
		}
		else {
			flag = true;
		}
	}
	

		/**
	    * @Title: getJsonOfMessage
	    * @Description: TODO ��ȡһ��message���json�ַ�����Ĭ��ΪUTF-8����
	    * @param @return    ����
	    * @return String    ��������
	    * @throws
	    */
	    
	public String getJsonOfMessage() {
		return (new Gson()).toJson(this);
	}
}
