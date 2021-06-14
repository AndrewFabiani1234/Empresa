package services;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Data {
	
	/**
	 * 
	 * @param s
	 * @return
	 */
	public static Date strToDate(String s) {
		Date d = null;
		try {
			d = new SimpleDateFormat("dd/MM/yyyy").parse(s);
		}
		catch(ParseException erro) {
			
		}
		return d;
	}
	
	/**
	 * 
	 * @param date
	 * @return
	 */
	public static Date timestampToDate(Timestamp date) {
		Date data = new Date(date.getTime());
		return data;
	}
	
	
	/**
	 * 
	 * @param d
	 * @return
	 */
	public static String dataToStr(Date d) {
		LocalDate local = d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int ano = local.getYear();
		int mes = local.getMonthValue();
		int dia = local.getDayOfMonth();
		return dia+"/"+(mes<10?"0"+mes:mes)+"/"+ano;
	}
	
	
}
