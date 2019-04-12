/**
 * 
 */
package v7_10;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * @className CreditAccount
 * @description CreditAccount.java
 * @author 31415926535x
 * @date 2019��3��5�� ����6:27:23
 * @tag 
 * @version v7_10
 */
public class CreditAccount extends Account {
	/************************ private ************************/
	/**
	 * @param acc 		����������Ϣ���ۼ���
	 * @param credit 	���ö��
	 * @param rate		Ƿ���������
	 * @param fee		���ÿ����
	 */
	private Accumulator acc;
	private double credit;
	private double rate;
	private double fee;
	
	
	/**
	 * ���Ƿ���
	 * @return balance
	 */
	private final double getDebt() {
		double balance = getBalance();
		return (balance < 0 ? balance : 0);
	}
	
	/*********************** public ************************/
	
	/**
	 * CreditAccount����س�Ա������ʵ��
	 * @param date		����
	 * @param id		���
	 * @param credit	���ö��
	 * @param rate		Ƿ��������
	 * @param fee		���ÿ����
	 */
	public CreditAccount(final Date date, final String id, double credit, double rate, double fee) {
		super(date, id);
		this.credit = credit;
		this.rate = rate;
		this.fee = fee;
		this.acc = new Accumulator(date, 0);
	}
	
	/**
	 * �������ö��
	 * @return credit
	 */
	public final double getCredit() {
		return credit;
	}
	
	/**
	 * ����Ƿ��������
	 * @return rate
	 */
	public final double getRate() {
		return rate;
	}
	
	/**
	 * �������ÿ����
	 * @return fee
	 */
	public final double getFee() {
		return fee;
	}
	
	/**
	 * ��ÿ�������
	 * @return credit
	 */
	public final double getAvailableCredit() {
		if(getBalance() < 0) {
			return credit + getBalance();
		}
		else {
			return credit;
		}
	}
	
	/**
	 * �����ֽ�
	 * @param date		����
	 * @param amount	���
	 * @param desc		��Ϣ
	 */
	public void deposit(final Date date, double amount, final String desc) {
		record(date, amount, desc);
		acc.change(date, getDebt());
	}
	
	/**
	 * ȡ���ֽ�
	 * @param date		����
	 * @param amount	���
	 * @param desc		��Ϣ
	 */
	public void withdraw(final Date date, double amount, final String desc) {
		if(amount - getBalance() > credit) {
			error("not enough credit");
		}
		else {
			record(date, -amount, desc);
			acc.change(date, getDebt());
		}
	}
	
	/**
	 * ������Ϣ����ѣ�ÿ��1�յ���һ��
	 * @param date ����
	 */
	public void settle(final Date date) {
		double interset = acc.getSum(date) * rate;
		if(interset != 0) {
			record(date, interset, "interest");
		}
		if(date.getMonth() == 1)
			record(date, -fee, "annual fee");
		acc.reset(date, getDebt());
	}
	
	/**
	 * �����Ϣ
	 */
	public final void show() {
		super.show();
		NumberFormat nf = new DecimalFormat("0.##");
		System.out.print("\tAvailable credit:" + nf.format(getAvailableCredit()));
	}
}
