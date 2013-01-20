import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;


public class Button extends Thing
{
	
	int width = 0;
	int height = 0;
	String name;
	
	boolean pressed = false;
	
	public Button(double x, double y,int w,int h,String txt) {
		super(x, y);
		width = w;
		height = h;
		name  = txt;
		// TODO Auto-generated constructor stub
	}

	public void draw(Graphics g)
	{
		makePoly();
		// Find the size of string s in font f in the current Graphics context g.
		FontMetrics fm   = g.getFontMetrics(g.getFont());
		java.awt.geom.Rectangle2D rect = fm.getStringBounds(name, g);

		int textHeight = (int)(rect.getHeight()); 
		int textWidth  = (int)(rect.getWidth());
		int panelHeight= height;
		int panelWidth = width;

		// Center text horizontally and vertically
		int x2 = (panelWidth  - textWidth)  / 2;
		int y2 = (panelHeight - textHeight) / 2  + fm.getAscent();

		if(pressed)
		{
			g.setColor(Color.GREEN);
			g.fillPolygon(poly);
			g.setColor(Color.black);
			g.drawString(name, (int)(x2+x-width/2),(int)(y2+y-height/2));  // Draw the string.
		}
		else
		{
			g.setColor(Color.GREEN);
			g.drawPolygon(poly);
			g.drawString(name, (int)(x2+x-width/2),(int)(y2+y-height/2));  // Draw the string.
		}
		
		
		
		g.drawString(name, (int)(x2+x-width/2),(int)(y2+y-height/2));  // Draw the string.
		
	}
	
	
	public void makePoly()
	{
		poly.reset();
		poly.addPoint((int)x-width/2,(int) y-height/2);
		poly.addPoint((int)x+width/2,(int) y-height/2);
		poly.addPoint((int)x+width/2,(int) y+height/2);
		poly.addPoint((int)x-width/2,(int) y+height/2);
		
	}
}
