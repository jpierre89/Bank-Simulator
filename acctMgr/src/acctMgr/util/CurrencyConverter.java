package acctMgr.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * for converting a currency to another denomination.
 * Corresponds to CurrencyType
 * @author Jon Pierre
 * @version 1.0
 */
public class CurrencyConverter {
	public final static BigDecimal US_TO_EURO_MUL = BigDecimal.valueOf(0.90);
	public final static BigDecimal US_TO_YEN_MUL = BigDecimal.valueOf(109.17);
	
	public static String getCurrencySymbol(CurrencyType type) {
		if (type == CurrencyType.US)
			return "$";
		else if (type == CurrencyType.Euro) {
			return "\u20AC";
		}
		else if(type == CurrencyType.Yen) {
			return "\u00A5";
		}
		else
			throw new Error("CurrencyConverter must conform with CurrencyType enums");
	}
	
	/**
	 * coverts currency amount from currencyType arg to CurrencyType result.
	 * Uses RoundingMode.HALF_EVEN for calculations
	 * @param arg CurrencyType to convert from
	 * @param result CurrencyType to convert to
	 * @param amount BigDecimal amount to convert
	 * @return BigDecimal the converted amount
	 */
	public static BigDecimal convert(CurrencyType arg, CurrencyType result, BigDecimal amount) {	
		switch (arg) {	
		case US :
			
			if (result == CurrencyType.US) {
				break;
			}
			else if (result == CurrencyType.Euro) {
				amount = amount.multiply(US_TO_EURO_MUL);
			}
			else if (result == CurrencyType.Yen) {
				amount = amount.multiply(US_TO_YEN_MUL);
			}
			else {
				throw new Error("CurrencyConverter must conform with CurrencyType enums");
			}
			break;
			
		case Euro :
			
			if (result == CurrencyType.US) {
				amount = amount.setScale(3, RoundingMode.HALF_EVEN).divide(US_TO_EURO_MUL, RoundingMode.HALF_EVEN);
			}
			else if (result == CurrencyType.Euro) {
				break;
			}
			else if (result == CurrencyType.Yen) {
				// Euro -> US , US -> Yen
				amount = amount.setScale(3, RoundingMode.HALF_EVEN).divide(US_TO_EURO_MUL, RoundingMode.HALF_EVEN); 
				amount = amount.multiply(US_TO_YEN_MUL); 
			}
			else {
				throw new Error("CurrencyConverter must conform with CurrencyType enums");
			}
			break;
			
		case Yen :
			
			if (result == CurrencyType.US) {
				amount = amount.setScale(3, RoundingMode.HALF_EVEN).divide(US_TO_YEN_MUL, RoundingMode.HALF_EVEN);
			}
			else if (result == CurrencyType.Euro) {	
				// Yen -> US , US to Euro	
				amount = amount.setScale(3, RoundingMode.HALF_EVEN).divide(US_TO_YEN_MUL, RoundingMode.HALF_EVEN); 		
				amount = amount.multiply(US_TO_EURO_MUL); 
			}
			else if (result == CurrencyType.Yen) {
				break;
			}
			else {
				throw new Error("CurrencyConverter must conform with CurrencyType enums");
			}
			break;
			
		default :
			throw new Error("CurrencyConverter must conform with CurrencyType enums");
		}
		
		return amount;
	}

}
