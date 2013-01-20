import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;


public class Enemy extends Player
{

	Player target;
	double angle = 0;
	double speed = 2;
	ArrayList<Player> plrList;
	boolean moving = true;
	boolean canBeKilled = true;
	boolean leadShot = false;
	
	int shootDelay = 50;
	int shootCount = 0;
	
	public Enemy(int xpos, int ypos,ArrayList<Player> playerList) 
	{
		super(xpos, ypos);
		plrList = playerList;
		// TODO Auto-generated constructor stub
	}
	
	public void getTarget()
	{
		int numplr = 0;
		for(int i = 0; i < plrList.size();i++)
		{
			if(plrList.get(i) == target)
			{
				numplr++;
			}
			if(target == null)
			{
				target = plrList.get(i);
			}
			else if(plrList.get(i) != target && plrList.get(i) != this)
			{
				if(Math.sqrt(Math.pow(target.x-x, 2)+Math.pow(target.y-y, 2))>Math.sqrt(Math.pow(plrList.get(i).x-x, 2)+Math.pow(plrList.get(i).y-y, 2)))
				{
					target = plrList.get(i);
				}
			}
		}
		if(numplr == 0)
		{
			target = plrList.get(0);
		}
	}
	
	public void draw(Graphics g)
	{	
		g.setColor(shpColor);
		super.draw(g);
		
		
		
		if(plrList.contains(target))
			g.drawLine((int)x+10, (int)y+10, (int)target.x+10, (int)target.y+10);
		//g.drawLine((int)x+10, (int)y+10, width/2, height/2);
		//g.drawLine((int)x+10, (int)y+10, (int)(x+10+Math.cos(angle) * speed*10), (int)(y+10+Math.sin(angle) * speed*10));
		if(!moving)
		{
			for(int i = 0; i < bulletList.size(); i++)
			{
				//if(i<=bulletList.size()-2)
					//g.drawLine((int)bulletList.get(i).x, (int)bulletList.get(i).y, (int)bulletList.get(i+1).x, (int)bulletList.get(i+1).y);
				
			}
			
		}
	
	}
	
	public void getTargAng()
	{
		if(leadShot)
		{
			/*
			double targSpeed = (Math.sqrt(Math.pow((target.xSpeed), 2)+Math.pow((target.ySpeed),2)));
			double distAB = (Math.sqrt(Math.pow((target.x-x), 2)+Math.pow((target.y-y),2)));
			double distA = targSpeed*(distAB/5);
			
			angle = Math.acos((2*Math.pow(distAB, 2)-Math.pow(distA,2))/(2*Math.pow(distAB, 2)));
			angle+=Math.atan((target.x-x)/(target.y-y));
			*/
		}
		else
		{
			if(x-target.x+target.ySpeed < 0)
				angle = Math.atan(((y-target.y)/(x-target.x)));
			if(x-target.x > 0)
				angle = Math.PI-Math.atan(((y-target.y)/(target.x-x)));
		}
	}
	
	public void updatePosition()
	{
		getTarget();
		getTargAng();
		
		
		
		shootCount--;
		if(shootCount <= 0)
		{
			shootCount = shootDelay;
			bulletList.add(new Bullet(x+5,y+5,angle-3*Math.PI/2));	
		}
		
		if(Math.sqrt(Math.pow(target.x-x, 2)+Math.pow(target.y-y, 2))<100)
		{
			angle = angle + Math.PI;
		}
		
		if(moving)
		{
			
			if(Math.cos(angle) * speed < 0)
			{
				xSpeed += -0.2;
			}
			if(Math.cos(angle) * speed > 0)
			{
				xSpeed += 0.2;
			}
			if(Math.sin(angle) * speed < 0)
			{
				ySpeed += -0.2;
			}
			if(Math.sin(angle) * speed > 0)
			{
				ySpeed += 0.2;
			}
			
			if(xSpeed > speed)
			{
				xSpeed = speed;
			}
			if(xSpeed < -speed)
			{
				xSpeed = -speed;
			}
			if(ySpeed > speed)
			{
				ySpeed = speed;
			}
			if(ySpeed < -speed)
			{
				ySpeed = -speed;
			}
		}
		
		if(canBeKilled)
			if(invuln > 0)
			{
				invuln--;
			}
		for(int i = 0; i < bulletList.size(); i++)
		{
			bulletList.get(i).doWrap = false;
			bulletList.get(i).updatePosition();
			if(Integer.parseInt(bulletList.get(i).toString())<= 0)
			{
				bulletList.remove(i);
			}
		}
		if(moving)
		{
			x += xSpeed;
			y += ySpeed;
		}
		wrap();
	}
	
	public void makePoly()
	{
		poly.reset();
		
		poly.addPoint((int)x+10,(int) y-10+10);
		poly.addPoint((int)x+5+10,(int) y+10+10);
		poly.addPoint((int)x+10+10,(int) y-3+10);
		poly.addPoint((int)x-10+10,(int) y-3+10);
		poly.addPoint((int)x-5+10,(int) y+10+10);
	}
}
