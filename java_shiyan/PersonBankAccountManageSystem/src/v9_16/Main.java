/**
 * ����Java�Դ���ArrayList����ʵ�ָ����˻��Ĵ洢�޸ĵȲ���
 */
package v9_16;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @className Main
 * @description Main.java
 * @author 31415926535x
 * @date 2019��3��7�� ����12:16:23
 * @tag 
 * @version v9_16
 */
public class Main {

	/**
	 * @param args
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		
		//��ʼ����
		Date date = new Date(2008, 11, 1);
		
		//�����˻�����
		ArrayList<Account> accounts = new ArrayList<Account>();
		//ArrayList<Account> accounts = new ArrayList<>();
		
		System.out.println("(a)add account (d)deposit (w)withdraw (s)show (c)change day (n)next month (e)exit");
		
		char cmd;
		
		do {
			//��ʾ���ں��ܽ��
			date.show();
			System.out.print("\tTotal: " + Account.getTotal() + "\tcommand> ");
			
			char type;
			int index, day;
			double amount, credit, rate, fee;
			String id, desc, cmdString;
			Account account;
			
			cmdString = input.next();
			cmd = cmdString.charAt(0);
			
			switch (cmd) {
			case 'a':		//�����˻�
				String typeString = input.next();
				type = typeString.charAt(0);
				id = input.next();
				if(type == 's') {
					rate = input.nextDouble();
					account = new SavingsAccount(date, id, rate);
				}
				else {
					credit = input.nextDouble();
					rate = input.nextDouble();
					fee = input.nextDouble();
					account = new CreditAccount(date, id, credit, rate, fee);
				}
				accounts.add(account);
				break;
			case 'd':		//�����ֽ�
				index = input.nextInt();
				amount = input.nextDouble();
				desc = input.nextLine();
				accounts.get(index).deposit(date, amount, desc);
				break;
			case 'w':		//ȡ���ֽ�
				index = input.nextInt();
				amount = input.nextDouble();
				desc = input.nextLine();
				accounts.get(index).withdraw(date, amount, desc);
				break;
			case 's':		//��ѯ���˻���Ϣ
				for(int i = 0; i < accounts.size(); ++i) {
					System.out.print("[" + i + "] ");
					accounts.get(i).show();
					System.out.println();
				}
				break;
			case 'c':		//�ı�����
				day = input.nextInt();
				if(day < date.getDay()) {
					System.out.print("You cannot specify a previous day");
				}
				else if (day > date.getMaxDay()) {
					System.out.print("Invalid day");
				}
				else {
					date = new Date(date.getYear(), date.getMonth(), day);
				}
				break;
			case 'n':		//�����¸���
				if(date.getMonth() == 12) {
					date = new Date(date.getYear() + 1, 1, 1);
				}
				else {
					date = new Date(date.getYear(), date.getMonth() + 1, 1);
				}
				for(int i = 0; i < accounts.size(); ++i) {
					accounts.get(i).settle(date);
				}
				break;
			}
			
		}while(cmd != 'e');
		
		//������е��˻���Ϣ���Ƴ�����
		accounts.clear();
	}

}
