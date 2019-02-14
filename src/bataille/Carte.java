
package bataille;


public class Carte {
   private String couleur;
   private int valeur;
   public static String[] tabCouleur = new String[] {"Coeur","Trèfle","Pique","Carreau"};
   public static int[] tabValeur = new int[] {2,3,4,5,6,7,8,9,10,11,12,13,1};
   
   public Carte(String Couleur, int Valeur)
	{
		this.couleur = Couleur;
		this.valeur = Valeur;
	}
   public int getValeur()
	{
		return this.valeur;
	}

}
