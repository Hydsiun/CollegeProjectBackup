/**
 * ��Account���Ϊ������ʹ��
 */
package v8_8;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * @className Account
 * @description Account.java
 * @author 31415926535x
 * @date 2019��3��5�� ����8:09:47
 * @tag 
 * @version v8_8
 */
abstract public class Account {
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
	 * �����ֽ�
	 * @param date		����
	 * @param amount	���
	 * @param desc		˵��
	 */
	abstract public void deposit(final Date date, double amount, final String desc);
	
	
	/**
	 * ȡ���ֽ�
	 * @param date		����
	 * @param amount	���
	 * @param desc		˵��
	 */
	abstract public void withdraw(final Date date, double amount, final String desc);
	
	/**
	 * ���㣨������Ϣ����ѵȣ���ÿ�½���һ�Σ�
	 * @param date		��������
	 */
	abstract public void settle(final Date date);
	
	/**
	 * ��ʾ�˻���Ϣ
	 */
//	abstract public void show();
	public void show() {
		NumberFormat nf = new DecimalFormat("0.##");
		System.out.print(id + "\tBalance: " + nf.format(balance));
	}
	
}