import java.awt.Color;
import java.awt.Graphics;


public class GridPoint extends Thing
{
	double maxStretch = 100;
	double netForceX = 0;
	double netForceY = 0;
	double trueX = 0;
	double trueY = 0;
	
	public GridPoint(double x2, double y2) 
	{
		super(x2,y2);
		trueX = x2;
		trueY = y2;
	}

	public void draw(Graphics g)
	{
		makePoly();
		g.setColor(Color.darkGray);
		g.drawPolygon(poly);
	}
	public void updatePosition()
	{
		//if(Math.sqrt(Math.pow(x-trueX+netForceY, 2) + Math.pow(y-trueY + netForceY, 2)) < maxStretch || Math.sqrt(Math.pow(x-trueX+netForceY, 2) + Math.pow(y-trueY + netForceY, 2)) > -maxStretch)
		{
			//x += netForceX;
			//y += netForceY;
		}
		x = trueX + netForceX;
		y = trueY + netForceY;
		
		
		netForceX = 0;
		netForceY = 0;
	}
}
