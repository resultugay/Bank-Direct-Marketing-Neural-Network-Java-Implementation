package bank_marketing;

import java.awt.Graphics;

import javax.swing.JPanel;

public class animation extends JPanel{
	JPanel animation_panel;
	public animation(JPanel animation_panel){
		this.animation_panel = animation_panel;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);	
		
		   for(int i = 0 ; i < 3; i++){			   
				drawCircle(g, 100,600/10*i*2+200, 600/17);
				 for(int j = 0 ; j < 10 ; j++){
					 g.drawLine(110, 600/10*i*2+200,435, 600/10*j+20);
					 for(int k = 0 ; k < 1 ; k++){
						 g.drawLine(465,  600/10*j+20,650,300);						 
					 }
				 }
		   }
		
		   for(int i = 0 ; i < 10 ; i++){
			drawCircle(g, 450,600/10*i+20, 600/40);
		   }
		   for(int i = 0 ; i < 1 ; i++){
				drawCircle(g, 650,300, 600/40);
		}
		   
		   
	}
		
		// Convenience method to draw from center with radius
		public void drawCircle(Graphics cg, int xCenter, int yCenter, int r) {
		cg.drawOval(xCenter-r, yCenter-r, 2*r, 2*r);
		
		}//end drawCircle
	
}
