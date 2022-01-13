package com.builders.api.customer.util;

import java.util.InputMismatchException;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class ValidateDocument {

	public static boolean isCPFValid(String documentCPF){
		documentCPF = trimSpecialChar(documentCPF);
		if (documentCPF.equals("00000000000") || documentCPF.equals("11111111111") || documentCPF.equals("22222222222") || documentCPF.equals("33333333333") || documentCPF.equals("44444444444") || documentCPF.equals("55555555555") || documentCPF.equals("66666666666") || documentCPF.equals("77777777777") || documentCPF.equals("88888888888") || documentCPF.equals("99999999999") || (documentCPF.length() != 11))
			return (false);
		
		char dig10, dig11;
		int sm, i, r, num, peso;
		
		try {
			// Calculo do 1o. Digito Verificador
			
			sm = 0;
			peso = 10;
			for (i = 0; i < 9; i++) {
				
				// converte o i-esimo caractere do CPF em um numero:
				// por exemplo, transforma o caractere '0' no inteiro 0
				// (48 eh a posicao de '0' na tabela ASCII)  
				
				num = (int) (documentCPF.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig10 = '0';
			else
				dig10 = (char) (r + 48); // converte no respectivo caractere numerico

			// Calculo do 2o. Digito Verificador
			sm = 0;
			peso = 11;
			for (i = 0; i < 10; i++) {
				num = (int) (documentCPF.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig11 = '0';
			else
				dig11 = (char) (r + 48);

			if ((dig10 == documentCPF.charAt(9)) && (dig11 == documentCPF.charAt(10)))// Verifica se os digitos calculados conferem com os digitos informados.
				return (true);
			else
				return (false);
		} catch (InputMismatchException erro) {
			return (false);
		}
		
	}
	
	public static boolean isCNPJValid(String documentCNPJ){
		documentCNPJ = trimSpecialChar(documentCNPJ);
		
		if (documentCNPJ.equals("00000000000000") || documentCNPJ.equals("11111111111111") || documentCNPJ.equals("22222222222222") || documentCNPJ.equals("33333333333333") || documentCNPJ.equals("44444444444444") || documentCNPJ.equals("55555555555555") || documentCNPJ.equals("66666666666666") || documentCNPJ.equals("77777777777777") || documentCNPJ.equals("88888888888888") || documentCNPJ.equals("99999999999999") || (documentCNPJ.length() != 14))
			return (false);
		
		char dig13, dig14;
		int sm, i, r, num, peso;
		
		try {
			// Calculo do 1o. Digito Verificador
			sm = 0;
			peso = 2;
			
			for (i = 11; i >= 0; i--) {
				
				// converte o i-ésimo caractere do CNPJ em um número:
				// por exemplo, transforma o caractere '0' no inteiro 0
				// (48 eh a posição de '0' na tabela ASCII)
				
				num = (int) (documentCNPJ.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso + 1;
				if (peso == 10)
					peso = 2;
			}

			r = sm % 11;
			if ((r == 0) || (r == 1))
				dig13 = '0';
			else
				dig13 = (char) ((11 - r) + 48);

			// Calculo do 2o. Digito Verificador
			sm = 0;
			peso = 2;
			for (i = 12; i >= 0; i--) {
				num = (int) (documentCNPJ.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso + 1;
				if (peso == 10)
					peso = 2;
			}

			r = sm % 11;
			if ((r == 0) || (r == 1))
				dig14 = '0';
			else
				dig14 = (char) ((11 - r) + 48);

			if ((dig13 == documentCNPJ.charAt(12)) && (dig14 == documentCNPJ.charAt(13))) // Verifica se os dígitos calculados conferem com os dígitos informados.
				return (true);
			else
				return (false);
		} catch (InputMismatchException erro) {
			return (false);
		}
		
	}
	
	public static boolean isEmailValid(String email){
		boolean result = true;
        try {
       
        	InternetAddress emailAddr = new InternetAddress(email);
            
        	emailAddr.validate();
        
        } catch (AddressException ex) {
            result = false;
        }
        
        return result;
	}
	
	private static String trimSpecialChar(String document) {
		if (document.contains(".")) {
			document = document.replace(".", "");
		}
		if (document.contains("-")) {
			document = document.replace("-", "");
		}
		if (document.contains("/")) {
			document = document.replace("/", "");
		}
		return document;
	}
	
}
