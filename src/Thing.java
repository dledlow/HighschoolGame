import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;


public class Thing 
{
	int mass = 30;
	double netForceX = 0;
	double netForceY = 0;
	double trueX = 0;
	double trueY = 0;
	double x,y;
	static int width = 1200,height = 900;
	double xSpeed,ySpeed;
	Polygon poly = new Polygon();
	
	public Thing(double x2,double y2)
	{
		x = x2;
		y = y2;
		
	}
	
	public void makePoly()
	{
		poly.reset();
		poly.addPoint((int)x, (int)y);
		poly.addPoint((int)x, (int)y);
		poly.addPoint((int)x, (int)y);
	}
	
	public void draw(Graphics g)
	{
		makePoly();
		g.setColor(Color.magenta);
		g.drawPolygon(poly);
	}
	
	public boolean collide(Thing ob2)
	{
		for(int i = 0; i < ob2.poly.xpoints.length; i++)
		{
			if(poly.contains(ob2.poly.xpoints[i],ob2.poly.ypoints[i]))
			{
				return true;
			}
		}
		return false;
	}
	
	public void wrap()
	{
		
		if(x < 0)
		{
			x = width;
		}
		else if(x > width)
		{
			x = 0;
		}
		else if(y < 0)
		{
			y = height;
		}
		else if(y > height)
		{
			y = 0;
		}
	}
	
	public void updatePosition()
	{
		x += xSpeed;
		if((xSpeed > 0 && xSpeed < 0.1)||(xSpeed < 0 && xSpeed > -0.1))
		{
			xSpeed = 0;
		}
		if((ySpeed > 0 && ySpeed < 0.1)||(ySpeed < 0 && ySpeed > -0.1))
		{
			ySpeed = 0;
		}
		
		if(xSpeed > 0)
		{
			xSpeed -= 0.05;
		}
		else if(xSpeed < 0)
		{
			xSpeed += 0.05;
		}
		
		
		y += ySpeed;
		if(ySpeed > 0)
		{
			ySpeed -= 0.05;
		}
		if(ySpeed < 0)
		{
			ySpeed += 0.05;
		}
		
		wrap();
	}
}
