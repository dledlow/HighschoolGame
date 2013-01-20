import java.util.*;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;


import javax.swing.*;
import javax.swing.Timer;



public class Title extends JComponent implements KeyListener,ActionListener,MouseListener{

	/**
	 * @param args
	 */
	
	Thing sizeControl = new Thing(0,0);//uses this to adjust the size of the screen of all Things using a static variable
	
	JFrame frame;
	Timer time;
	
	boolean hax = false;
	
	
	 int gpSize = 30;
	 int num = 30;//how many? CRANK DIS SHIT UP!
	 int delay = 75;//how fast?
	 int count = 10;//counter
	 int width = 1024,height = 768;
	 int size = 0;
	 int numLines = 0;
	 
	 boolean showGuide = false;
	 
	 
	 Button go = new Button((int)(width/2), (int)(height/2), 200, 100, "GO!!!");
	 Button info = new Button((int)(width/2), (int)(height/2)+150, 200, 100, "How to Play");
	 Button back = new Button((int)(3*width/4), (int)(height/2)+150, 100, 50, "Back");
	 
	 
	private static final long serialVersionUID = -688856155579961348L;
	 ArrayList<boom> boomList = new ArrayList<boom>();
	 Bullet[][] grid;//the title
	 ArrayList<Gravitron> gravList = new ArrayList<Gravitron>();
	 
	public Title() throws Exception
	{
		time = new Timer(30, this);
		frame = new JFrame();
		makeTitle();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width,height);
		frame.add(this);
		frame.addMouseListener(this);
		frame.addKeyListener(this);
		frame.setVisible(true);
		time.start();//start the timer
		repaint();
	}
	
	 public void paint(Graphics g)
	 {
	  	try 
	  	{
	  		Thread.sleep(5);
	  	} 
	  	catch (InterruptedException e) 
	  	{
		  e.printStackTrace();
	  	}
	  	g.setColor(Color.black);
	  	g.fillRect(0, 0, width, height);
	  	
	  	
	  	
	  	
	  	
	  	
	  	g.setColor(Color.darkGray);
	    
	    for(int x  = 0; x < gpSize; x++)
	    {
	     g.drawLine((int)width/gpSize*x + width/gpSize, (int)0,(int) width/gpSize*x + width/gpSize,(int) height);
	    }
	    for(int y  = 0; y < gpSize; y++)
	    {
	     g.drawLine((int)0, (int)height/gpSize*y,(int) width,(int) height/gpSize*y);
	    }
	    
	    for(int i = 0; i < gravList.size(); i++)
	    {
	    	gravList.get(i).draw(g);
	    }
	    
	    
	    
		g.setColor(Color.GREEN);
		for(int y = 0; y < numLines; y++)
		{
				
			for(int x = 0; x < size; x++)
			{
				try
				{
					grid[x][y].draw(g);
				}
				catch(Exception e)
				{
						
				}
			}
		}
		
		if(!showGuide)
	    {	
		  	go.draw(g);
		  	info.draw(g);
	    }
	    else
	    {
	    	FontMetrics fm   = g.getFontMetrics(g.getFont());
			java.awt.geom.Rectangle2D rect = fm.getStringBounds("DEAL WITH IT.", g);

			int textHeight = (int)(rect.getHeight()); 
			int textWidth  = (int)(rect.getWidth());
			int panelHeight= height;
			int panelWidth = width;

			// Center text horizontally and vertically
			int x2 = (panelWidth  - textWidth)  / 2;
			int y2 = (panelHeight - textHeight) / 2  + fm.getAscent();

	    	g.drawString("DEAL WITH IT.", x2, y2);
	    	
	    	g.drawRect(width/4, height/4+100, width/2+50, height/4+25);
	    	
	    	back.draw(g);
	    }
	    
	  	for(int i = 0; i < boomList.size(); i++)
	    {
	     boomList.get(i).draw(g);
	    }
	 }
	

	
	public void makeTitle() throws FileNotFoundException
	{
		Scanner sc = new Scanner(new FileReader("Title.txt"));
		String line = "";
		while(sc.hasNextLine())
		{
			numLines++;
			line = sc.nextLine();
			if(size < line.length())
			{
				size = line.length();
			}	
		}
		
		//for nice fonts use "poison" or "rowan cap" or "Lean" or "AMD AAA01" or "Caligraphy"
		sc = new Scanner(new FileReader("Title.txt"));
		grid = new Bullet[size][numLines];
		
		char[] lineChars = new char[size];
		for(int y = 0; y < numLines; y++)
		{
			lineChars = sc.nextLine().toCharArray();
			for(int x = 0; x < size; x++)
			{
				try
				{
					if(lineChars[x] != ' ')
					{
						//                         | plrSize
						//                         v
						grid[x][y] = new Bullet(x*10+width/2-size/2*10,y*10+height/5,0);
						grid[x][y].xSpeed = 0;
						grid[x][y].ySpeed = 0;
						//grid[x][y].shpColor = (Color.GREEN);
					}
				}
				catch (Exception e)
				{
					
				}
			}
		}
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		
		width = frame.getWidth();
		height = frame.getHeight();
		sizeControl.width = width;
		sizeControl.height = height;
		
		if(info.pressed == true&&!showGuide)
		{
			showGuide = true;
		}
		
		if(back.pressed == true&&showGuide)
		{
			showGuide = false;
		}
		
		go.x = width/2;
		go.y = height/2;
		info.x = width/2;
		info.y = height/2 + 150;
		back.x = 3*width/4;
		back.y = height/2 + 150;
		
		for(int i = 0; i < boomList.size(); i++)
		{
			boomList.get(i).updatePosition();
			if(Integer.parseInt(boomList.get(i).toString())<= 0)
		    {
		    	boomList.remove(i);
		    }
			else
			{
				for(int ii = 0;ii<gravList.size(); ii++)
				{
					gravList.get(ii).pull(boomList.get(i));
					if(boomList.get(i).collide(gravList.get(ii)))
					{
						boomList.remove(i);
					}
				}
			}
		}
		
		count--;
		   if(count <=0)
		   {
		    
		    double rand1 = Math.random();
		    double rand2 = Math.random();
		    for(int ii = 0; ii < num; ii++)
		    {
		     boomList.add(new boom(rand1*width,rand2*height));
		    }
		    count = delay;
		   }
		   
		   repaint();
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(hax)
			gravList.add(new Gravitron(e.getX(), e.getY(), false, 0));
	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		if(go.poly.contains(e.getX(),e.getY()))
		{
			what1 me = new what1();
			frame.setVisible(false);
		}
		if(info.poly.contains(new Point(e.getX(),e.getY())))
		{
			info.pressed = true;
		}
		if(back.poly.contains(new Point(e.getX(),e.getY())))
		{
			back.pressed = true;
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
		// TODO Auto-generated method stub
		go.pressed = false;
		info.pressed = false;
		back.pressed = false;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == e.VK_F)
		  {
			hax = true;
		  }
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == e.VK_F)
		{
			hax = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
