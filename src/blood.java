import java.awt.Color;
import java.awt.Graphics;


public class blood extends boom
{
	
	int size = (int) Math.round(Math.random()*3+1);
	
	
	
	public blood(double x, double y, Color col) {
		super(x, y);
		wut = col;
		// TODO Auto-generated constructor stub
	}

	
	
	public void makePoly()
	{
		poly.reset();
		for(int i = 0;i<360;i+=6)
        {
            poly.addPoint((int) ((int)Math.round(size * Math.cos(i*Math.PI/180)) + x + size),(int)(y + size + Math.round(size * Math.sin(i*Math.PI/180))));
        }
	}
}
