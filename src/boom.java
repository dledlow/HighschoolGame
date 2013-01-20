import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;


public class boom extends Thing{

	
	
	double angle = Math.random()*2*Math.PI;
	int health = 150;
	double speed = Math.random()*4;
	
	Color wut = new Color((int)(Math.random()*100)+150,(int)(Math.random()*175),(int)(Math.random()*50));
	int shape = (int)Math.round(Math.random()*3);
	boolean doWrap = true;
	
	public boom(double x, double y) {
		super(x, y);
		mass = 125;
		xSpeed = Math.cos(angle-Math.PI/2) * speed;
		ySpeed = Math.sin(angle-Math.PI/2) * speed;
		// TODO Auto-generated constructor stub
	}
	
	public void updatePosition()
	{
		
		
		x += xSpeed;
		y += ySpeed;
		health--;
		if(doWrap)
			wrap();
	}

	public void makePoly()
	{
		poly.reset();
		if(shape == 0)
		{

			poly.addPoint((int)x + 3,(int) y-3);
			poly.addPoint((int)x - 3,(int) y-3);
			poly.addPoint((int)x,(int) y);
			
		}
		else if(shape == 1)
		{
			
			
			poly.addPoint((int)x + 3,(int) y-3);
			poly.addPoint((int)x - 3,(int) y-3);
			poly.addPoint((int)x - 3,(int) y + 3);
			poly.addPoint((int)x + 3,(int) y + 3);
			
		}
		else
		{
			
			
			poly.addPoint((int)x + 3,(int) y-3);
			poly.addPoint((int)x - 3,(int) y-3);
			poly.addPoint((int)x + 3,(int) y + 3);
			
			
		}
		
	}
	
	public void draw(Graphics g)
	{
		g.setColor(wut);
		makePoly();
		g.drawPolygon(poly);
	}
	
	public String toString()
	{
		return Integer.toString(health);
		
	}
}
