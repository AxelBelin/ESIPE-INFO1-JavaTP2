package fr.esipe.tp2 ;

public class Morse {
	
	public void afficherMorseInnefficace(String[] args) {
		System.out.println("version inneficace") ;
		
		var str = "" ;
		  
		  for(var s: args) {
			  str += s + " Stop. " ;
		 }
		  
		  System.out.println(str) ;
	}
	
	public void afficherMorseEfficace(String[] args) {
		System.out.println("version efficace") ;
		
		var builder = new StringBuilder() ;
		
		for(var s: args) {
			builder.append(s).append(" Stop. ") ;
		}
			  
		System.out.println(builder.toString()) ;
	}

	public static void main(String[] args) {
		if(args.length <= 0) {
	        throw new IllegalArgumentException("Usage : saisir au moins 1 argument") ;
	    }
		
		var m = new Morse() ;
		
		m.afficherMorseInnefficace(args) ;
		System.out.println() ;
		m.afficherMorseEfficace(args) ;
	}
}

