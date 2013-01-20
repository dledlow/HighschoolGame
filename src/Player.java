import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.ArrayList;


public class Player extends Gravitron{

	boolean invincible = false;
	int health = 100;
	int maxHealth = 100;
	int thrust = 7;
	int mass = 30;
	int invuln = 0;
	boolean exploded = false;
	
	Color shpColor = new Color(0);
	
	ArrayList<boom> bulletList = new ArrayList<boom>();
	boolean up,down,left,right;
	
	
	public Player(int xpos, int ypos) 
	{
		super(xpos, ypos);
		// TODO Auto-generated constructor stub
	}
	
	public void makePoly()
	{
		poly.reset();
		for(int i = 0;i<360;i+=3)
        {
            poly.addPoint((int) ((int)Math.round(10 * Math.cos(i*Math.PI/180)) + x + 10),(int)(y + 10 + Math.round(10 * Math.sin(i*Math.PI/180))));
            
        }
		poly.addPoint((int)x+5 + 10,(int) y+10);
		poly.addPoint((int)x+10,(int) y+15);
		poly.addPoint((int)x+5,(int) y+10);
		poly.addPoint((int)x+10,(int) y+5);
		poly.addPoint((int)x+5 + 10,(int) y+10);
	}
	
	public boolean collide(Thing ob2)
	{
		if(!invincible)
		{
			for(int i = 0; i < ob2.poly.xpoints.length; i++)
			{
				if(poly.contains(ob2.poly.xpoints[i],ob2.poly.ypoints[i]))
				{
					health -= 10;
					invuln = 20;
					return true;
				}
			}
		}
		return false;
	}
	
	public void updatePosition()
	{
		
		
		if(left && xSpeed > -thrust)
		{
			xSpeed += -0.2;
		}
		if(right && xSpeed < thrust)
		{
			xSpeed += 0.2;
		}
		if(up && ySpeed > -thrust)
		{
			ySpeed += -0.2;
		}
		if(down && ySpeed < thrust)
		{
			ySpeed += 0.2;
		}
		if(xSpeed > thrust*4)
		{
			xSpeed = thrust*4;
		}
		if(xSpeed < -thrust*4)
		{
			xSpeed = -thrust*4;
		}
		if(ySpeed > thrust*4)
		{
			ySpeed = thrust*4;
		}
		if(ySpeed < -thrust*4)
		{
			ySpeed = -thrust*4;
		}
		
		super.updatePosition();
		if(invuln > 0)
		{
			invuln--;
		}
		for(int i = 0; i < bulletList.size(); i++)
		{
			bulletList.get(i).updatePosition();
			if(Integer.parseInt(bulletList.get(i).toString())<= 0)
			{
				bulletList.remove(i);
			}
		}
	}
	
	public void draw(Graphics g)
	{
		//g.drawOval((int)x,(int) y, 20, 20);
		makePoly();
		g.setColor(shpColor);
		g.drawPolygon(poly);
		
		for(int i = 0; i < bulletList.size(); i++)
		{
			bulletList.get(i).draw(g);
		}
		
		g.setColor(Color.red);
		g.fillRect((int)(x), (int)(y+23), 20*health/maxHealth, 2);
		
	}

}
