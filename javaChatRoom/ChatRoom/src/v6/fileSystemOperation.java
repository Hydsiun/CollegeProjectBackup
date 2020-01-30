package v6;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
    * @ClassName: fileSystemOperation
    * @Description: TODO ����������ϵͳ���ļ��������
    * @author 31415926535x
	* @mail 2509437152wx@gmail.com
    * @date 2019��5��14������8:27:48
    *
    */
    
public class fileSystemOperation extends Thread{

	

	    
	
	    /**
	    * @Title: mkdir_
	    * @Description: TODO Ϊÿһ���������еĵ�ǰ�û�����һ���Լ����ļ��У�·��Ϊ ȫ��·��+id
	    * @param @param fileNameString Ҫ�������ļ�����
	    * @param @param userOrServer �������û��˻��Ƿ������ˣ�trueΪ���û��ˣ������ڷ�������
	    * @param @return    ����
	    * @return boolean    ��������
	    * @throws
	    */
	    
	public static boolean mkdir_(String fileNameString, int type) {
		if(type == 0) {
			return true;
		}
		if(type == 1) {
			fileNameString = userGlobalSettingsInfos.getUserDirString() + fileNameString;
		}
		else {
			fileNameString = serverGlobalSettingsInfos.getServerDirString() + fileNameString;
		}
		File dirFile = new File(fileNameString);
		if(!dirFile.exists()) {
			boolean flag = dirFile.mkdirs();
			if(flag) {
				System.out.println(fileNameString + ": has been mkdir!");
			}
			else {
				System.out.println(fileNameString + ": can't be mkdired!");
			}
			try {
				getInfo(dirFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
		else {
			System.out.println(fileNameString + ": has been existed! No need to mkdir it again");
			return false;
		}
	}
	
	
	    /**
	    * @Title: getInfo
	    * @Description: TODO ��ʾ��������·�����ļ�����Ϣ
	    * @param @param f1
	    * @param @throws IOException    ����
	    * @return void    ��������
	    * @throws
	    */
	    
	public static void getInfo(File f1) throws IOException {
        SimpleDateFormat sdf;
        sdf= new SimpleDateFormat("yyyy��MM��dd��hhʱmm��");
        if (f1.isFile())
            System.out.println("<File>\t"+f1.getAbsolutePath()+"\t"+
              f1.length()+"\t"+sdf.format(new Date(f1.lastModified())));
        else
        {
            System.out.println("<Dir>\t"+f1.getAbsolutePath());
            File[] files = f1.listFiles();
            for (int i=0;i<files.length;i++)
                getInfo(files[i]);
        }
    }
}
