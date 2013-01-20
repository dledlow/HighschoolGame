	import java.awt.Color;
	import java.awt.Graphics;
	import java.awt.Polygon;
public class winBoom extends Thing{
	

		double angle = Math.random()*2*Math.PI;
		int health = 100;
		double speed = Math.random()*3+0.1;
		double xspeed = Math.cos(angle-Math.PI/2) * speed;
		double yspeed = Math.sin(angle-Math.PI/2) * speed;
		Color wut = new Color((int)(Math.random()*250),(int)(Math.random()*72),(int)(Math.random()*125));
		int shape = (int)Math.round(Math.random()*3);
		
		public winBoom(double x, double y) {
			super(x, y);
			
			// TODO Auto-generated constructor stub
		}
		
		public void updatePosition()
		{
			
			x += xspeed;
			y += yspeed;
			health--;
			wrap();
		}

		public void makePoly()
		{
			poly.reset();
			if(shape == 0)
			{
				//heart
				
				poly.addPoint((int)x,(int) y-3);
				poly.addPoint((int)x + 2,(int) y - 5);
				poly.addPoint((int)x + 4,(int) y - 5);
				poly.addPoint((int)x + 5,(int) y - 4);
				poly.addPoint((int)x + 5,(int) y - 2);
				poly.addPoint((int)x + 3,(int) y + 1);
				poly.addPoint((int)x,(int) y + 4);
				poly.addPoint((int)x - 3,(int) y + 1);
				poly.addPoint((int)x - 5,(int) y - 2);
				poly.addPoint((int)x - 5,(int) y - 4);
				poly.addPoint((int)x - 4,(int) y - 5);
				poly.addPoint((int)x - 2,(int) y - 5);
				
			}
			else if(shape == 1)
			{
				//diamond
				
				poly.addPoint((int)x,(int) y-5);
				poly.addPoint((int)x + 4,(int) y);
				poly.addPoint((int)x,(int) y+5);
				poly.addPoint((int)x - 4,(int) y);
				
			}
			else if (shape == 2)
			{
				//spade
				
				poly.addPoint((int)x,(int) y-5);
				poly.addPoint((int)x + 2,(int) y - 3);
				poly.addPoint((int)x + 4,(int) y - 2);
				poly.addPoint((int)x + 4,(int) y);
				poly.addPoint((int)x + 2,(int) y + 2);
				poly.addPoint((int)x,(int) y);
				poly.addPoint((int)x+2,(int) y+4);
				poly.addPoint((int)x-2,(int) y+4);
				poly.addPoint((int)x,(int) y);
				poly.addPoint((int)x - 2,(int) y + 2);
				poly.addPoint((int)x - 4,(int) y);
				poly.addPoint((int)x - 4,(int) y - 2);
				poly.addPoint((int)x - 2,(int) y - 3);
				
			}
			else
			{
				//club
				
				poly.addPoint((int)x,(int) y-5);
				poly.addPoint((int)x + 2,(int) y-3);
				poly.addPoint((int)x,(int) y);
				poly.addPoint((int)x + 3,(int) y - 2);
				poly.addPoint((int)x + 5,(int) y);
				poly.addPoint((int)x + 3,(int) y + 2);
				poly.addPoint((int)x,(int) y);
				poly.addPoint((int)x+2,(int) y+4);
				poly.addPoint((int)x-2,(int) y+4);
				poly.addPoint((int)x,(int) y);
				poly.addPoint((int)x - 3,(int) y + 2);
				poly.addPoint((int)x - 5,(int) y);
				poly.addPoint((int)x - 3,(int) y - 2);
				poly.addPoint((int)x,(int) y);
				poly.addPoint((int)x - 2,(int) y-3);
				
			}
		}
		
		public void draw(Graphics g)
		{
			g.setColor(wut);
			makePoly();
			g.fillPolygon(poly);
		}
		
		public String toString()
		{
			return Integer.toString(health);
			
		}
	

}
