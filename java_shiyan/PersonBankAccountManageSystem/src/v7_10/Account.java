/**
 * ����Account���࣬������SavingAccount��CreditAccount��
 */
package v7_10;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * @className Account
 * @description Account.java
 * @author 31415926535x
 * @date 2019��3��5�� ����5:44:04
 * @tag 
 * @version v7_10
 */
public class Account {
	/**
	 * @param id		�˺� 
	 * @param balance	���
	 * @param total		�����˻����ܽ���ʼΪ0
	 */
	private String id;
	private double balance;
	private static double total = 0;
	
	/**
	 * ����������õĹ��췽����idΪ�˻�
	 * @param date		����
	 * @param id		�˺�
	 */
	protected Account(final Date date, final String id) {
		this.id = id;
		this.balance = 0;
		date.show();
		System.out.println("\t#" + id + " created");
	}
	
	/**
	 * ��¼һ����
	 * @param date 		����
	 * @param amount 	���
	 * @param desc 		˵��
	 */
	protected void record(final Date date, double amount, final String desc) {
		//amount = Math.floor(amount * 100 + 0.5) / 100;
		BigDecimal bDecimal = new BigDecimal(amount);
		amount = bDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue();
		this.balance += amount;
		total += amount;
		date.show();
		NumberFormat nf = new DecimalFormat("0.##");
		System.out.println("\t#" + id + "\t" + nf.format(amount) + "\t" + nf.format(balance) + "\t" + desc);
	}
	
	/**
	 * ���������Ϣ
	 * @param msg	������Ϣ
	 */
	protected final void error(final String msg) {
		System.out.println("Error(#" + id + "): " + msg);
	}
	
	/******************* public **********************/
	
	/**
	 * ����id
	 * @return id
	 */
	public final String getId() {
		return id;
	}
	
	/**
	 * �������
	 * @return balance
	 */
	public final double getBalance() {
		return balance;
	}
	
	/**
	 * ��ʾ�ܽ��
	 * @return total
	 */
	public static double getTotal() {
		return total;
	}
	
	/**
	 * ��ʾ�˻���Ϣ
	 */
	public void show() {
		NumberFormat nf = new DecimalFormat("0.##");
		System.out.print(id + "\tBalance: " + nf.format(balance));
	}
	
}
