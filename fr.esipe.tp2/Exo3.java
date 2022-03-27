package fr.esipe.tp2 ;
import java.util.regex.Pattern ;
import java.util.Objects;
import java.util.regex.Matcher ;

public record Exo3() {
	
	public static void afficherNombres(String[] args) { // Question 2
		var regex = Pattern.compile("[0-9]+") ;
		
		for(var s: args) {
			if(regex.matcher(s).matches()) {
				System.out.println(Integer.parseInt(s)) ; // on cast si jamais la saisie commence par un 0 car ce n'est pas un nombre
			} else {
				System.out.println("Cet argument n'est pas un nombre") ;
			}
		}
	}
	
	private static String reverseString(String str) {
		return new StringBuilder(str).reverse().toString() ;
	}
	
	public static void afficherNombresFinChaine(String[] args) { // Question 3
		var regex = Pattern.compile("([0-9]+)") ;
		
		for(var s: args) {
			var matcher = regex.matcher(reverseString(s)) ;
			
			if(matcher.lookingAt()) {
				System.out.println(Integer.parseInt(reverseString(matcher.group()))) ; // on cast si jamais la saisie commence par un 0 car ce n'est pas un nombre
			} else {
				System.out.println("Cet argument n'est pas une chaine valide") ;
			}
		}
	}
	
	public static byte[] parseIPv4(String ip) {
		short validByte ; // pas besoin d'un int pour stocker valeur < 255
		byte[] parsedIP = new byte[4] ;
		
		var regex = Pattern.compile("([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})") ;
		var matcher = regex.matcher(ip) ;
		
		if(matcher.matches()) {
			for(var i = 1; i <= matcher.groupCount(); i++) {
				validByte = Short.parseShort(matcher.group(i)) ;
				
				if(validByte > 255) {
					throw new IllegalArgumentException("Invalid IPv4 adress : every byte must be < 255") ;
				}
				
				parsedIP[i - 1] = (byte)validByte ; // Si validByte > 127 alors on retombe dans les négatifs car un byte ne peut pas stocker une valeur > 127. Il faut donc penser à ajouter un masque (& 0xFF) lors de la lecture du tableau de byte retourné par cette méthode
			}
		} else {
			throw new IllegalArgumentException("Invalid IPv4 adress") ;
		}
		
		return parsedIP ;
	}
	
	public static void printParsedIPv4(byte[] parsedIP) {
		var builder = new StringBuilder() ;
		var separator = "";
		
		for(var s: parsedIP) {
			builder.append(separator).append(s & 0xFF) ;
			separator = "." ;
		}
			  
		System.out.println(builder.toString()) ;
	}
	
	public static void main(String[] args) {
		if(args.length <= 0) {
	        throw new IllegalArgumentException("Usage : saisir au moins 1 argument") ;
	    }
		
		System.out.println("Question 2");
		afficherNombres(args) ;
		
		System.out.println();
	
		System.out.println("Question 3");
		afficherNombresFinChaine(args) ;
		
		System.out.println();
		
		// Question 4
		byte[] parsedIP = new byte[4] ;
		System.out.println("Question 4") ;
		parsedIP = parseIPv4(args[0]) ;
		
		printParsedIPv4(parsedIP) ;
	}
}
