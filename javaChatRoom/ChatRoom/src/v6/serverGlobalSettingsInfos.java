package v6;

public class serverGlobalSettingsInfos {
	private static String serverDirString = "G:\\Backup\\CollegeProjectBackup\\javaChatRoom\\ChatRoom\\src\\v6\\serverData\\";
	private static int serverBufferSize = 2048;
	private static String encoding = "UTF8";
	    /**
	    * @Title: setServerDirString
	    * @Description: TODO �޸ķ�����Ĭ�ϵ��ļ��洢�ļ��ľ���·��
	    * @param @param userDir    ����
	    * @return void    ��������
	    * @throws
	    */
	    
	public static void setServerDirString(String userDir) {
		serverDirString = userDir;
	}
	
	
	    /**
	    * @Title: getServerDirString
	    * @Description: TODO ��ȡ�������Ĵ洢�ļ��ľ���·��
	    * @param @return    ����
	    * @return String    ��������
	    * @throws
	    */
	    
	public static String getServerDirString() {
		return serverDirString;
	}
	
	    /**
	    * @Title: setServerBufferSize
	    * @Description: TODO ���ô����ļ��Ļ������Ĵ�С
	    * @param @param BufferSize    ����
	    * @return void    ��������
	    * @throws
	    */
	    
	public static void setServerBufferSize(int BufferSize) {
		serverBufferSize = BufferSize;
	}
	
	    /**
	    * @Title: getServerBufferSize
	    * @Description: TODO �����ļ�������ļ��������Ĵ�С��Ĭ��Ϊ2048
	    * @param @return    ����
	    * @return int    ��������
	    * @throws
	    */
	    
	public static int getServerBufferSize() {
		return serverBufferSize;
	}
	
	    /**
	    * @Title: getEncoding
	    * @Description: TODO ��ȡ�������˵��ַ����ı���
	    * @param @return    ����
	    * @return String    ��������
	    * @throws
	    */
	    
	public static String getEncoding() {
		return encoding;
	}
}

