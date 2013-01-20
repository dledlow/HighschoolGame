import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;


public class Bullet extends boom{

	
	double speed = 5;
	
	
	
	
	public Bullet(double x, double y,double angleGet) {
		super(x,y);
		health = 150;
		angle = angleGet;
		mass = 10;
		xSpeed = Math.cos(angle-Math.PI/2) * speed;
		ySpeed = Math.sin(angle-Math.PI/2) * speed;
		wut = new Color((int)(Math.random()*50),(int)(Math.random()*200),(int)(Math.random()*255));
		
		makePoly();
		// TODO Auto-generated constructor stub
	}
	
	public void makePoly()
	{
		poly.reset();
		poly.addPoint((int)x+4,(int) y);
		poly.addPoint((int)x,(int) y+4);
		poly.addPoint((int)x-4,(int) y);
		poly.addPoint((int)x,(int) y-4);
	}
	
	public boolean collide(Thing ob2)
	{
		for(int i = 0; i < ob2.poly.xpoints.length; i++)
		{
			if(poly.contains(ob2.poly.xpoints[i],ob2.poly.ypoints[i]))
			{
				health = 0;
				return true;
			}
		}
		return false;
	}

	public void draw(Graphics g)
	{
		//g.setColor(wut);
		makePoly();
		
		
		
		g.drawPolygon(poly);
	}
	
	
	public void updatePosition()
	{
		
		x += xSpeed;
		y += ySpeed;
		
		
		health--;
		if(doWrap)
			wrap();
	}
	
}
