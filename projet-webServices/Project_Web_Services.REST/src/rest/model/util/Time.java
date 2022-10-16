package rest.model.util;

import javax.xml.bind.annotation.XmlTransient;

public class Time {

	private int hour;
	private int minute;
	private int second;
	
	private String value;
	
	
	public Time(){ }
	
	
	public Time(String date_string){
		this.value = date_string;
		
		
		String spliter[] = date_string.split(":");

		this.hour = Integer.valueOf(spliter[0]);
		this.minute = Integer.valueOf(spliter[1]);
		this.second = Integer.valueOf(spliter[2]);
	}
	
	
	public Time(int hour, int minute, int second){
		this.hour = hour;
		this.minute = minute;
		this.second = second;
	}
	
	
	
	
	/**
	 * @return the hour
	 */
    @XmlTransient
	public int getHour() {
		return hour;
	}
	/**
	 * @param hour the hour to set
	 */
	public void setHour(int hour) {
		this.hour = hour;
		this.setValue();
	}
	/**
	 * @return the minute
	 */
    @XmlTransient
	public int getMinute() {
		return minute;
	}
	/**
	 * @param minute the minute to set
	 */
	public void setMinute(int minute) {
		this.minute = minute;
		this.setValue();
	}
	/**
	 * @return the second
	 */
    @XmlTransient
	public int getSecond() {
		return second;
	}
	/**
	 * @param second the second to set
	 */
	public void setSecond(int second) {
		this.second = second;
		this.setValue();
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue() {
		this.value = hour + ":" + minute + ":" + second;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return value;
	}
	
	
	
}
