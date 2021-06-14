package services;

import java.text.DecimalFormat;

public class Numero {

	
	/**
	 * Formata numeros decimais
	 * @param numero
	 * @param qtdadeCasas
	 * @return
	 */
	public static String formatar(double numero, int qtdadeCasas) {
		String mascara = "0.";
		for(int a=0; a<qtdadeCasas; a++) {
			mascara += "0";
		}
		DecimalFormat df = new DecimalFormat(mascara);
		return df.format(numero);
	}
	
	
}
