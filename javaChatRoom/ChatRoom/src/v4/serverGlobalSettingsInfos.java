package v4;

public class serverGlobalSettingsInfos {
	static String serverDirString = "G:\\Backup\\CollegeProjectBackup\\javaChatRoom\\ChatRoom\\src\\v4\\serverData\\";
	static int serverBufferSize = 2048;
	
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
}
