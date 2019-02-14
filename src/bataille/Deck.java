import java.util.LinkedList;
import java.util.Iterator;
import java.util.Scanner;

public class Deck {

	private LinkedList<Integer> pack_of_cards;

	public Deck() {
		pack_of_cards = new LinkedList<Integer>();
	}

	public Deck(int nbVals) {
		pack_of_cards = new LinkedList<Integer>();
		for (int j = 1; j <= nbVals; j++)
			for (int i = 0; i < 4; i++)
				pack_of_cards.add(j);
	}

	public boolean isEmpty() {
		return pack_of_cards.isEmpty();
	}

	public int size() {
		return pack_of_cards.size();
	}

	public String toString() {
		return pack_of_cards.size() + " " +(pack_of_cards.size()==0||pack_of_cards.size()==1?"carte":"cartes")+" "+pack_of_cards;
	}

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

	void riffleShuffle(int n){
		for(int i=0; i<n; i++){
			Deck tmp = split(); // coupe
			riffleWith(tmp); // mélange
		}
	}
