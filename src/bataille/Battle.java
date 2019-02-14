// Battle version 2017

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
		"Joueur 1 a "+(player1.toString())+"\n"+
		"Joueur 2 a "+(player2.toString())+"\n"+
		(trick.isEmpty()?"Le pli est vide":
		"Le pli contient "+trick.toString())
		;
	}
	
	public boolean equals(Battle b){
		return player1.equals(b.player1) &&
				player2.equals(b.player2) &&
				trick.equals(b.trick)
				;
	}
	
	// La bataille sans cartes
	public Battle(){
		nbVals = 0;
		player1 = new Deck();
		player2 = new Deck();
		trick = new Deck();
	}

	// Dupliquer une Question
	public Battle copy(){
		Battle r = new Battle();
		r.nbVals = this.get_nbVals();
		r.player1 = this.player1.copy();
		r.player2 = this.player2.copy();
		r.trick = this.trick.copy();
		return r;
	}
	
	// Question truquée (pour les tests)
	public Battle(int nbVals,Deck player1,Deck player2){
		this.nbVals = nbVals;
		this.player1 = player1;
		this.player2 = player2;
		trick = new Deck();
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

	
	// Question 3.1
	
	
	// Condition initiale d'une Question avec un jeu de cartes 
	// contenant nbVals valeurs
	public Battle(int nbVals) {
		this.nbVals = nbVals;
		Deck d = new Deck(nbVals); // nouveau jeu
		player1 = new Deck(); // les joueurs n'ont aucune 
		player2 = new Deck(); // carte en main
		trick = new Deck(); // pli vide
		d.riffleShuffle(7); // on bat les cartes
		for (int i = 0; i < 2 * nbVals; i++) { // puis on les distribue
			player1.pick(d); // NB : la méthode pick renvoie une carte (int),
			player2.pick(d); // cette donnée est perdue puisqu'elle n'est stockée nulle part.
		}
		// la Question peut commencer
	}

	// Question 3.2
	
	// La Question est-elle terminée ?
	public boolean isOver() {
		return ((player1.isEmpty()) || (player2.isEmpty()));
	}


	// Un tour de jeu
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

	
	// Question 3.3
	
	// Qui a gagné ?
	public int winner() {
		int p1 = player1.size();
		int p2 = player2.size();
		if (p1 == p2) return 0 ;
		if (p1 > p2) return 1; 
				else return 2;
	}

	
	// Une Question avec un nombre maximum de coups fixé à l'avance
	public int game(int turns) {
		while ((turns > 0) && (this.oneRound()))
			turns--;
		return this.winner();
	}

	// Question 4.1
	
	// Une Question sans limite de coups avec détection des Questions infinies
	public int game() {
		// this est le lièvre
		// la tortue en est une copie
		Battle turtle = this.copy();
		do {
			// Le lièvre joue deux tours...
			if(!this.oneRound()) return this.winner();
			if(!this.oneRound()) return this.winner();
			// ...alors que la tortue n'en joue qu'un.
			turtle.oneRound();
			// La tortue n'est utilisée que pour 
			// la détection des Questions infinies.
		} while(!this.equals(turtle));
		return 3;
	}

	// Question 4.2
	
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
