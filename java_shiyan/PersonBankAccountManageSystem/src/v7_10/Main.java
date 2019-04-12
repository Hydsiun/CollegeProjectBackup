/**
 * ������
 */
package v7_10;

/**
 * @className Main
 * @description Main.java
 * @author 31415926535x
 * @date 2019��3��5�� ����7:14:44
 * @tag 
 * @version v7_10
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Date date = new Date(2008, 11, 1);	//��ʼ����
		
		//���������˻�
		SavingsAccount sa1 = new SavingsAccount(date, "S3755217", 0.015);
		SavingsAccount sa2 = new SavingsAccount(date, "02342342", 0.015);
		CreditAccount ca = new CreditAccount(date, "C5392394", 10000, 0.0005, 50);
		
		//11�·ݵļ����˵�
		sa1.deposit(new Date(2008, 11, 5), 5000, "salary");
		ca.withdraw(new Date(2008, 11, 15), 2000, "buy a cell");
		sa2.deposit(new Date(2008, 11, 25), 10000, "sell stock 0323");
		
		//�������ÿ�
		ca.settle(new Date(2008, 12, 1));
		
		//12�·ݵļ�����Ŀ
		ca.deposit(new Date(2008, 12, 1), 2016, "repay the creadit");
		sa1.deposit(new Date(2008, 12, 5), 5500, "salary");
		
		//���������˻�
		sa1.settle(new Date(2009, 1, 1));
		sa2.settle(new Date(2009, 1, 1));
		ca.settle(new Date(2009, 1, 1));
		
		//��������˻���Ϣ
		System.out.println();
		sa1.show();
		System.out.println();
		sa2.show();
		System.out.println();
		ca.show();
		System.out.println();
		System.out.println("Total: " + Account.getTotal());
	}

}
