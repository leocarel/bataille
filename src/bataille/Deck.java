// Deck version 2017

import java.util.LinkedList;
import java.util.Iterator;
import java.util.Scanner;

public class Deck {

	private LinkedList<Integer> pack_of_cards;

	// paquet vide
	public Deck() {
		pack_of_cards = new LinkedList<Integer>();
	}

	// paquet de cartes complet trié avec nbVals valeurs
	public Deck(int nbVals) {
		pack_of_cards = new LinkedList<Integer>();
		for (int j = 1; j <= nbVals; j++)
			for (int i = 0; i < 4; i++)
				pack_of_cards.add(j);
	}

	public boolean isEmpty() {
		return pack_of_cards.isEmpty();
	}

	// nombre de cartes dans le paquet
	public int size() {
		return pack_of_cards.size();
	}

	// affichage
	public String toString() {
		return pack_of_cards.size() + " " +(pack_of_cards.size()==0||pack_of_cards.size()==1?"carte":"cartes")+" "+pack_of_cards;
	}
	
	// Compléter ci-dessous

	// Question 1

	public Integer pick(Deck d){
		if (d.isEmpty())
			return null;
		Integer card = d.pack_of_cards.removeFirst();
		this.pack_of_cards.addLast(card);
		return card;
	}
	
	public void pickAll(Deck d) {
		while (!d.pack_of_cards.isEmpty()) {
			this.pack_of_cards.addLast(d.pack_of_cards.removeFirst());
		}
	}

	public boolean isDeck(int nbVals) {
		int[] count = new int[nbVals];
		for (Integer x : pack_of_cards) {
			if ((x < 1) || (x > nbVals))
				return false;
			count[x - 1]++;
		}
		for (int i = 0; i < nbVals; i++)
			if (count[i] > 4)
				return false;
		return true;
	}
	
	// Question 2.1
	
	public int cut(){
		int s=0;
		for(int i=0; i<size(); i++)
			if(Math.random()<0.5) s++;
		return s;
	}

	public Deck split(){
		Deck r = new Deck();
		int c=cut();
		for(int i=0; i<c; i++)
			r.pick(this);
		return r;
	}
	
	// Question 2.2
	
	public void riffleWith (Deck s1){
		Deck result = new Deck(); 
		double p = 0.0 ;
		while(!s1.isEmpty() && !pack_of_cards.isEmpty()){
			p = ((double) s1.size())/((double) s1.size()+pack_of_cards.size());
			if(Math.random() < p)
				result.pick(s1);
			else
				result.pick(this);
		}
		result.pickAll(s1);
		result.pickAll(this);
		pack_of_cards = result.pack_of_cards;
	}
	
	// Question 2.3
	
	void riffleShuffle(int n){
		for(int i=0; i<n; i++){
			Deck tmp = split(); // coupe
			riffleWith(tmp); // mélange
		}
	}

	// Les méthodes ci-dessous sont utilisées pour les tests


	
	// paquets customisés (peuvent-être invalides)
	public Deck(int[] a){
		pack_of_cards = new LinkedList<Integer> ();
		for(Integer card:a){
			this.pack_of_cards.addLast(card);
		}
	}

	public Deck(String s){
		Scanner sc = new Scanner(s);
		pack_of_cards = new LinkedList<Integer> () ;
		while (sc.hasNextInt()) {
	          pack_of_cards.addLast(sc.nextInt());
	      }
		sc.close();
	}
	
	public int highestVal(){
		int r = 0;
		for(Integer card:pack_of_cards){
			if(card > r) r = card;
		}
		return r;
	}
	
	public int[] countCards(int nbVals){
		int[] count = new int[nbVals+1];
		for (Integer x : pack_of_cards) {
			if ((x < 1) || (x > nbVals))
				count[0]++;
			else count[x]++;
		}
		return count;
	}
	
	public boolean isSubdeck(Deck d){
		Iterator<Integer> it = d.pack_of_cards.iterator ();
		for(Integer card : this.pack_of_cards){
			if (!it.hasNext()) return false;
			while(it.next() != card){
				if (!it.hasNext()) return false;
			}
		};
		return true;
	}

	public boolean extractSubdeck(Deck d){
		Iterator<Integer> it = d.pack_of_cards.iterator ();
		for(Integer card : this.pack_of_cards){
			if (!it.hasNext()) return false;
			while(it.next() != card){
				if (!it.hasNext()) return false;
			}
			it.remove();
		};
		return true;
	}
	
	public boolean isFull(int nbVals){
		return ((pack_of_cards.size() == 4*nbVals) && isDeck(nbVals)); 
	}
	
	public boolean equals(Deck d){
		Iterator<Integer> it1 = pack_of_cards.iterator() ;
		Iterator<Integer> it2 = d.pack_of_cards.iterator() ;
		while(it1.hasNext() && it2.hasNext()){
			if(!(it1.next().equals(it2.next()))) return false;
		}
		return (!(it1.hasNext() || it2.hasNext())) ;
	}

	public int [] toArray(){
		int [] ret = new int[size()] ;
		int counter = 0 ;
		for(int card:this.pack_of_cards){
			ret[counter] = card ;
			counter++;
		}
		return ret;
	}
	
	public Deck copy(){
		Deck d = new Deck();
		for(Integer card:this.pack_of_cards)
			d.pack_of_cards.addLast(card);
		return d;
	}
	
	public boolean sameCards(Deck d){
		int nbVals = this.highestVal();
		int a[] = this.countCards(nbVals);
		int b[] = d.countCards(nbVals);
		for(int i = 0 ; i <= nbVals ; i++){
			if(a[i] != b[i]) return false;
		}
		return true;
	}
	
}
