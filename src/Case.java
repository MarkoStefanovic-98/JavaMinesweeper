
public class Case {
	
	//Private
	boolean bombe, decouverte, drapeau;
	int valeur;
	//Private fin
	
	public boolean isBombe()		{ return bombe; }
	public boolean isDecouverte() 	{ return decouverte; }
	public boolean isDrapeau()		{ return drapeau; }
	public int getValeur()			{ return valeur; }
	public void setBombe(boolean b)		{ bombe = b; }
	public void setDecouverte(boolean d){ decouverte = d; }
	public void setDrapeau(boolean d)	{ drapeau = d; }
	public void setValeur(int v)		{ valeur = v;}
}
//Test PUSH BENOIT