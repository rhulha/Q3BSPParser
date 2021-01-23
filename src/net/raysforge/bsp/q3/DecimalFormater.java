package net.raysforge.bsp.q3;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class DecimalFormater {
	
	public static DecimalFormat f1;
	public static DecimalFormat f2;
	
	static {
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
		otherSymbols.setDecimalSeparator('.');
		otherSymbols.setGroupingSeparator('y'); 
		f1 = new DecimalFormat("#0.####", otherSymbols);
		
		f2 = new DecimalFormat("#0.###################", otherSymbols);
	}
	
	public static String f ( double d)
	{
		String format = f2.format(d);
		if( format.equals("-0"))
			format = "0";
		return format;
	}




}
