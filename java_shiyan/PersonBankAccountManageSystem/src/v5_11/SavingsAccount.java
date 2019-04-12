package v5_11;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * @className SavingsAccount
 * @description SavingsAccount.java
 * @author 31415926535x
 * @date 2019��3��5�� ����12:05:27
 * @tag 
 * @version v5_11
 */
public class SavingsAccount {
	
	/**
	 * @param id �˺�
	 * @param balance ���
	 * @param rate ����������
	 * @param lastDate �ϴα������ʱ��
	 * @param accumulation �����ۼ�֮��
	 * @param total	�����˻��Ľ��
	 */
	private int id;					//�˺�
	private double balance;			//���
	private double rate;			//����������
	private int lastDate;			//�ϴα������ʱ��
	private double accumulation;	//�����ۼ�֮��
	
	static double total = 0;        //�����˻��Ľ��
	
	/**
	 * ��¼һ����
	 * @param date ����
	 * @param amount ���
	 */
	private void record(int date, double amount) {
		this.accumulation = accumulate(date);
		this.lastDate = date;
		//amount = Math.floor(amount * 100 + 0.5) / 100;		//����С�������λ
		BigDecimal bDecimal = new BigDecimal(amount);
		amount = bDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue();
		this.balance += amount;
		total += amount;
		NumberFormat nf = new DecimalFormat("0.##");
		System.out.println(date + "\t#" + id + "\t" + nf.format(amount) + "\t" + nf.format(balance));
	}
	
	/**
	 * ��õ�ָ������Ϊֹ�Ĵ������ۻ�ֵ
	 * @param date
	 * @return accumulation + balance * (date - lastDate)
	 */
	private double accumulate(int date) {
		return accumulation + balance * (date - lastDate);
	}
	
	/**
	 * ���췽��
	 * @param date	����
	 * @param id	���
	 * @param rate	����
	 */
	public SavingsAccount(int date, int id, double rate) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.balance = 0;
		this.rate = rate;
		this.lastDate = date;
		this.accumulation = 0;
		System.out.println(date + "\t#" + id + " is created");
	}
	
	/**
	 * ���ر��
	 * @return id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * �������
	 * @return balance
	 */
	public double getBalance() {
		return balance;
	}
	
	/**
	 * ���ش���������
	 * @return rate
	 */
	public double getRate() {
		return rate;
	}
	
	/**
	 * �����ֽ�
	 * @param date		����
	 * @param amount	���
	 */
	public void deposit(int date, double amount) {
		record(date, amount);
	}
	
	/**
	 * ȡ���ֽ�
	 * @param date		����
	 * @param amount	���
	 */
	public void withdraw(int date, double amount) {
		if(amount > getBalance())
			System.out.println("Error: not enough money");
		else {
			record(date, -amount);
		}
	}
	
	/**
	 * ������Ϣ��ÿ��1��1�յ���һ�θú���
	 * @param date	����
	 */
	public void settle(int date) {
		double interest = accumulate(date) * rate / 365;	//������Ϣ
		if(interest != 0)
			record(date, interest);
		this.accumulation = 0;
	}
	
	/**
	 * ��ʾ�˻���Ϣ
	 */
	public void show() {
		System.out.print("#" + id + "\tBalance: " + balance);
	}
	
	
	/**
	 * ��ʾ�ܽ��
	 * @return total
	 */
	public static double getTotal() {
		return total;
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//���������˻�
		SavingsAccount sa0 = new SavingsAccount(1, 21325302, 0.015);
		SavingsAccount sa1 = new SavingsAccount(1, 58320212, 0.015);
		
		//������Ŀ
		sa0.deposit(5, 5000);
		sa1.deposit(25, 10000);
		sa0.deposit(45, 5500);
		sa1.withdraw(60, 4000);
		
		//�������90�쵽�����еļ�Ϣ�գ����������˻�����Ϣ
		sa0.settle(90);
		sa1.settle(90);

		//��������˻���Ϣ
		sa0.show();
		System.out.println();
		sa1.show();	
		System.out.println();
		
		System.out.println("Total: " + getTotal());
	}

}