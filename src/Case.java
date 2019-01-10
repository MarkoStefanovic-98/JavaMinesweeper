
public class Case {
	
	//Private
	boolean bombe, decouverte;
	int valeur;
	//Private fin
	
	public boolean isBombe()		{ return bombe; }
	public boolean isDecouverte() 	{ return decouverte; }
	public int getValeur()			{ return valeur; }
	public void setBombe(boolean b)		{ bombe = b; }
	public void setDecouverte(boolean d){ decouverte = d; }
	public void setValeur(int v)		{ valeur = v;}
}
//Test PUSH BENOIT