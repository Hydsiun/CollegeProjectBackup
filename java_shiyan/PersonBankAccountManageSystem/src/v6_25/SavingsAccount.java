/**
 * 
 */
package v6_25;
import v6_25.Date;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;


/**
 * @className SavingsAccount
 * @description SavingsAccount.java
 * @author 31415926535x
 * @date 2019��3��5�� ����12:55:39
 * @tag 
 * @version v6_25
 */

public class SavingsAccount {
	
	/**
	 * @param id 			�˺�
	 * @param balance 		���
	 * @param rate 			����������
	 * @param lastDate 		�ϴα������ʱ��
	 * @param accumulation	�����ۼ�֮��
	 * @param total			�����˻��Ľ��
	 */
	private String id;				//�˺�
	private double balance;			//���
	private double rate;			//����������
	private Date lastDate;			//�ϴα������ʱ��
	private double accumulation;	//�����ۼ�֮��
	
	static double total = 0;        //�����˻��Ľ��
	
	/**
	 * ��¼һ����
	 * @param date 		����
	 * @param amount 	���
	 * @param desc 		˵��
	 */
	private void record(final Date date, double amount, final String desc) {
		this.accumulation = accumulate(date);
		this.lastDate = date;
		//amount = Math.floor(amount * 100 + 0.5) / 100.0;		//����С�������λ
		BigDecimal bDecimal = new BigDecimal(amount);
		amount = bDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue();
		this.balance += amount;
		total += amount;
		NumberFormat nf = new DecimalFormat("0.##");
		date.show();
		System.out.println("\t#" + id + "\t" + nf.format(amount) + "\t" + nf.format(balance) + "\t" + desc);
	}
	
	/**
	 * ���������Ϣ
	 * @param msg	������Ϣ
	 */
	private final void error(final String msg) {
		System.out.println("Error(#" + id + "): " + msg);
	}
	
	/**
	 * ��õ�ָ������Ϊֹ�Ĵ������ۻ�ֵ
	 * @param date
	 * @return accumulation + balance * (date - lastDate)
	 */
	private double accumulate(final Date date) {
		return accumulation + balance * date.distance(lastDate);
	}
	
	
	/************************** public *****************************/
	
	
	/**
	 * ���췽��
	 * @param date	����
	 * @param id	���
	 * @param rate	����
	 */
	public SavingsAccount(final Date date, final String id, double rate) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.balance = 0;
		this.rate = rate;
		this.lastDate = date;
		this.accumulation = 0;
		date.show();
		System.out.println("\t#" + id + " is created");
	}
	
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
	 * ���ش���������
	 * @return rate
	 */
	public final double getRate() {
		return rate;
	}
	
	/**
	 * �����ֽ�
	 * @param date		����
	 * @param amount	���
	 */
	public void deposit(final Date date, double amount, final String desc) {
		record(date, amount, desc);
	}
	
	/**
	 * ȡ���ֽ�
	 * @param date		����
	 * @param amount	���
	 */
	public void withdraw(final Date date, double amount, final String desc) {
		if(amount > getBalance())
			error("not enough money");
		else {
			record(date, -amount, desc);
		}
	}
	
	/**
	 * ������Ϣ��ÿ��1��1�յ���һ�θú���
	 * @param date	����
	 */
	public void settle(final Date date) {
		double interest = accumulate(date) * rate / date.distance(new Date(date.getYear() - 1, 1, 1));	//������Ϣ
		if(interest != 0)
			record(date, interest, "interest");
		this.accumulation = 0;
	}
	
	/**
	 * ��ʾ�˻���Ϣ
	 */
	public void show() {
		System.out.print(id + "\tBalance: " + balance);
	}
	
	
	/**
	 * ��ʾ�ܽ��
	 * @return total
	 */
	public static double getTotal() {
		return total;
	}

}