package v4;


    /**
    * @ClassName: globalSettingsInfos
    * @Description: TODO �����ҵ�һЩȫ�ֵ�������Ϣ
    * @author 31415926535x
	* @mail 2509437152wx@gmail.com
    * @date 2019��5��14������8:43:29
    *
    */
    
public class userGlobalSettingsInfos {
	static String usersDirString = "G:\\Backup\\CollegeProjectBackup\\javaChatRoom\\ChatRoom\\src\\v4\\userData\\";
	static int userBufferSize = 2048;
	
	
	    /**
	    * @Title: setUserDirString
	    * @Description: TODO �����û����ļ��洢����·��
	    * @param @param userDir    ����
	    * @return void    ��������
	    * @throws
	    */
	    
	public static void setUserDirString(String userDir) {
		usersDirString = userDir;
	}
	
	    /**
	    * @Title: getUserDirString
	    * @Description: TODO ��ȡ�û��ļ��洢�ľ���·��
	    * @param @return    ����
	    * @return String    ��������
	    * @throws
	    */
	    
	public static String getUserDirString() {
		return usersDirString;
	}
	
	    /**
	    * @Title: setUserBufferSize
	    * @Description: TODO �����û������ļ��Ļ�������С��Ĭ��Ϊ2048
	    * @param @param bufferSize    ����
	    * @return void    ��������
	    * @throws
	    */
	    
	public static void setUserBufferSize(int bufferSize) {
		userBufferSize = bufferSize;
	}
	
	    /**
	    * @Title: getUserBufferSize
	    * @Description: TODO ��ȡ�ļ�����Ļ�������С
	    * @param     ����
	    * @return int    ��������
	    * @throws
	    */
	    
	public static int getUserBufferSize() {
		return userBufferSize;
	}
}
