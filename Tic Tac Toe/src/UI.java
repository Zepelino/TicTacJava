import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class UI {

	private String O = "Você Ganhou!!!!", X = "Você Perdeu!!!", tie = "Velha ;n;";
	
	public int posY = -70, angle=-150;
	
	public void tick() {
		angle+=1.5;
		if(posY < Game.Height+70)
			posY+=2;
	}
	
	public void renderEmpate(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.rotate(Math.toRadians(angle), Game.Width/2-(g.getFontMetrics().stringWidth(tie)/2),posY );
		g2.setColor(Color.red);
		g2.setFont(Game.pixelfonte);
		g2.drawString(tie, Game.Width/2-(g.getFontMetrics().stringWidth(tie)/2), posY);
		g2.rotate(Math.toRadians(-angle), Game.Width/2-(g.getFontMetrics().stringWidth(tie)/2),posY );
	}
	
	public void renderXWIN(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.rotate(Math.toRadians(-angle), Game.Width/2-(g.getFontMetrics().stringWidth(X)/2),posY );
		g.setColor(Color.red);
		g.setFont(Game.pixelfonte);
		g.drawString(X, Game.Width/2-(g.getFontMetrics().stringWidth(X)/2), posY);
		g2.rotate(Math.toRadians(angle), Game.Width/2-(g.getFontMetrics().stringWidth(X)/2),posY );
	}
	
	public void renderOWIN(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.rotate(Math.toRadians(angle), Game.Width/2-(g.getFontMetrics().stringWidth(O)/2),posY );
		g2.setColor(Color.red);
		g2.setFont(Game.pixelfonte);
		g2.drawString(O, Game.Width/2-(g.getFontMetrics().stringWidth(O)/2), posY);
		g2.rotate(Math.toRadians(-angle), Game.Width/2-(g.getFontMetrics().stringWidth(O)/2),posY );
	}
	
	
}
