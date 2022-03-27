# Compte rendu TP2 Java Axel BELIN

## Exercice 1 - Assignation, égalité, référence

1. On considère le code suivant :

        `var s = "toto";`

        `System.out.println(s.length());`

- Quel est le type de s ? Comment le compilateur fait-il pour savoir qu'il existe une méthode length() sur s ?
        Le mot clé var, permet de faire un "inférence de type" (le compilateur va déduire le type à partir d'une expression). Ainsi, le compilateur va automatiquement appliquer le type déclaré sur la variable. Ici, il s'agit d'une chaine de caractère, donc un String.
        Le compilateur va remplacer le mot clé var par String. Une fois que le compilateur a remplacé var par le bon type, il peut ainsi savoir de quelle classe il provient et donc regarder si la méthode appelée provient bien de la classe String.

2. Qu'affiche le code suivant ? Expliquer.

        `var s1 = "toto";

        var s2 = s1;

        var s3 = new String(s1);
    
        System.out.println(s1 == s2);

        System.out.println(s1 == s3);`

Le code suivant affiche : **true** puis **false** .

`var s1 = "toto";`

`var s2 = s1;`

Ces instructions font une copie de l'adresse de la variable s1 dans la variable s2. Ainsi, le compilateur n'a pas besoin de recréer un nouvel objet string pour en faire une copie car s2 va pointer vers la case mémoire qui contient toto.
Un objet String est immuable, donc le compilateur sait qu'il s'agit d'une constante. Ainsi, il va créer une seule fois la chaine et faire pointer toutes les autres références à cette chaine vers la même zone mémoire (qui contient la chaine toto).
Le deuxième affichage affiche false car on compare l'adresse de deux objets différents qui contiennent deux adresses différentes. S1 et s3 ont le même contenu mais pas la même adresse. En effet, s3 est un autre objet de type String car le mot clé "new" effectue une allocation en mémoire.

3. Quelle est la méthode à utiliser si l'on veut tester si le contenu des chaînes de caractères est le même ?
        Pour tester le contenu de deux objets différents on appelle la méthode equals() qui teste si le contenu de deux objets sont égaux.

4. Qu'affiche le code suivant ? Expliquer.

        `var s6 = "toto";`

        `var s7 = "toto";`

        `System.out.println(s6 == s7);`

Pour les chaines de carcatères, Java créé un objet en mémoire la première fois que le compilateur en voit une.
Ensuite, si une autre chaine de caractères est crée et qu'elle a le même contenu qu'une chaine déjà en mémoire, comme une chaine est immuable, il n'y a aucun intéret de réallouer de la mémoire pour stocker un objet qui a le même contenu et qui ne sera pas modifié.
Dans cet exemple, le compilateur va regarder le hashcode des chaines s6 et s7.
Comme il est identique (car elles contiennent toutes les 2 la chaine "toto"), alors il sait qu'il n'a pas besoin de stocker une deuxième fois une chaine qui est identique à une autre déjà stockée.

5. Expliquer pourquoi il est important que java.lang.String ne soit pas mutable.

        En Java les objets String sont immuables car si plusieurs objets différents pointent sur l'adresse d'une même chaine cela peut être dangereux d'avoir le droit de modifier le contenu de l'objet.
        En effet, on risquerait de provoquer des incohérences dans le programme.
        Par exemple: si j'ai une chaine "Bonjour" et qu'un objet pointe sur son adresse, il n'est pas souhaitable de modifier cette chaine à un autre endroit du code avec "au revoir". Cela va créer une incohérence dans le programme.

6. Qu'affiche le code suivant ?

        `var s8 = "hello";`

        `s8.toUpperCase();`

        `System.out.println(s8);`

Le code suivant affiche "hello".
La méthode toUpperCase() renvoie un nouvel objet String. Donc ici, un appel à cette méthode renvoie une nouvelle chaine "TOTO".
Cepandant, comme la valeur de retour de cette méthode n'est pas stockée dans une variable alors on ne la récupère pas. Ainsi, l'affichage de s8 affiche la chaine qui a été assignée à cet objet lors de sa création ("toto").
Les String sont immutables en Java et les méthodes appelées sur les objets String renvoient un nouvel objet String.

## Exercice 2 - En morse. Stop.

1. Utiliser dans un premier temps l'opérateur + qui permet la concaténation de chaînes de caractères.
   ### Code :
        `public class Morse {

                public static void main(String[] args) {

                        System.out.println("version inneficace") ;
		
		        var str = "" ;
		  
		        for(var s: args) {

			        str += s + " Stop. " ;

		        }
		  
		        System.out.println(str) ;
                }
        } `
        
   ### Résultat :
   `java Morse ceci est drole`
   
   `ceci Stop. est Stop. drole Stop.`

2. A quoi sert l'objet java.lang.StringBuilder ?
L'objet java.lang.StringBuilder permet de créer un Buffer extensible de caractères (un buffer qui contient des caractères) :
   - Cela évite d’avoir trop d’allocations de String intermédiaires comme lorsque l'on utilise l'opérateur +.
   - Cela permet de faire du formattage de chaine de caractères sans avoir à créer plusieurs String.
   - Cela est pratique pour des questions d'optimisation de mémoire.

Une fois le formatage de la chaine terminé, il faut mettre le StringBuilder sous la forme d'un String grâce à la méthode toString().

- Pourquoi sa méthode append(String) renvoie-t-elle un objet de type StringBuilder ?

        La méthode append() renvoie un StringBuilder pour pouvoir chainer les appels. Cela est très pratique pour formatter une chaine de caractères, surtout pour des questions de lisibilité du code.
        
        Exemple :
        
        `var builder = new StringBuilder();
        
	for(var s: array) {

	    builder.append(s).append("un truc").append("un autre truc").append("etc.) ;

        } `

3. Réécrire la classe Morse en utilisant un StringBuilder.
   ### Code :
        `public class Morse {

                public static void main(String[] args) {

                        System.out.println("version efficace") ;
		
		        var builder = new StringBuilder() ;
		
		        for(var s: args) {

			   builder.append(s).append(" Stop. ") ;

		        }
			  
		        System.out.println(builder.toString()) ;
                }
        } `

   ### Résultat :
   `java Morse ceci est drole`
   
   `ceci Stop. est Stop. drole Stop.`

- Quel est l'avantage par rapport à la solution précédente ?

        L'opérateur + sur une chaine est très innéficace dans une boucle car le compilateur ne peut pas faire la concaténation en une seule chaine.
        En effet, il est obligé de réallouer de la mémoire à chaque itération. Par contre, hors d'une boucle, on peut utiliser + sans problème car le compilateur va faire toute la concaténation dans une seule chaine.

4. Recopier le code suivant dans une classe de Test :
   ### Code :
        `public class Test {

	        public static void main(String[] args) {

		        var first = args[0];

                        var second = args[1];

                        var last = args[2];

                        System.out.println(first + ' ' + second + ' ' + last) ;
	        }
        }

   ### Résultat :
   `java Morse toto titi tata`
   
   `toto titi tata`

- Pourquoi peut-on utiliser **' '** à la place de **" "** ?

        Dans cet exemple, on ne veut concaténer qu'un seul caractère entre chaque +.
        Comme on ne veut qu'ajouter un espace cela ne sert à rien de recréer une nouvelle chaine juste pour un seul caractère.
        Les quotes ('') peuvent être utilisées pour ne contenir qu'un seul caractère.

- Compiler le code puis utiliser la commande javap pour afficher le bytecode Java (qui n'est pas un assembleur) généré. Que pouvez-vous en déduire ?

   ### Bytecode généré :
        `public static void main(java.lang.String[]);

                Code:

                0: aload_0

                1: iconst_0

                2: aaload

                3: astore_1

                4: aload_0

                5: iconst_1

                6: aaload

                7: astore_2

                8: aload_0

                9: iconst_2

                10: aaload

                11: astore_3

                12: getstatic     #7                  // Field java/lang/System.out:Ljava/io/PrintStream;
                
                15: aload_1

                16: aload_2

                17: aload_3
                
                18: invokedynamic #13,  0             // InvokeDynamic #0:makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
                
                23: invokevirtual #17                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
                
                26: return`

D'après le bytecode généré, on remarque que cette version de Java utilise des opérations spécifiques appelées invokedynamic et invokevirtual.
Ces dernières permettent ici d'effectuer une concaténation de plusieurs chaines de carcatères dans une seule chaine sans avoir besoin de réallouer à chaque fois de la mémoire.
Ainsi, une seule et unique chaine sera crée en mémoire qui contiendra la chaine de caractère finale issue de la concatanation suivante : first + ' ' + second + ' ' + last.

5. Compiler le code de la question 1, puis utiliser la commande javap pour afficher le bytecode Java généré.
   ### Bytecode généré :
        `public static void main(java.lang.String[]);

                Code:

                0: ldc           #7                  // String
                
                2: astore_1

                3: aload_0

                4: astore_2

                5: aload_2

                6: arraylength

                7: istore_3

                8: iconst_0

                9: istore        4

                11: iload         4

                13: iload_3

                14: if_icmpge     38

                17: aload_2

                18: iload         4

                20: aaload

                21: astore        5

                23: aload_1

                24: aload         5

                26: invokedynamic #8,  0              // InvokeDynamic #0:makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
                
                31: astore_1

                32: iinc          4, 1

                35: goto          11

                38: getstatic     #4                  // Field java/lang/System.out:Ljava/io/PrintStream;
                
                41: aload_1

                42: invokevirtual #6                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
                
                45: return`

- Que pouvez-vous en déduire ?

        On constate que par rapport au Bytecode généré par la classe Test de la question 4, les instruction suivante ont été ajoutées entre l'appel de invokedynamic et de invokevirtual :
                
                `31: astore_1

                32: iinc          4, 1

                35: goto          11

                38: getstatic     #4                  // Field java/lang/System.out:Ljava/io/PrintStream;
                
                41: aload_1`

Les instructions ci-dessus signifient :
- On stocke une référence vers un élément contenu dans args dans la variable locale numéro 1.
- On incrémente un compteur de 1. Ce compteur est un nombre qui va de 0 à la taille de args.
- On effectue un jump vers l'instruction numéro 11 avec l'instruction goto. Cela signifie qu'on effectue une itération supplémentaire de la boucle for.
   - On récupère la nouvelle valeur du compteur qui correspond au compteur de boucle.
   - On récupère la taille du tableau args
   - On compare la valeur du compteur avec la taille du tableau : Si le compteur >= à la taille du tableau cela signifie qu'on a terminé de parcourir args et que l'on peut sortir de la boucle for. Si c'est le cas, on effectue un jump à l'instruction numéro 38.
   - L'instruction numéro 38 correspond à l'affichage de la chaine de caractères formatée sur la sortie standard. Cette chaine contient tous les arguments saisis par l'utilisateur séparés par la chaine "Stop." Il s'agit de la dernière instruction de notre programme.

On constate que le compilateur est obligé d'executer n fois les instructions à l'intégrieur d'une boucle où n représente ici la taille du tableau args.
A l'éxecution, notre programme devra donc allouer de la mémoire autant de fois qu'il fera d'itérations. D'ailleurs, plus la taille de args est importante, plus le programme devra faire des appels à malloc() pour stocker la chaine de caractère formatée.

Ainsi, on peut en déduire que L'opérateur + sur une chaine de carcatères est très innéficace dans une boucle car le compilateur ne peut pas faire la concaténation en une seule chaine.
Il est obligé de réallouer de la mémoire à chaque itération. Par contre, hors d'une boucle, on peut utiliser + sans problème car le compilateur va faire toute la concaténation dans une seule chaine.

- Dans quel cas doit-on utiliser StringBuilder.append() plutôt que le + ?

        Dans une boucle : il faut utiliser un StringBuilder au lieu de concaténer avec +.
        Néanmoins, l'utilisation de l'opérateur + rend le code plus lisible. Ainsi, lorsque cela ne réduit pas la performance du programme, il faut préférer l'utilisation de cet opérateur.
        C'est notamment la cas à l'extérieur d'une boucle car le compilateur peut cacluler la taille de la chaine finale et ne faire qu'une seule allocation mémoire pour stocker la chaine finale si il y a plusieurs +. 

- Et pourquoi est-ce que le chargé de TD va me faire les gros yeux si j'écris un + dans un appel à la méthode append ?

C'est totalement inutile car on perd tout l'intéret d'utiliser un StringBuilder, voire pire car on paye le prix de deux allocations alors qu'on en a besoin que d'une seule :
1. La création de l'objet StringBuilder
2. L'allocation d'un nouveau String avec l'utilisation de l'opérateur + en paramètre de la méthode append().

## Exercice 3 - Reconnaissance de motifs

1. A quoi servent la classe java.util.regex.Pattern et sa méthode compile ?

**java.util.regex.Pattern**
- Cette classe sert à composer et utiliser des expressions régulières.
Un Pattern représente un automate créé à partir d’une expression régulière.
Cette classe contient des méthodes permettant de créer des expressions régulières, mais aussi de vérifier et valider leurs syntaxes.
- Méthode compile :

Cette méthode permet de "compiler" une expression régulière donnée en paramètres sous forme d'un string.
Elle retourne un objet Pattern qui contient la version "compilée" de l'expression régulière donnée en paramètre de la méthode si sa syntaxe est valide, sinon si elle est invalide elle lève une exception de type PatternSyntaxException.
"compiler" signifie ici verifier si la syntaxe de l'expression régulière est correcte, c'est-à-dire qu'elle doit uniquement utiliser les symboles mis à disposition par la classe Pattern (+, *, ?,[a-z], [0-9], etc.), sans contenir d'erreur de syntaxe.

- A quoi sert la classe java.util.regex.Matcher ?

**java.util.regex.Matcher**
- Cette classe permet de vérifier si un motif donné correspond bien à une expression régulière (match).
Elle parcourt l’automate créé sur un texte (motif).
Matcher contient des méthodes capables d'interpréter des instances de la classe Pattern.
Ainsi, on utilise cette classe en "combinaison" avec la classe Pattern.
Par exemple, la méthode matcher de la classe Pattern prend un motif en paramètre (texte, mot, lettre, etc.) et renvoie un objet Matcher en fonction d'une expression régulière.
On peut ensuite appeler des méthodes de la classe Matcher sur cet objet comme :
- Matcher.matches() -> reconnait tout le texte/motif et retourne **true** si il match avec l'expression régulière.
- Matcher.lookingAt() -> reconnait le début du texte/motif et retourne **true** si le début match avec l'expression régulière.
- Matcher.find() -> reconnait une partie du texte/motif et retourne **true** si il existe au moins une séquence dans le texte qui match avec l'expression régulière.

2. Écrire un programme qui lit des chaînes de caractères sur la ligne de commande et affiche les chaînes qui correspondent à des nombres, c'est-à-dire les chaînes dont tous les caractères sont compris entre '0' et '9'.
   ### Code :
   `var regex = Pattern.compile("[0-9]+") ;
   
   for(var s: args) {

        if(regex.matcher(s).matches()) {

                System.out.println(Integer.parseInt(s)) ; // on cast si jamais la saisie commence par un 0 car un nombre qui commence par 0 n'est pas un nombre (sauf 0)
	
        } else {

		System.out.println("Cet argument n'est pas un nombre") ;

	}
   } `

3. Modifier le programme pour que l'on reconnaisse (et extrait) un nombre même dans le cas où le nombre est précédé par des caractères qui ne sont pas des chiffres.
   ### Code :
   Méthode qui permet de renverser un String (le mettre à l'envers) en utilisant un StringBuilder :

   `private static String reverseString(String str) {
           
           return new StringBuilder(str).reverse().toString() ;

    }`

    `var regex = Pattern.compile("([0-9]+)") ;
    
    for(var s: args) {

        var matcher = regex.matcher(reverseString(s)) ;
			
	if(matcher.lookingAt()) {

                System.out.println(Integer.parseInt(reverseString(matcher.group()))) ; // on cast si jamais la saisie commence par un 0 car un nombre qui commence par 0 n'est pas un nombre (sauf 0)
	
        } else {
                
                System.out.println("Cet argument n'est pas une chaine valide") ;
        
        }
    } `

4. Écrire une méthode qui prend en paramètre une chaîne de caractères contenant une adresse IPv4 et renvoie un tableau de 4 bytes. Il faut tester qu'il s'agit bien d'une adresse valide. Vous utiliserez pour cela la notion de groupe.
   ### Code :
   `public static byte[] parseIPv4(String ip) {

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

        byte[] parsedIP = new byte[4] ;

        System.out.println("Question 4") ;

        parsedIP = parseIPv4(args[0]) ;
        
        printParsedIPv4(parsedIP) ;
   } `

   ### Résultats des questions 2, 3 et 4 :

   `java Exo3 192.168.128.16 toto abc3 789 42a13 abc 4de f6h 78942a44`
   
   `Question 2`

   `Cet argument n'est pas un nombre` (192.168.128.16)

   `Cet argument n'est pas un nombre` (toto)

   `Cet argument n'est pas un nombre` (abc3)

   `789`

   `Cet argument n'est pas un nombre` (42a13)

   `Cet argument n'est pas un nombre` (abc)

   `Cet argument n'est pas un nombre` (4de)

   `Cet argument n'est pas un nombre` (f6h)

   `Cet argument n'est pas un nombre` (78942a44)

   `Question 3`

   `16` (192.168.128.16)

   `Cet argument n'est pas une chaine valide` (toto)

   `3` (abc3)

   `789`

   `13` (42a13)

   `Cet argument n'est pas une chaine valide` (abc)

   `Cet argument n'est pas une chaine valide` (4de)

   `Cet argument n'est pas une chaine valide` (f6h)

   `44` (78942a44)

   `Question 4`

   `192.168.128.16`
