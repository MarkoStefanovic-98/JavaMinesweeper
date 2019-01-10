
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Tableau {
	enum Difficulte {Facile, Moyen, Difficile};
	int nombreCase, nombreMine, trouverX, trouverY;
	Difficulte difficulte;
	Scanner clavier = new Scanner(System.in);
	Case tableauCases[][];
	
	void afficher(){
		do
		{
			System.out.println("\nGRILLE | " + nombreMine + " mines | x choix restants | x coups restants");

			for(int x=0; x<nombreCase; x++)	{
				for(int y=0; y<nombreCase; y++) {
					if(tableauCases[x][y].isDecouverte())
						System.out.print(" "+tableauCases[x][y].getValeur()+" ");						
					else
						System.out.print(" * ");
				}
				System.out.println();
			}
			
			System.out.print("\nChoisir une case, x : ");
			trouverX = clavier.nextInt();
			System.out.print("\nChoisir une case, y : ");
			trouverY = clavier.nextInt();
			tableauCases[trouverX][trouverY].setDecouverte(true);
			
		} while(trouverX != 0);
	} 
	
	void réglageDifficulté(Difficulte diff) {
		switch(diff)
		{
			case Facile	: nombreCase = 10; nombreMine = 10; break;
			case Moyen	: nombreCase = 15; nombreMine = 20; break;
			case Difficile	: nombreCase = 20; nombreMine = 30; break;
		}
		
		this.difficulte = diff;
	}
	
	void initialisation() {
		tableauCases = new Case[nombreCase][nombreCase]; // Initialisation d'une matrice pour localiser les cases
		
		int i=0, x=0, y=0;
		
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
		grille.réglageDifficulté(Difficulte.Facile);
		grille.initialisation();
		grille.afficher();
	}
}