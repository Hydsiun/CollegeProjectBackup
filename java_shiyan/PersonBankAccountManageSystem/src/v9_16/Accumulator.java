/**
 * 
 */
package v9_16;

/**
 * @className Accumulator
 * @description Accumulator.java
 * @author 31415926535x
 * @date 2019��3��6�� ����1:00:36
 * @tag 
 * @version v9_16
 */
public class Accumulator {
	/**
	 * @param lastDate	�ϴα����ֵ������
	 * @param value		��ֵ�ĵ�ǰֵ
	 * @param sum		��ֵ�����ۼ�֮��
	 */
	private Date lastDate;
	double value;
	double sum;
	
	/**
	 * ���췽����dateΪ��ʼ�ۼӵ����ڣ�valueΪ��ʼֵ
	 * @param date		����
	 * @param value		ֵ
	 */
	public Accumulator(final Date date, double value) {
		this.lastDate = date;
		this.value = value;
		this.sum = 0;
	}
	
	/**
	 * ��õ�����date���ۼӽ��
	 * @param date		����
	 * @return
	 */
	public final double getSum(final Date date) {
		return sum + value * date.sub(lastDate);
	}
	
	/**
	 * ��date����ֵ���Ϊvalue
	 * @param date		����
	 * @param value		ֵ
	 */
	public void change(final Date date, double value) {
		this.sum = getSum(date);
		this.lastDate = date;
		this.value = value;
	}
	
	/**
	 * ��ʼ���������ڱ�Ϊdate����ֵ��Ϊvalue���ۼ�������
	 * @param date		����
	 * @param value		ֵ
	 */
	public void reset(final Date date, double value) {
		this.lastDate = date;
		this.value = value;
		this.sum = 0;
	}
}
