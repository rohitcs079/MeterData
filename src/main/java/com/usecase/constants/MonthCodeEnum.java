package com.usecase.constants;

/**
 * MonthCodeEnum for Calendar Month
 * @author ROHIT
 *
 */
public enum MonthCodeEnum {
	JAN(0), FEB(1), MAR(2), APR(3), MAY(4), JUN(5), JUL(6), AUG(7), SEP(8), OCT(
			9), NOV(10), DEC(11);
	private int code;

	MonthCodeEnum(int code) {
		this.code = code;
	}

	public int getValue() {
		return code;
	}
	
	
}