import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Game extends Canvas implements Runnable, MouseListener{

	public static final int Width = 300, Height = 300;
	
	public int PLAYER = 1, OPONENTE = -1;
	
	public int CURRENT = PLAYER;///////////////
	
	public BufferedImage X_SPRITE, O_SPRITE;
	public int[][] TABULEIRO = new int[3][3];
	
	public int mx, my;
	
	public boolean Pressed = false;
	
	public UI ui;
	
	public InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("PixelFonte.ttf");
	public static Font pixelfonte;
	
	public Game() {
		this.setPreferredSize(new Dimension(Width,Height));
		this.addMouseListener(this);
		
		try {
			X_SPRITE = ImageIO.read(getClass().getResource("/X.png"));
			O_SPRITE = ImageIO.read(getClass().getResource("/O.png"));
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		ui = new UI();
		
		reset();
		
		try {
			pixelfonte = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(80f);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void reset() {
		for(int xx = 0; xx < TABULEIRO.length; xx++) {
			for(int yy = 0; yy < TABULEIRO.length; yy++) {
				TABULEIRO[xx][yy] = 0;
			}
		}
	}
	
	public int checkVictory() {
		//Player
		if(TABULEIRO[0][0] == PLAYER && TABULEIRO[1][0] == PLAYER && TABULEIRO [2][0] == PLAYER) {
			return PLAYER;
		}
		if(TABULEIRO[0][1] == PLAYER && TABULEIRO[1][1] == PLAYER && TABULEIRO [2][1] == PLAYER) {
			return PLAYER;
		}
		if(TABULEIRO[0][2] == PLAYER && TABULEIRO[1][2] == PLAYER && TABULEIRO [2][2] == PLAYER) {
			return PLAYER;
		}
		/////////////////////////////////////////////////////////////////////////////////////////////////
		if(TABULEIRO[0][0] == PLAYER && TABULEIRO[0][1] == PLAYER && TABULEIRO [0][2] == PLAYER) {
			return PLAYER;
		}
		if(TABULEIRO[1][0] == PLAYER && TABULEIRO[1][1] == PLAYER && TABULEIRO [1][2] == PLAYER) {
			return PLAYER;
		}
		if(TABULEIRO[2][0] == PLAYER && TABULEIRO[2][1] == PLAYER && TABULEIRO [2][2] == PLAYER) {
			return PLAYER;
		}
		//////////////////////////////////////////////////////////////////////////////////////////////////
		if(TABULEIRO[0][0] == PLAYER && TABULEIRO[1][1] == PLAYER && TABULEIRO [2][2] == PLAYER) {
			return PLAYER;
		}
		if(TABULEIRO[2][0] == PLAYER && TABULEIRO[1][1] == PLAYER && TABULEIRO [0][2] == PLAYER) {
			return PLAYER;
		}
		
		///////////////////////////////////////    OPONENTE   //////////////////////////////////////////////////////
		if(TABULEIRO[0][0] == OPONENTE && TABULEIRO[1][0] == OPONENTE && TABULEIRO [2][0] == OPONENTE) {
			return OPONENTE;
		}
		if(TABULEIRO[0][1] == OPONENTE && TABULEIRO[1][1] == OPONENTE && TABULEIRO [2][1] == OPONENTE) {
			return OPONENTE;
		}
		if(TABULEIRO[0][2] == OPONENTE && TABULEIRO[1][2] == OPONENTE && TABULEIRO [2][2] == OPONENTE) {
			return OPONENTE;
		}
		/////////////////////////////////////////////////////////////////////////////////////////////////
		if(TABULEIRO[0][0] == OPONENTE && TABULEIRO[0][1] == OPONENTE && TABULEIRO [0][2] == OPONENTE) {
			return OPONENTE;
		}
		if(TABULEIRO[1][0] == OPONENTE && TABULEIRO[1][1] == OPONENTE && TABULEIRO [1][2] == OPONENTE) {
			return OPONENTE;
		}
		if(TABULEIRO[2][0] == OPONENTE && TABULEIRO[2][1] == OPONENTE && TABULEIRO [2][2] == OPONENTE) {
			return OPONENTE;
		}
		//////////////////////////////////////////////////////////////////////////////////////////////////
		if(TABULEIRO[0][0] == OPONENTE && TABULEIRO[1][1] == OPONENTE && TABULEIRO [2][2] == OPONENTE) {
			return OPONENTE;
		}
		if(TABULEIRO[2][0] == OPONENTE && TABULEIRO[1][1] == OPONENTE && TABULEIRO [0][2] == OPONENTE) {
			return OPONENTE;
		}
		
		int filled = 0;
		//Deu velha
		for(int xx = 0; xx < TABULEIRO.length; xx++) {
			for(int yy = 0; yy < TABULEIRO.length; yy++) {
				if(TABULEIRO[xx][yy] != 0) {
					filled++;
				}
			}
		}
		
		if(filled == TABULEIRO.length * TABULEIRO[0].length) {
			return 0;
		}
		
		
		//tá rodando
		return -10;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void tick() {
		if(CURRENT == PLAYER) {
			if(Pressed) {
				Pressed = false;
				mx/=100;
				my/=100;
				if(TABULEIRO[mx][my] == 0) {
					TABULEIRO[mx][my] = PLAYER;
					CURRENT = OPONENTE;
				}
			}
		}else if(CURRENT == OPONENTE) {
			
			for(int xx = 0; xx<TABULEIRO.length; xx++) {
				for(int yy = 0; yy<TABULEIRO.length; yy++) {
					if(TABULEIRO[xx][yy] == 0) {
						Node bestMove = getBestMove(xx,yy,0,OPONENTE);
						
						TABULEIRO[bestMove.x][bestMove.y] = OPONENTE;
						
						CURRENT = PLAYER;
						return;
					}
				}
			}
		}
		
		
		if(checkVictory() == PLAYER) {
			ui.tick();
		}else if(checkVictory() == OPONENTE) {
			ui.tick();
		}else if(checkVictory() == 0) {
			ui.tick();
		}
		if(ui.posY >= Height+70) {
			reset();
			ui.posY = -70;
			ui.angle = -150;
		}
	}
	
	public Node getBestMove(int x, int y, int depth, int turno) {
		if(checkVictory() == PLAYER) {
			return new Node(x, y, depth-10, depth);
		}else if(checkVictory() == OPONENTE) {
			return new Node(x, y, 10-depth, depth);
		}else if(checkVictory() == 0) {
			return new Node(x, y, 0, depth);
		}
		
		List<Node> nodes = new ArrayList<Node>();
		
		for(int xx = 0; xx<TABULEIRO.length; xx++) {
			for(int yy = 0; yy<TABULEIRO.length; yy++) {
				
				if(TABULEIRO[xx][yy] == 0) {
					Node node;
					if(turno == PLAYER) {
						TABULEIRO[xx][yy] = PLAYER;
						node = getBestMove(xx, yy, depth+1, OPONENTE);
						TABULEIRO[xx][yy] = 0;
					}else {
						TABULEIRO[xx][yy] = OPONENTE;
						node = getBestMove(xx, yy, depth+1, PLAYER);
						TABULEIRO[xx][yy] = 0;
					}
					nodes.add(node);
				}
			}
		}
		
		Node finalNode = nodes.get(0);
		for(int i = 0; i<nodes.size(); i++) {
			Node n = nodes.get(i);
			if(turno == PLAYER) {
				if(n.score > finalNode.score) {
					finalNode = n;
				}
			}else{
				if(n.score < finalNode.score) {
					finalNode = n;
				}
			}
		}
		
		return finalNode;
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, Width, Height);
		
		for(int xx = 0; xx<TABULEIRO.length; xx++) {
			for(int yy = 0; yy<TABULEIRO.length; yy++) {
				g.setColor(Color.black);
				g.drawRect(xx*100, yy*100, 100, 100);
				
				if(TABULEIRO[xx][yy] == PLAYER) {
					g.drawImage(O_SPRITE, xx*100+5, yy*100+5,90,90, null);
				}else if(TABULEIRO[xx][yy] == OPONENTE) {
					g.drawImage(X_SPRITE, xx*100+5, yy*100+5,90,90, null);
				}
			}
		}
		
		if(checkVictory() == PLAYER) {
			ui.renderOWIN(g);
		}else if(checkVictory() == OPONENTE) {
			ui.renderXWIN(g);
		}else if(checkVictory() == 0) {
			ui.renderEmpate(g);
		}
		
		g.dispose();
		bs.show();
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		JFrame frame = new JFrame();
		frame.setTitle("Jogo da Velha");
		frame.add(game);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setVisible(true);
		new Thread(game).start();
	}

	@Override
	public void run() {
		double fps = 60.0;
		while(true) {
			tick();
			render();
			try {
				Thread.sleep((int)(1000/fps));
			} catch (InterruptedException e) {}
		}
		
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Pressed = true;
		mx = e.getX();
		my = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {

		
	}

	@Override
	public void mouseEntered(MouseEvent e) {

		
	}

	@Override
	public void mouseExited(MouseEvent e) {

		
	}
	
}
