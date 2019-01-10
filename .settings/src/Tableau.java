
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Tableau {
	enum Difficulte {Facile, Moyen, Difficile};
	int nombreCase, nombreMine, caseChoisie;
	Difficulte difficulte;
	Scanner clavier = new Scanner(System.in);
	Case tableauCases[][];
	boolean perdu = false;
	
	void afficher(){
		do
		{
			System.out.println("\nTABLEAU | " + nombreMine + " mines | x choix restants | x coups restants");

			int z=0; 
			for(int x=0; x<nombreCase; x++) {
				for(int y=0; y<nombreCase; y++)
					System.out.printf("%03d ", ++z);
				System.out.println();
			}

			System.out.println("\nTABLEAU OUVERT");
			
			for(int x=0; x<nombreCase; x++)	{
				for(int y=0; y<nombreCase; y++) {
					if(tableauCases[x][y].isBombe() && perdu)
						System.out.print(" X ");
					else if(tableauCases[x][y].isDecouverte())
						System.out.print(" "+tableauCases[x][y].getValeur()+" ");
					else
						System.out.print(" - ");
				}
				System.out.println();
			}
			
			System.out.print("\nChoisir une case : ");
			caseChoisie = clavier.nextInt();
			decouvrirCase();
		} while(caseChoisie!=0);
	}

	void decouvrirCase() {
		int x=0, y=0, X=0, Y=0, z=0;
		for(x=0; x<nombreCase; x++)
			for(y=0; y<nombreCase; y++)
				if(++z == caseChoisie) { X=x; Y=y; }
			

		tableauCases[X][Y].setDecouverte(true);
		if(tableauCases[X][Y].isBombe()) perdu = true;
		z = calculBombe(X, Y);
		tableauCases[X][Y].setValeur(z);
	}

	int calculBombe(int X, int Y) {
		int x=0, y=0, z=0;
		for(x=-1; x<2; x++)
			for(y=-1; y<2; y++)	{
				try { if(tableauCases[X+x][Y+y].isBombe()) z++;}
				catch(ArrayIndexOutOfBoundsException e) {} //Pour éviter un crash
			}
		return z;
	}
	
	void reglageDifficulte(Difficulte diff) {
		switch(diff)
		{
			case Facile	: nombreCase = 10; nombreMine = 10; break;
			case Moyen	: nombreCase = 15; nombreMine = 20; break;
			case Difficile	: nombreCase = 20; nombreMine = 30; break;
		}
		
		this.difficulte = diff;
	}
	
	void initialisation() {
		int i=0, x=0, y=0;
		tableauCases = new Case[nombreCase][nombreCase]; // Initialisation d'une matrice pour localiser les cases
		
		for(x=0; x<nombreCase; x++)
			for(y=0; y<nombreCase; y++)
				tableauCases[x][y] = new Case();

		while (i<nombreMine)
		{
			x = ThreadLocalRandom.current().nextInt(0, nombreCase);
			y = ThreadLocalRandom.current().nextInt(0, nombreCase);
			if(tableauCases[x][y].isBombe() == false) {
				tableauCases[x][y].setBombe(true);
				i++;
			}
		}
		
		 System.out.println("\nMINES : ");
		 for(x=0; x<nombreCase; x++)
		 {
			 for(y=0; y<nombreCase; y++)
			 {
			 	if (tableauCases[x][y].isBombe()) System.out.print(" * ");
			 	else System.out.print("   ");
			 }
			 System.out.println(" ");
		 }
	}
	 
	public static void main(String[] args) {
		System.out.println("\tMineSweeper");
		
		Tableau grille = new Tableau();
		grille.reglageDifficulte(Difficulte.Facile);
		grille.initialisation();
		grille.afficher();
	}
}