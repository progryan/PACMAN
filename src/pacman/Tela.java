package pacman;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Tela extends JFrame implements KeyListener {
	static int contador = 0;
	static int pontos = 0;
	static int vidas = 3;
	int var;
	int OneRandom, TwoRandom;
	private static final long serialVersionUID = 1L;
	private final int posInicialX = 10;
	private final int posInicialY = 10;
	private static boolean flagUp = false;
	private static boolean flagDown = false;
	private static boolean flagLeft = false;
	private static boolean flagRight = false;
	private Random r = new Random();
	int superFood = 0;
	int superContador = 900;

	ImageIcon RightIcon = new ImageIcon("/Media/Gifs/p1_r.gif");
	ImageIcon LeftIcon = new ImageIcon("/Media/Gifs/p1_l.gif");
	ImageIcon UpIcon = new ImageIcon("/Media/Gifs/p1_c.gif");
	ImageIcon DownIcon = new ImageIcon("/Media/Gifs/p1_b.gif");
	ImageIcon C = new ImageIcon("/Media/Gifs/cccc.gif");
	
	ImageIcon F1 = new ImageIcon("/Media/Gifs/f1.gif");
	ImageIcon F2 = new ImageIcon("/Media/Gifs/f2.gif");
	ImageIcon F3 = new ImageIcon("/Media/Gifs/f3.gif");
	ImageIcon F4 = new ImageIcon("/Media/Gifs/f4.gif");
	

	private JLabel p1 = new JLabel(RightIcon);
	private JLabel f1 = new JLabel(F1);
	private JLabel f2 = new JLabel(F2);
	private JLabel f3 = new JLabel(F3);
	private JLabel f4 = new JLabel(F4);

	private JLabel pontuacao = new JLabel("PACMAN ainda nao comeu nada");
	private JLabel vida = new JLabel("Vidas: 3");
	Comida[][] comida = new Comida[27][20];
	ArrayList<JLabel> objetos = new ArrayList<JLabel>();
	int velocidade = 1;
	Timer t1 = new Timer();

	public Tela() {
		tocador();
		tocarPassou();
		screen();
		addObj();
		iniciarP();
		drawP();
		drawComida();
		rodarJogo();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			flagDown = false;
			flagRight = false;
			flagLeft = false;
				if (!flagUp) {
					t1.cancel();
					t1 = new Timer(true);
					flagUp = true;
					p1.setIcon(UpIcon);
	
					// System.out.println("X: " + p1.getX() + "\nY: " + p1.getY() + "\n");
					TimerTask task1 = new TimerTask() {
						@Override
						public void run() {
	
							if (colisionObject(p1.getX(), p1.getY() - velocidade - 1)) {
							} else {
								p1.setLocation(p1.getX(), p1.getY() - velocidade);
								colisionFood(p1.getX(), p1.getY());
							}
						}
					};
					t1.scheduleAtFixedRate(task1, 1, 10 - 0*superFood);
				}
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			flagUp = false;
			flagRight = false;
			flagLeft = false;
			if (!flagDown) {
				t1.cancel();
				t1 = new Timer(true);
				flagDown = true;
				// System.out.println("X: " + p1.getX() + "\nY: " + p1.getY() + "\n");
				p1.setIcon(DownIcon);

				TimerTask task1 = new TimerTask() {
					@Override
					public void run() {

						if (colisionObject(p1.getX(), p1.getY() + velocidade + 1)) {
						} else {
							p1.setLocation(p1.getX(), p1.getY() + velocidade);
							colisionFood(p1.getX(), p1.getY());
						}
					}
				};
				t1.scheduleAtFixedRate(task1, 1, 10 - 0*superFood);
			}
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			flagDown = false;
			flagRight = false;
			flagUp = false;
			if (!flagLeft) {
				t1.cancel();
				t1 = new Timer(true);
				flagLeft = true;
				// System.out.println("X: " + p1.getX() + "\nY: " + p1.getY() + "\n");
				p1.setIcon(LeftIcon);

				TimerTask task1 = new TimerTask() {
					@Override
					public void run() {

						if (colisionObject(p1.getX() - velocidade - 1, p1.getY())) {
						} else {
							p1.setLocation(p1.getX() - velocidade, p1.getY());
							colisionFood(p1.getX(), p1.getY());
						}
					}
				};
				t1.scheduleAtFixedRate(task1, 1, 10 - 0*superFood);
			}
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			flagDown = false;
			flagUp = false;
			flagLeft = false;
			if (!flagRight) {
				t1.cancel();
				t1 = new Timer(true);
				flagRight = true;
				// System.out.println("X: " + p1.getX() + "\nY: " + p1.getY() + "\n");
				p1.setIcon(RightIcon);
				TimerTask task1 = new TimerTask() {
					@Override
					public void run() {

						if (colisionObject(p1.getX() + velocidade + 1, p1.getY())) {
						} else {
							p1.setLocation(p1.getX() + velocidade, p1.getY());
							colisionFood(p1.getX(), p1.getY());
						}
					}
				};
				t1.scheduleAtFixedRate(task1, 1, 10 - 0*superFood);
			}
		}
		else if (e.getKeyCode() == KeyEvent.VK_G) {
			for (int i = 0; i < 27; i++) {
				for (int j = 0; j < 20; j++) {
					int[] rastros = vetorRastro(comida[i][j].cmd.getX(), comida[i][j].cmd.getY());
					System.out.println("p[" + i + "][" + j + "] -> " + comida[i][j].rastro);
					System.out.println("Rastros: " + rastros[0] + ", " + rastros[1] + ", " + rastros[2] + ", " + rastros[3]);
				}
			}
		}
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public boolean colisionObject(int X, int Y) {
		Rectangle rect1 = new Rectangle(X, Y, 15, 16);
		for (int i = 0; i < objetos.size(); i++) {
			Rectangle rect2 = new Rectangle(objetos.get(i).getX(), objetos.get(i).getY(), objetos.get(i).getWidth(),
					objetos.get(i).getHeight());
			if (rect1.intersects(rect2)) {
				return true;
			}
		}
		return false;
	}

	public boolean colisionFood(int X, int Y) {
		Rectangle rect1 = new Rectangle(X, Y, 15, 16);
		for (int i = 0; i < 27; i++) {
			for (int j = 0; j < 20; j++) {
				Rectangle aux = new Rectangle(comida[i][j].cmd.getX(), comida[i][j].cmd.getY(),
						comida[i][j].cmd.getWidth(), comida[i][j].cmd.getHeight());
				if(rect1.intersects(aux)) {
					comida[i][j].rastro = 3000;
				}
				if (comida[i][j].flag == true) {
					Rectangle rect2 = new Rectangle(comida[i][j].cmd.getX(), comida[i][j].cmd.getY(),
							comida[i][j].cmd.getWidth(), comida[i][j].cmd.getHeight());
					if (rect1.intersects(rect2)) {
						comida[i][j].flag = false;
						comida[i][j].cmd.setVisible(false);
						if (comida[i][j].Food) {
							pontos += 40;
							superFood = 300;
							music.stop();
							supercomida.play();
							
							Timer m = new Timer(true);
							TimerTask task1 = new TimerTask() {
								@Override
								public void run() {
									tocarPassou();
								}
							};
							m.schedule(task1, 2100);
						}
						pontos += 10;
						if (contador != 0)
							pontuacao.setText("PACMAN jah comeu " + ++contador + " vezes" + "           "
									+ "Score: " + pontos);
						else
							pontuacao.setText("PACMAN soh comeu " + ++contador + " vez" + "       " + "Score: "
									+ pontos);
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean colisionGhost(int X, int Y) {
		Rectangle rect1 = new Rectangle(p1.getX(), p1.getY(), 15, 16);
		Rectangle rect2 = new Rectangle(X, Y, 15, 16);
		if (rect1.intersects(rect2))
			return true;
		return false;
	}

	public void andar(JLabel generic, int valor) {
		int [] rastros = vetorRastro(generic.getX(), generic.getY());
		int rs;
		int r0, r1, r2, r3;
		r0 = rastros[0];
		r1 = rastros[1];
		r2 = rastros[2];
		r3 = rastros[3];
		rs = r0 + r1 + r2 + r3;
		int mover = 9999;
		if(rs > 0)
			mover = r.nextInt(rs);
		else {
			mover = r.nextInt(4);
			r0 = r1 = r2 = r3 = 1;
		}
		r0 = r1 = r2 = r3 = 1; //ANDAR NO RASTRO DESABILITADO
		if (mover < r0) {
			if (colisionObject(generic.getX(), generic.getY() - valor -1)) {
				generic.setLocation(generic.getX(), generic.getY());
			} else {
					generic.setLocation(generic.getX(), generic.getY() - valor);
			}
		} else if (mover < r0 + r1) {
			if (colisionObject(generic.getX(), generic.getY() + valor +1)) {
				generic.setLocation(generic.getX(), generic.getY());
			} else {
					generic.setLocation(generic.getX(), generic.getY() + valor);
			}
		} else if (mover < r0 + r1 + r2) {
			if (colisionObject(generic.getX() - valor -1, generic.getY())) {
				generic.setLocation(generic.getX(), generic.getY());
			} else {
					generic.setLocation(generic.getX() - valor, generic.getY());
			}
		} else {
			if (colisionObject(generic.getX() + valor +1, generic.getY())) {
				generic.setLocation(generic.getX(), generic.getY());
			} else {
					generic.setLocation(generic.getX() + valor, generic.getY());
			}
		}

	}

	public Posicao perseguir(JLabel fantasma, int valor) {
		Posicao p = new Posicao();
		boolean estadoColisaoY = false;
		boolean estadoColisaoX = false;

		if (!estadoColisaoX && fantasma.getX() != p1.getX()) {
			if (fantasma.getX() < p1.getX()) {
				if (colisionObject(fantasma.getX() + valor +1, fantasma.getY())) {
					p.setXps(fantasma.getX());
					//if (p1.getY() < 300) {
						if(!colisionObject(fantasma.getX(), fantasma.getY() + valor +1)) {
							p.setYps(fantasma.getY() + valor);
							estadoColisaoY = true;
						}
					//}
					/*else {
						if(!colisionObject(fantasma.getX(), fantasma.getY() - valor -1)) {
							p.setYps(fantasma.getY() - valor);
							estadoColisaoY = true;
						}
					}*/
						
				} else {
					p.setXps(fantasma.getX() + valor);
					p.setYps(fantasma.getY());
					estadoColisaoY = false;
				}
			} else {
				if (colisionObject(fantasma.getX() - valor -1, fantasma.getY())) {
					p.setXps(fantasma.getX());
					//if (p1.getY() < 300) {
						if(!colisionObject(fantasma.getX(), fantasma.getY() - valor -1)) {
							p.setYps(fantasma.getY() - valor);
							estadoColisaoY = true;
						}
					//}
					/*else {
						if(!colisionObject(fantasma.getX(), fantasma.getY() - valor -1)) {
							p.setYps(fantasma.getY() - valor);
							estadoColisaoY = true;
						}
					}*/
					
				} else {
					p.setXps(fantasma.getX() - valor);
					p.setYps(fantasma.getY());
					estadoColisaoY = false;
				}
			}
		}
		if (!estadoColisaoY && fantasma.getY() != p1.getY()) {
			if (fantasma.getY() < p1.getY()) {
				if (colisionObject(fantasma.getX(), fantasma.getY() + valor +1)) {
					p.setYps(fantasma.getY());
					//if (fantasma.getX() < p1.getX()) {
						if(!colisionObject(fantasma.getX() - valor -1, fantasma.getY())) {
							p.setXps(fantasma.getX() - valor);
							estadoColisaoX = true;
						}
					//}
					//else
					//	if(!colisionObject(fantasma.getX() - valor -1, fantasma.getY()))
					//		p.setXps(fantasma.getX() - valor);
				
				} else {
					p.setXps(fantasma.getX());
					p.setYps(fantasma.getY() + valor);
					estadoColisaoX = false;
				}
			} else {
				if (colisionObject(fantasma.getX(), fantasma.getY() - valor -1)) {
					p.setYps(fantasma.getY());
					//if (fantasma.getX() <= p1.getX()) {
						if(!colisionObject(fantasma.getX() + valor +1, fantasma.getY())) {
							p.setXps(fantasma.getX() + valor);
							estadoColisaoX = true;
						}
					//}
					//else
					//	if(!colisionObject(fantasma.getX() - valor -1, fantasma.getY()))
					//		p.setXps(fantasma.getX() - valor);
					
				} else {
					p.setXps(fantasma.getX());
					p.setYps(fantasma.getY() - valor);
					estadoColisaoX = false;
				}
			}
		}
		if(p.getXPs() <= 0)
			p.setXps(fantasma.getX());
		if(p.getYPs() <= 0)
			p.setYps(fantasma.getY());
		return p;
	}
	

	public Posicao correr(JLabel fantasma, int valor) {
		Posicao p = new Posicao();
		Rectangle r1 = new Rectangle(fantasma.getX(), fantasma.getY(), fantasma.getWidth(), fantasma.getHeight());
		Rectangle r2 = new Rectangle(p1.getX(), p1.getY(), p1.getWidth(), p1.getHeight());

		if (!r1.intersects(r2)) {
			if (fantasma.getX() < p1.getX()) {
				if (colisionObject(fantasma.getX() - valor -1, fantasma.getY())) {
					p.setXps(fantasma.getX());
					p.setYps(fantasma.getY());
				} else {
					p.setXps(fantasma.getX() - valor);
					p.setYps(fantasma.getY());
				}
			} else {
				if (colisionObject(fantasma.getX() + valor +1, fantasma.getY())) {
					p.setXps(fantasma.getX());
					p.setYps(fantasma.getY());
				} else {
					p.setXps(fantasma.getX() + valor);
					p.setYps(fantasma.getY());
				}
			}
			if (fantasma.getY() < p1.getY()) {
				if (colisionObject(fantasma.getX(), fantasma.getY() - valor -1)) {
					p.setXps(fantasma.getX());
					p.setYps(fantasma.getY());
				} else {
					p.setXps(fantasma.getX());
					p.setYps(fantasma.getY() - valor);
				}
			} else {
				if (colisionObject(fantasma.getX(), fantasma.getY() + valor +1)) {
					p.setXps(fantasma.getX());
					p.setYps(fantasma.getY());
				} else {
					p.setXps(fantasma.getX());
					p.setYps(fantasma.getY() + valor);
				}
			}
		}
		else {
			pontos+=400;
		}
		return p;
	}
	public int[] vetorRastro(int X, int Y) {
		int rastros[] = new int[4];
		Rectangle rect1 = new Rectangle(X, Y, 15, 20);
		for (int i = 0; i < 27; i++) {
			for (int j = 0; j < 20; j++) {
				Rectangle aux = new Rectangle(comida[i][j].cmd.getX(), comida[i][j].cmd.getY(),
						comida[i][j].cmd.getWidth(), comida[i][j].cmd.getHeight());
				if(rect1.intersects(aux)) {
					if(j-1 > 0)
						rastros[0] = comida[i][j-1].rastro;
					if(j+1 < 20)
						rastros[1] = comida[i][j+1].rastro;
					if(i-1 > 0)
						rastros[2] = comida[i-1][j].rastro;
					if(i+1 < 27)
						rastros[3] = comida[i+1][j].rastro;
				}
			}
		}
		return rastros;
		
		
	}

	public void addObj() {
		objetos.add(new JLabel(new ImageIcon("/Media/Images/horizontal.png")));
		objetos.add(new JLabel(new ImageIcon("/Media/Images//Media/Images//Media/Images/horizontal.png")));
		objetos.add(new JLabel(new ImageIcon("/Media/Images//Media/Images/vertical.png")));
		objetos.add(new JLabel(new ImageIcon("/Media/Images/vertical.png")));
		objetos.get(0).setBounds(0, 0, 810, 5);
		objetos.get(1).setBounds(0, 600, 810, 5);
		objetos.get(2).setBounds(0, 0, 5, 605);
		objetos.get(3).setBounds(805, 0, 5, 605);

		objetos.add(new JLabel(new ImageIcon("/Media/Gifs/bv.gif")));
		objetos.get(objetos.size() - 1).setBounds(31, 31, 5, 210);
		objetos.add(new JLabel(new ImageIcon("/Media/Gifs/bv.gif")));
		objetos.get(objetos.size() - 1).setBounds(271, 5, 5, 30);
		objetos.add(new JLabel(new ImageIcon("/Media/Gifs/bv.gif")));
		objetos.get(objetos.size() - 1).setBounds(541, 5, 5, 30);
		objetos.add(new JLabel(new ImageIcon("/Media/Gifs/bv.gif")));
		objetos.get(objetos.size() - 1).setBounds(271, 571, 5, 30);
		objetos.add(new JLabel(new ImageIcon("/Media/Gifs/bv.gif")));
		objetos.get(objetos.size() - 1).setBounds(541, 571, 5, 30);
		objetos.add(new JLabel(new ImageIcon("/Media/Gifs/bv.gif")));
		objetos.get(objetos.size() - 1).setBounds(776, 31, 5, 210);
		objetos.add(new JLabel(new ImageIcon("/Media/Gifs/bv.gif")));
		objetos.get(objetos.size() - 1).setBounds(31, 366, 5, 210);
		objetos.add(new JLabel(new ImageIcon("/Media/Gifs/bv.gif")));
		objetos.get(objetos.size() - 1).setBounds(776, 366, 5, 210);
		objetos.add(new JLabel(new ImageIcon("/Media/Gifs/bv.gif")));
		objetos.get(objetos.size() - 1).setBounds(61, 366, 5, 180);
		objetos.add(new JLabel(new ImageIcon("/Media/Gifs/bv.gif")));
		objetos.get(objetos.size() - 1).setBounds(746, 366, 5, 180);
		objetos.add(new JLabel(new ImageIcon("/Media/Gifs/bv.gif")));
		objetos.get(objetos.size() - 1).setBounds(61, 61, 5, 180);
		objetos.add(new JLabel(new ImageIcon("/Media/Gifs/bv.gif")));
		objetos.get(objetos.size() - 1).setBounds(746, 61, 5, 180);
		objetos.add(new JLabel(new ImageIcon("/Media/Gifs/bv.gif")));
		objetos.get(objetos.size() - 1).setBounds(361, 66, 5, 210);
		objetos.add(new JLabel(new ImageIcon("/Media/Gifs/bv.gif")));
		objetos.get(objetos.size() - 1).setBounds(361, 306, 5, 210);
		objetos.add(new JLabel(new ImageIcon("/Media/Gifs/bv.gif")));
		objetos.get(objetos.size() - 1).setBounds(451, 66, 5, 210);
		objetos.add(new JLabel(new ImageIcon("/Media/Gifs/bv.gif")));
		objetos.get(objetos.size() - 1).setBounds(451, 306, 5, 210);

		objetos.add(new JLabel(new ImageIcon("/Media/Gifs/bh.gif")));
		objetos.get(objetos.size() - 1).setBounds(31, 31, 210, 5);
		objetos.add(new JLabel(new ImageIcon("/Media/Gifs/bh.gif")));
		objetos.get(objetos.size() - 1).setBounds(571, 31, 210, 5);
		objetos.add(new JLabel(new ImageIcon("/Media/Gifs/bh.gif")));
		objetos.get(objetos.size() - 1).setBounds(36, 571, 210, 5);
		objetos.add(new JLabel(new ImageIcon("/Media/Gifs/bh.gif")));
		objetos.get(objetos.size() - 1).setBounds(571, 571, 210, 5);
		objetos.add(new JLabel(new ImageIcon("/Media/Gifs/bh.gif")));
		objetos.get(objetos.size() - 1).setBounds(301, 31, 210, 5);
		objetos.add(new JLabel(new ImageIcon("/Media/Gifs/bh.gif")));
		objetos.get(objetos.size() - 1).setBounds(301, 571, 210, 5);
		objetos.add(new JLabel(new ImageIcon("/Media/Gifs/bh.gif")));
		objetos.get(objetos.size() - 1).setBounds(126, 151, 210, 5);
		objetos.add(new JLabel(new ImageIcon("/Media/Gifs/bh.gif")));
		objetos.get(objetos.size() - 1).setBounds(481, 151, 210, 5);
		objetos.add(new JLabel(new ImageIcon("/Media/Gifs/bh.gif")));
		objetos.get(objetos.size() - 1).setBounds(126, 241, 210, 5);
		objetos.add(new JLabel(new ImageIcon("/Media/Gifs/bh.gif")));
		objetos.get(objetos.size() - 1).setBounds(481, 241, 210, 5);
		objetos.add(new JLabel(new ImageIcon("/Media/Gifs/bh.gif")));
		objetos.get(objetos.size() - 1).setBounds(126, 361, 210, 5);
		objetos.add(new JLabel(new ImageIcon("/Media/Gifs/bh.gif")));
		objetos.get(objetos.size() - 1).setBounds(481, 361, 210, 5);
		objetos.add(new JLabel(new ImageIcon("/Media/Gifs/bh.gif")));
		objetos.get(objetos.size() - 1).setBounds(126, 451, 210, 5);
		objetos.add(new JLabel(new ImageIcon("/Media/Gifs/bh.gif")));
		objetos.get(objetos.size() - 1).setBounds(481, 451, 210, 5);
		objetos.add(new JLabel(new ImageIcon("/Media/Gifs/bh.gif")));
		objetos.get(objetos.size() - 1).setBounds(126, 271, 210, 5);
		objetos.add(new JLabel(new ImageIcon("/Media/Gifs/bh.gif")));
		objetos.get(objetos.size() - 1).setBounds(481, 271, 210, 5);
		objetos.add(new JLabel(new ImageIcon("/Media/Gifs/bh.gif")));
		objetos.get(objetos.size() - 1).setBounds(126, 301, 210, 5);
		objetos.add(new JLabel(new ImageIcon("/Media/Gifs/bh.gif")));
		objetos.get(objetos.size() - 1).setBounds(481, 301, 210, 5);
		objetos.add(new JLabel(new ImageIcon("/Media/Gifs/bh.gif")));
		objetos.get(objetos.size() - 1).setBounds(126, 331, 210, 5);
		objetos.add(new JLabel(new ImageIcon("/Media/Gifs/bh.gif")));
		objetos.get(objetos.size() - 1).setBounds(481, 331, 210, 5);
		
		for (int i = 0; i < objetos.size(); i++) {
			this.add(objetos.get(i));
		}
	}

	public void iniciarP() {
		p1.setBounds(posInicialX, posInicialY, 15, 16);
		f1.setBounds(220, 130, 15, 16);
		f2.setBounds(580, 130, 15, 16);
		f3.setBounds(220, 370, 15, 16);
		f4.setBounds(580, 370, 15, 16);
		
		this.add(p1);
		this.add(f1);
		this.add(f2);
		this.add(f3);
		this.add(f4);
	}

	public void screen() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocation(300, 150);
		this.setSize(816, 660);
		this.setVisible(true);
		this.setLayout(null);
		this.addKeyListener(this);
	}

	public void drawP() {
		vida.setBounds(680, 603, 200, 30);
		vida.setForeground(Color.GREEN);
		vida.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
		pontuacao.setBounds(0, 600, 550, 30);
		pontuacao.setForeground(Color.WHITE);
		pontuacao.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
		this.add(pontuacao);
		this.add(vida);
		getContentPane().setBackground(Color.BLACK);
	}

	public void gameOver() {
		int total = pontos;
		p1.setLocation(0,0);
		for (int i = 0; i < 27; i++) {
			for (int j = 0; j < 20; j++) {
				comida[i][j].cmd.setVisible(false);
			}
		}
		for (int i = 0; i < objetos.size(); i++) {
			objetos.get(i).setVisible(false);
		}
		p1.setVisible(false);
		f1.setVisible(false);
		f2.setVisible(false);
		f3.setVisible(false);
		f4.setVisible(false);
		pontuacao.setVisible(false);

		JLabel go = new JLabel("GAME OVER");
		JLabel aux = new JLabel("Pontuacao final: " + total);
		go.setFont(new Font("Comic Sans MS", Font.BOLD, 72));
		go.setBounds(180, 50, 550, 500);
		aux.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
		aux.setBounds(190, 100, 550, 500);
		go.setForeground(Color.WHITE);
		aux.setForeground(Color.WHITE);
		this.add(go);
		this.add(aux);
		this.repaint();

	}
	public static AudioClip music;
	public static AudioClip death;
	public static AudioClip supercomida;
	public static AudioClip gameover;
	@SuppressWarnings("deprecation")
	public static void tocador (){
		try{
			music = Applet.newAudioClip(new File("/Media/Sounds/song.wav").toURL());
			death = Applet.newAudioClip(new File("/Media/Sounds/death.wav").toURL());
			supercomida = Applet.newAudioClip(new File("/Media/Sounds/supercomida.wav").toURL());
			gameover = Applet.newAudioClip(new File("/Media/Sounds/gameover.wav").toURL());
			
			}
		catch (MalformedURLException e) {  
			System.out.println("Erro. Verifique o diretorio de sons");  
			}  
	}
	public void tocarPassou() {  
		music.loop();
	}
	
	public void tocarGO() {  
		gameover.loop();
	}
	
	public void drawComida() {
		for (int i = 0; i < 27; i++) {
			for (int j = 0; j < 20; j++) {
				comida[i][j] = new Comida();
				if (i > 0 && i % 7 == 0 && j % 4 == 0) {
					comida[i][j].Food = true;
					comida[i][j].cmd = new JLabel(new ImageIcon("/Media/Gifs/super.gif"));
					comida[i][j].cmd.setBounds(i * 30 + 10, j * 30 + 10, 15, 15);
				} else {
					comida[i][j].cmd = new JLabel(new ImageIcon("/Media/Images/comida.png"));
					comida[i][j].cmd.setBounds(i * 30 + 15, j * 30 + 15, 10, 10);
				}

				this.add(comida[i][j].cmd);
			}
		}
	}
	
	public void rodarJogo() {
		Timer mm = new Timer(true);
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				if (vidas == 3) {
				} else if (vidas == 2)
					vida.setForeground(Color.YELLOW);
				else if (vidas == 1)
					vida.setForeground(Color.RED);
				else {
					vida.setForeground(Color.WHITE);
					vida.setText("MORTO");
					gameOver();
				}
				if(superFood > 0)
					superContador--;
				if(superContador == 0) {
					superFood = 0;
					superContador = 900;
				}
				
				for (int i = 0; i < 27; i++) {
					for (int j = 0; j < 20; j++) {
						if(comida[i][j].rastro > 0)
							comida[i][j].rastro--;
					}
				}
				if(superFood == 0) {
					f1.setIcon(F1);
					f2.setIcon(F2);
					f3.setIcon(F3);
					f4.setIcon(F4);
					
					int sortear = r.nextInt(100);
					if(sortear < 5) {
						andar(f1, 2);
						andar(f2, 2);
						andar(f3, 2);
						andar(f4, 2);
					}
					else {
					
						Posicao p = perseguir(f1, 1);
						f1.setLocation(p.getXPs(), p.getYPs());
						p = perseguir(f2, 1);
						f2.setLocation(p.getXPs(), p.getYPs());
						p = perseguir(f3, 1);
						f3.setLocation(p.getXPs(), p.getYPs());
						p = perseguir(f4, 1);
						f4.setLocation(p.getXPs(), p.getYPs());
					}
					boolean morreu = false;
					if (colisionGhost(f1.getX(), f1.getY()) || 
						colisionGhost(f2.getX(), f2.getY()) || 
						colisionGhost(f3.getX(), f3.getY()) || 
						colisionGhost(f4.getX(), f4.getY())) morreu = true;
					
					if(morreu) {
						death.play();
						vida.setText("Vidas: " + --vidas);
						iniciarP();
						for (int i = 0; i < 27; i++) {
							for (int j = 0; j < 20; j++) {
								comida[i][j].rastro = 0;						}	
						}
					}
				}
				else {
					f1.setIcon(C);
					f2.setIcon(C);
					f3.setIcon(C);
					f4.setIcon(C);
					
					Posicao p;
					int fugir = r.nextInt(10);
					if(fugir < 5) {
						p = correr(f1, 1);
						if(p.getXPs() <= 0 || p.getYPs() <= 0) {
							f1.setVisible(false);
							f1.setLocation(0,0);
							TimerTask taskm = new TimerTask() {
								@Override
								public void run() {
									f1.setBounds(220, 130, 15, 20);
									f1.setVisible(true);
								}
							};
							mm.schedule(taskm, 5000);
						}
						else f1.setLocation(p.getXPs(), p.getYPs());
					}
					else
						andar(f1, 1);
					
					if(fugir < 5) {
						p = correr(f2, 1);
						if(p.getXPs() <= 0 || p.getYPs() <= 0) {
							f2.setVisible(false);
							f2.setLocation(0,0);
							TimerTask taskm = new TimerTask() {
								@Override
								public void run() {
									f2.setBounds(580, 130, 15, 20);
									f2.setVisible(true);
								}
							};
							mm.schedule(taskm, 5000);
						}
						else f2.setLocation(p.getXPs(), p.getYPs());
						//f2.setVisible(false);
					}
					else
						andar(f2, 1);
					
					if(fugir < 5) {
						p = correr(f3, 1);
						if(p.getXPs() <= 0 || p.getYPs() <= 0) {
							f3.setVisible(false);
							f3.setLocation(0,0);
							TimerTask taskm = new TimerTask() {
								@Override
								public void run() {
									f3.setBounds(220, 370, 15, 20);
									f3.setVisible(true);
								}
							};
							mm.schedule(taskm, 5000);
						}
						else f3.setLocation(p.getXPs(), p.getYPs());
					}
					else
						andar(f3, 1);
					
					if(fugir < 5) {
						p = correr(f4, 1);
						if(p.getXPs() <= 0 || p.getYPs() <= 0) {
							f4.setVisible(false);
							f4.setLocation(0,0);
							TimerTask taskm = new TimerTask() {
								@Override
								public void run() {
									f4.setBounds(580, 370, 15, 20);
									f4.setVisible(true);
								}
							};
							mm.schedule(taskm, 5000);
						}
						else f4.setLocation(p.getXPs(), p.getYPs());
					}
					else
						andar(f4, 1);
				}
			}
		};
		Timer t = new Timer();
		t.scheduleAtFixedRate(task, 1000, 10);
	}

}
