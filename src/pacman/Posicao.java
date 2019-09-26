package pacman;

public class Posicao {
	private int x;
	private int y;
	private int tempoRestanteRastro = 0;
	
	Posicao(){}
	
	public int getXPs() {
		return x;
	}
	public int getYPs() {
		return y;
	}
	public void setXps(int x) {
		this.x = x;
	}
	public void setYps(int y) {
		this.y = y;
	}
	public int getTempoRastro() {return tempoRestanteRastro;}
	public void startRastro() {tempoRestanteRastro = 10;}
	public void decair() {tempoRestanteRastro--; if(tempoRestanteRastro < 0) tempoRestanteRastro = 0;}
}