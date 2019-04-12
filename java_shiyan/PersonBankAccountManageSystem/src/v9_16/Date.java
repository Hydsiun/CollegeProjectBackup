/**
 * 
 */
package v9_16;

/**
 * @className Date
 * @description Date.java
 * @author 31415926535x
 * @date 2019��3��6�� ����12:56:16
 * @tag 
 * @version v9_16
 */
public class Date {
	/**
	 * @param year 		��
	 * @param month		��
	 * @param day		��
	 * @param totalDays	�������Ǵӹ�ԪԪ��1��1�տ�ʼ�ĵڼ���
	 * @param DAYS_BEFORE_MONTH �洢ƽ����ĳ����1��֮ǰ�ж����죬Ϊ����getMaxDay������ʵ�֣���������һ��
	 */
	private int year;
	private int month;
	private int day;
	private int totalDays;
	
	final int DAYS_BEFORE_MONTH[] = { 0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365 };
	
	/**
	 * �������չ�������
	 * @param year	��
	 * @param month	��
	 * @param day	��
	 */
	public Date(int year, int month, int day) {
		this.year = year;
		this.month = month;
		this.day = day;
		if(day <= 0 || day > getMaxDay()) {
			System.out.print("Invalid date: ");
			show();
			System.out.println();
			System.exit(0);
		}
		int years = year - 1;
		totalDays = years * 365 + years / 4 - years / 100 + years / 400 + DAYS_BEFORE_MONTH[month - 1] + day;
		if(isLeapYear() && month > 2)++totalDays;
	}
	
	
	/**
	 * �������
	 * @return year
	 */
	public final int getYear() {
		return year;
	}
	
	/**
	 * �����·�
	 * @return month
	 */
	public final int getMonth() {
		return month;
	}
	
	/**
	 * ���صڼ���
	 * @return day
	 */
	public final int getDay() {
		return day;
	}
	
	/**
	 * ��õ����ж�����
	 * @return day
	 */
	public final int getMaxDay() {
		if(isLeapYear() && month == 2)
			return 29;
		else
			return DAYS_BEFORE_MONTH[month] - DAYS_BEFORE_MONTH[month - 1];
	}
	
	/**
	 * �жϵ����Ƿ�Ϊ����
	 * @return true or false
	 */
	public final boolean isLeapYear() {
		return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
	}
	
	/**
	 * �����ǰ����
	 */
	public final void show() {
		System.out.print(getYear() + "-" + getMonth() + "-" + getDay());
	}
	
	/**
	 * ������������֮��������
	 * @param date	����
	 * @return totalDays - date.totalDays;
	 */
//	public final int distance(final Date date) {
//		return totalDays - date.totalDays;
//	}
	public final int sub(final Date date) {
		return this.totalDays - date.totalDays;
	}
}