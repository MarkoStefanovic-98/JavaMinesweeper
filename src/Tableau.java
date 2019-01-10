
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Tableau {
	enum Difficulte {Facile, Moyen, Difficile};
	int nombreCase, nombreMine, caseChoisie, nombreCoup, nbCaseDecouverte, nbChoixRestant;
	Difficulte difficulte;
	Scanner clavier = new Scanner(System.in);
	Case tableauCases[][];
	boolean perdu = false;
	boolean bombeDecouverte = false;
	
	void afficher(){
		boolean perdu = false, gagne = false;
		do
		{
			nbCaseDecouverte = 0;
			for(int x=0; x<nombreCase; x++) for(int y=0; y<nombreCase; y++) if(tableauCases[x][y].isDecouverte()) nbCaseDecouverte++; 
			nbChoixRestant = nombreCase*nombreCase - nbCaseDecouverte - nombreMine; 
			if(nbChoixRestant==0) gagne = true;
			System.out.println("\nGRILLE | " + nombreMine + " mines | "+(nbChoixRestant)+" choix restants | "+nombreCoup+" coups effectués");

			int z=0; 
			for(int x=0; x<nombreCase; x++) {
				for(int y=0; y<nombreCase; y++)
					System.out.printf("%03d ", ++z);
				System.out.println();
			}

			System.out.println("\nTABLEAU OUVERT");
			
			for(int x=0; x<nombreCase; x++)	{
				for(int y=0; y<nombreCase; y++) {
					if(tableauCases[x][y].isBombe() && bombeDecouverte)
					{ System.out.print(" X "); perdu=true;}
					else if(tableauCases[x][y].isDecouverte() || bombeDecouverte)
						System.out.print(" "+tableauCases[x][y].getValeur()+" ");
					else
						System.out.print(" - ");
				}
				System.out.println();
			}
			
			if(perdu){ 
				System.out.println("\nPERDU !");
			}
			else if(gagne)	{ System.out.println("\nGG WP !");}
			else {
				System.out.print("\nChoisir une case : ");
				try { caseChoisie = clavier.nextInt();  } // si l'utilisateur n'entre pas un nombre
				catch (InputMismatchException e) { System.out.println("ERREUR : Entrez un nombre !"); break; }

				decouvrirCase();
				nombreCoup++;
			}
		}while(!perdu && !gagne);
	}

	void decouvrirCase() {
		int x=0, y=0, X=0, Y=0, a=0, b=0, z=0;
		for(x=0; x<nombreCase; x++)
			for(y=0; y<nombreCase; y++)
				if(++z == caseChoisie) { X=x; Y=y; }
			

		tableauCases[X][Y].setDecouverte(true);
		if(tableauCases[X][Y].isBombe()) bombeDecouverte = true;
		for(a=-1; a<2; a++)
			for(b=-1; b<2; b++)
			{ // propagation des zéros (pas tout à fait)
				try {
					if(tableauCases[X+a][Y+b].getValeur() == 0)
						tableauCases[X+a][Y+b].setDecouverte(true);
				}
				catch(ArrayIndexOutOfBoundsException e) {} // si on dépasse la taille de la matrice
				}
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
		int i=0, x=0, y=0, a=0, b=0, z=0;
		tableauCases = new Case[nombreCase][nombreCase]; // Initialisation d'une matrice pour localiser les cases
		
		for(x=0; x<nombreCase; x++)
			for(y=0; y<nombreCase; y++)
				tableauCases[x][y] = new Case();
		
//On place les mines
		while (i<nombreMine)
		{
			x = ThreadLocalRandom.current().nextInt(0, nombreCase);
			y = ThreadLocalRandom.current().nextInt(0, nombreCase);
			if(tableauCases[x][y].isBombe() == false) {
				tableauCases[x][y].setBombe(true);
				i++;
			}
		}
		
		 for(x=0; x<nombreCase; x++)
			 for(y=0; y<nombreCase; y++)
			 { 
				z=0;
				for(a=-1; a<2; a++)
					for(b=-1; b<2; b++)	{ 
						try { if(tableauCases[x+a][y+b].isBombe()) z++;} 
						catch(ArrayIndexOutOfBoundsException e) {}
					}
			 		tableauCases[x][y].setValeur(z);
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