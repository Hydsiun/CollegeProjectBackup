/**
 * 
 */
package v9_16;


/**
 * @className SavingsAccount
 * @description SavingsAccount.java
 * @author 31415926535x
 * @date 2019��3��7�� ����1:20:27
 * @tag 
 * @version v9_16
 */
public class SavingsAccount extends Account {
	/*********************** private **********************/
	/**
	 * @param acc	����������Ϣ���ۼ���
	 * @param rate	����������
	 */
	private Accumulator acc;
	private double rate;
	
	/*********************** public ***********************/
	
	/**
	 * ���췽��
	 * @param date	����
	 * @param id	���
	 * @param rate	����
	 */
	public SavingsAccount(final Date date, final String id, double rate) {
		super(date, id);
		this.rate = rate;
		acc = new Accumulator(date, 0);
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
	 * @param desc		��Ϣ
	 */
	public void deposit(final Date date, double amount, final String desc) {
		record(date, amount, desc);
		acc.change(date, getBalance());
	}
	
	/**
	 * ȡ���ֽ�
	 * @param date		����
	 * @param amount	���
	 * @param desc		��Ϣ
	 */
	public void withdraw(final Date date, double amount, final String desc) {
		if(amount > getBalance()) {
			error("not enough money");
		}
		else {
			record(date, -amount, desc);
			acc.change(date, getBalance());
		}
	}
	
	/**
	 * ������Ϣ��ÿ��1��1�յ���һ�θú���
	 * @param date	����
	 */
	public void settle(final Date date) {
		if(date.getMonth() == 1) {
			//ÿ���һ�¼���һ����Ϣ
			double interest = acc.getSum(date) * rate / date.sub(new Date(date.getYear() - 1, 1, 1));	//������Ϣ
			if(interest != 0) {
				record(date, interest, "interest");
			}
			acc.reset(date, getBalance());
		}
		
		
	}
}
