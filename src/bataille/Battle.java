public class Battle {

	private int nbVals;
	private Deck trick;
	private Deck player1;
	private Deck player2;

	public Deck get_player1() {
		return player1;
	}

	public Deck get_player2() {
		return player2;
	}

	public Deck get_trick() {
		return trick;
	}
	
	public int get_nbVals() {
		return nbVals;
	}

	public String toString(){
		return
		"Joueur 1a "+(player1.toString())+"\n"+
		"Joueur 2a "+(player2.toString())+"\n"+
		(trick.isEmpty()?"pli vide":
		"pli"+trick.toString())
		;
	}
	
	public boolean equals(Battle b){
		return player1.equals(b.player1) &&
				player2.equals(b.player2) &&
				trick.equals(b.trick)
				;
	}
	
	public Battle copy(){
		Battle r = new Battle();
		r.nbVals = this.get_nbVals();
		r.player1 = this.player1.copy();
		r.player2 = this.player2.copy();
		r.trick = this.trick.copy();
		return r;
	}
	

	public Battle(int nbVals,String player1,String player2){
		this.nbVals = nbVals;
		this.player1 = new Deck(player1);
		this.player2 = new Deck(player2);
		this.trick = new Deck();
	}

	public Battle(int nbVals,Deck player1,Deck player2,Deck trick){
		this.nbVals = nbVals;
		this.player1 = player1;
		this.player2 = player2;
		this.trick = trick;
	}


	
	public Battle(int nbVals,String player1,String player2,String trick){
		this.nbVals = nbVals;
		this.player1 = new Deck(player1);
		this.player2 = new Deck(player2);
		this.trick = new Deck(trick);
	}


	public Battle(int nbVals) {
		this.nbVals = nbVals;
		Deck d = new Deck(nbVals); 
		player1 = new Deck();
		player2 = new Deck();
		trick = new Deck(); 
		d.riffleShuffle(7); 
		for (int i = 0; i < 2 * nbVals; i++) { /
			player1.pick(d); 
			player2.pick(d); 
		}

	}

	public boolean isOver() {
		return ((player1.isEmpty()) || (player2.isEmpty()));
	}

	public boolean oneRound() {
		if (this.isOver()){
			return false;}
		Integer c1 = trick.pick(player1);
		Integer c2 = trick.pick(player2);
		while (c1 == c2) {
			for (int i = 0; i < 2; i++) {
				if (this.isOver())
					return false;
				c1 = trick.pick(player1);
				c2 = trick.pick(player2);
			}
		}
		if (c1 > c2)
			player1.pickAll(trick);
		else
			player2.pickAll(trick);
		return true;
	}


	public int winner() {
		int p1 = player1.size();
		int p2 = player2.size();
		if (p1 == p2) return 0 ;
		if (p1 > p2) return 1; 
				else return 2;
	}


	public int game(int turns) {
		while ((turns > 0) && (this.oneRound()))
			turns--;
		return this.winner();
	}

	public int game() {
		Battle turtle = this.copy();
		do {
			if(!this.oneRound()) return this.winner();
			if(!this.oneRound()) return this.winner();

			turtle.oneRound();
		} while(!this.equals(turtle));
		return 3;
	}
w
	public static void stats(int nbVals, int nb_of_games){
		int player1_wins = 0 ;
		int player2_wins = 0 ;
		int out_of_cards = 0 ;
		int unfinished = 0 ;
		int result = 0;
		Battle b = null;
		for(int i = 0; i < nb_of_games ; i++){
			b = new Battle(nbVals);
			result = b.game();
			if(result == 1)player1_wins++;
			if(result == 2)player2_wins++;
			if(result == 0)out_of_cards++;
			if(result == 3)unfinished++;
		}
		System.out.println(""+player1_wins+" Questions sur "+nb_of_games+" ont été gagnées par le premier joueur.");
		System.out.println(""+player2_wins+" Questions sur "+nb_of_games+" ont été gagnées par le deuxième joueur.");
		System.out.println(""+unfinished+" Questions infinies sur "+nb_of_games);
		System.out.println(""+out_of_cards+" Questions nulles sur "+nb_of_games);
	}
	
}
