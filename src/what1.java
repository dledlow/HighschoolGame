import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

public  class what1 extends JComponent implements KeyListener,ActionListener,MouseListener{

 //@author Daniel Ledlow
	
	

 
 
	
	
 Thing sizeControl = new Thing(0,0);//uses this to adjust the size of the screen of all Things using a static variable
 
 int numWin = 3000;//how many? CRANK DIS SHIT UP!
 int winDelay = 100;//how fast?
 int winCount = 10;//counter
 
 JFrame frame;
 
 private static final long serialVersionUID = -688856155579961348L;
 int width = 1024,height = 768;
 
 
 int gpSize = 30;
 
 //points on background grid
 /*
 GridPoint[][] grid = new GridPoint[gpSize][gpSize];
 */
 
 //our players
 Player you = new Player(100,height/4);
 Player him = new Player(100,3*height/4);
 
 //player attributes
 int thrust = 7;
 int maxHealth = 40;
 
 boolean hax = false;//hax
 boolean moveGravBullet = false;
 
 //failed fps stuff
 int frameCount = 0;
 long startTime = System.currentTimeMillis();
 long fps = 0;
 
 //list of planets
 ArrayList<Gravitron> gravList = new ArrayList<Gravitron>();
 
 //list of shrapnel pieces
 ArrayList<boom> boomList = new ArrayList<boom>();
 
 //each player now stores their own bullets
 static ArrayList<Player> plrList = new ArrayList<Player>();
 
 //do you want to party?
 boolean celebrate = true;
 
 //have you won?  
 boolean win = false;
 
 //alt game
 boolean alt = false;
 boolean r,o;
 Enemy draw = new Enemy(0,400,plrList);
 Player target = new Player(10,400);
 
 
 //...
 Timer what;

 public what1()
 {
  what = new Timer(30,this);
  
  frame = new JFrame();
  
  plrList.add(you);
  plrList.add(him);//a controllable second player //R!
  plrList.add(new Enemy(3*width/4,height/2,plrList));
  
  target.invincible = true;
  //gravList.add(new Gravitron(width/2,height/2,false,0));
  
  for(int i = 0; i < plrList.size(); i++)
  {
   plrList.get(i).shpColor = (new Color((int)(255/plrList.size()*(i+1)),200,(int)(255-(255/plrList.size()*(i+1)))));
   plrList.get(i).health = maxHealth;
   plrList.get(i).maxHealth = maxHealth;
  }
  /*
  for(int i  = 0; i < gpSize; i++)
  {
   for(int ii  = 0; ii < gpSize; ii++)
   {
    grid[i][ii] = new GridPoint(width/gpSize*i,height/gpSize*ii);
   }
  }
  */
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  frame.setSize(width,height);
  frame.add(this);
  frame.addKeyListener(this);
  frame.addMouseListener(this);
  frame.setVisible(true);
  what.start();//start the timer
  repaint();
  
 }
 
 public void paint(Graphics g)
 {
  try {
   Thread.sleep(10);
  } catch (InterruptedException e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
  }
  frameCount++;
  g.setColor(Color.black);
  g.fillRect(0, 0, width, height);//background
  
  
  g.setColor(Color.darkGray);
  
  for(int x  = 0; x < gpSize; x++)
  {
   g.drawLine((int)width/gpSize*x + width/gpSize, (int)0,(int) width/gpSize*x + width/gpSize,(int) height);
  }
  for(int y  = 0; y < gpSize; y++)
  {
   g.drawLine((int)0, (int)height/gpSize*y,(int) width,(int) height/gpSize*y);
  }
  /*
  for(int x  = 0; x < gpSize; x++)
  {
   for(int y  = 0; y < gpSize; y++)
   {
    if(x+1<gpSize)
     g.drawLine((int)grid[x][y].x, (int)grid[x][y].y,(int) grid[x+1][y].x,(int) grid[x+1][y].y);
    if(y+1<gpSize)
     g.drawLine((int)grid[x][y].x, (int)grid[x][y].y,(int) grid[x][y+1].x,(int) grid[x][y+1].y);
   }
  }*/
  
  
  //ship color
  g.setColor(new Color(100,150,150));
  
  
  //drawing the objects
  for(int i = 0; i < plrList.size(); i++)
  {
   //the player also draws their own bullets
   
   plrList.get(i).draw(g);
  }
  
  /*
  for(int i  = 0; i < gpSize; i++)
  {
   for(int ii  = 0; ii < gpSize; ii++)
   {
    grid[i][ii].draw(g);
   }
  }*/
  
  for(int i = 0; i < boomList.size(); i++)
  {
   boomList.get(i).draw(g);
  }
  for(int i = 0; i <gravList.size(); i++)
  {
   gravList.get(i).draw(g);
  }
  
  for(int i = 0; i < plrList.size(); i++)
  {
   //health bar
   g.setColor(Color.white);
   g.drawString("Player " + i + "'s Health:", width-maxHealth*2-50, 13 + i*50);
   g.setColor(Color.RED);
   g.fillRect(width-plrList.get(i).health*2-20, 20+ i*50, plrList.get(i).health*2 , 15);
   g.setColor(Color.BLACK);
   g.drawRect(width-maxHealth*2-20, 20 + i*50, maxHealth*2, 15);
  }
 
  
  g.setColor(Color.BLACK);
  
   
  
  if(plrList.size() == 1)
  {
   if(plrList.get(0) == you)
   {
    g.setColor(Color.green);
    g.drawString("You're Winner!", width/2, height/2 - 10);
    win = true;
    g.setColor(Color.black);
   }
   else
   {
    g.setColor(Color.green);
    g.drawString("You're Not Winner!", 350, 300);
    g.setColor(Color.black);
   }
  }
  
  //acceleration bar
  if(!you.exploded)
  {
   //g.fillRect(5, (int)(height-80-(50*(Math.sqrt(2*thrust*thrust)))) - 20, 55,(int) (height - ((height-80-(50*(Math.sqrt(2*thrust*thrust)))))));
   try {
	g.setColor(new Color((int)((255/(Math.sqrt(2*thrust*thrust))*Math.sqrt(you.ySpeed*you.ySpeed+you.xSpeed*you.xSpeed))),200,255-(int)((255/(Math.sqrt(2*thrust*thrust))*Math.sqrt(you.ySpeed*you.ySpeed+you.xSpeed*you.xSpeed)))));
} catch (Exception e) {
	// TODO Auto-generated catch block
	g.setColor(Color.red);
}
   g.drawString("0%", 6, height-80);
   g.drawString("50%", 6,(int)(height-80-(50*(Math.sqrt(2*thrust*thrust)/2))));
   g.drawString("100%", 6, (int)(height-80-(50*(Math.sqrt(2*thrust*thrust)))));
   g.fillRect(30, height-80-((int)(50*Math.sqrt(you.ySpeed*you.ySpeed+you.xSpeed*you.xSpeed))), 20, (int)(50*Math.sqrt(you.ySpeed*you.ySpeed+you.xSpeed*you.xSpeed) + 5));
  }
  //g.drawString("# of explosion pieces: "+Integer.toString(boomList.size()),50,50);
 }
 

 @Override
 public void keyPressed(KeyEvent e) 
 {
  // TODO Auto-generated method stub
  if(e.getKeyCode() == e.VK_A)
  {
   you.left = true;
  }
  if(e.getKeyCode() == e.VK_D)
  {
   you.right = true;
  }
  if(e.getKeyCode() == e.VK_W)
  {
   you.up = true;
  }
  if(e.getKeyCode() == e.VK_S)
  {
   you.down = true;
  }
  
  //hax
  if(e.getKeyCode() == e.VK_F)
  {
   hax = true;
  }
  
  if(e.getKeyCode() == e.VK_R)
  {
   r = true;
  }
  if(e.getKeyCode() == e.VK_O)
  {
   o = true;
  }
  
  if(e.getKeyCode() == e.VK_SPACE && alt)
  {
   
   double rand1 = Math.random();
   double rand2 = Math.random();
   for(int ii = 0; ii < numWin; ii++)
   {
    boomList.add(new boom(rand1*width,rand2*height));
   }
   
  }
  
  //for second player******************************* R!
  if(e.getKeyCode() == e.VK_LEFT)
  {
   him.left = true;
  }
  if(e.getKeyCode() == e.VK_RIGHT)
  {
   him.right = true;
  }
  if(e.getKeyCode() == e.VK_UP)
  {
   him.up = true;
  }
  if(e.getKeyCode() == e.VK_DOWN)
  {
   him.down = true;
  }
  
  if(e.getKeyCode() == e.VK_SPACE && hax)
  {
   for(int a = 0; a < 300; a++)
    you.bulletList.add(new Bullet(you.x+10,you.y+10,Math.random()*Math.PI*2));
  }
  
  //shoot based on direction of acceleration
  if(e.getKeyCode() == e.VK_Q)
  {
   if(you.x+you.xSpeed<you.x)
   {
    you.bulletList.add(new Bullet(you.x+5,you.y+5,Math.atan((you.y+you.ySpeed-you.y)/(you.x+you.xSpeed-you.x))-Math.PI/2));
   }
   else
   {
    you.bulletList.add(new Bullet(you.x+5,you.y+5,Math.atan((you.y+you.ySpeed-you.y)/(you.x+you.xSpeed-you.x))-Math.PI*1.5));
   }
  }
  
  //for second player******************************* R!
  if(e.getKeyCode() == e.VK_END)
  {
   for(int i = 0; i < plrList.size();i++)
    if(plrList.get(i) != you)
    {
     if(plrList.get(i).x+plrList.get(i).xSpeed<plrList.get(i).x)
     {
      plrList.get(i).bulletList.add(new Bullet(plrList.get(i).x+5,plrList.get(i).y+5,Math.atan((plrList.get(i).y+plrList.get(i).ySpeed-plrList.get(i).y)/(plrList.get(i).x+plrList.get(i).xSpeed-plrList.get(i).x))-Math.PI/2));
     }
     else
     {
      plrList.get(i).bulletList.add(new Bullet(plrList.get(i).x+5,plrList.get(i).y+5,Math.atan((plrList.get(i).y+plrList.get(i).ySpeed-plrList.get(i).y)/(plrList.get(i).x+plrList.get(i).xSpeed-plrList.get(i).x))-Math.PI*1.5));
     }
    }
  }
  
  
  
  repaint();
  
 }

 @Override
 public void keyReleased(KeyEvent e) {
  // TODO Auto-generated method stub
  if(e.getKeyCode() == e.VK_A)
  {
   you.left = false;
  }
  if(e.getKeyCode() == e.VK_D)
  {
   you.right = false;
  }
  if(e.getKeyCode() == e.VK_W)
  {
   you.up = false;
  }
  if(e.getKeyCode() == e.VK_S)
  {
   you.down = false;
  }
  
  
  if(e.getKeyCode() == e.VK_R)
  {
   r = false;
  }
  if(e.getKeyCode() == e.VK_O)
  {
   o = false;
  }
  
  //hax
  if(e.getKeyCode() == e.VK_F)
  {
   hax = false;///////
  }
  
  
  
  //for second player******************************* R!
  if(e.getKeyCode() == e.VK_LEFT)
  {
   him.left = false;
  }
  if(e.getKeyCode() == e.VK_RIGHT)
  {
   him.right = false;
  }
  if(e.getKeyCode() == e.VK_UP)
  {
   him.up = false;
  }
  if(e.getKeyCode() == e.VK_DOWN)
  {
   him.down = false;
  }
  
  
  repaint();
 }

 @Override
 public void keyTyped(KeyEvent arg0) {
  // TODO Auto-generated method stub
  
 }

 
 
 @Override
 public void actionPerformed(ActionEvent e) 
 {
  startTime = System.currentTimeMillis();
  
  
  if(win)
  {
   what.setDelay(40);
  }
  width = frame.getWidth();
  height = frame.getHeight();
  sizeControl.width = width;
  sizeControl.height = height;
  
  /*
  for(int x  = 0; x < gpSize; x++)
  {
   for(int y  = 0; y < gpSize; y++)
   {
    grid[x][y].trueX = width/gpSize*x;
    grid[x][y].trueY = height/gpSize*y;
    grid[x][y].updatePosition();
   }
  }*/
  
  for(int i = 0; i < gravList.size(); i++)
  {
   gravList.get(i).updatePosition();
   /*for(int x  = 0; x < gpSize; x++)
   {
    for(int y  = 0; y < gpSize; y++)
    {
     
     grid[x][y].netForceX += gravList.get(i).getForceX(grid[x][y]);
     
     grid[x][y].netForceY += gravList.get(i).getForceY(grid[x][y]);
    }
   }*/
  }
  
  for(int i = 0; i < boomList.size(); i++)
	  for(int a = 0; a <plrList.size(); a++)
	  	{
		  plrList.get(a).pull(boomList.get(i));
		  
	  	}
   
  for(int i = 0; i < boomList.size(); i++)
   for(int a = 0; a <gravList.size(); a++)
   {
    gravList.get(a).pull(boomList.get(i));
    if(gravList.get(a).collide(boomList.get(i)))
    {
     boomList.get(i).health = 0;
    }
   }
   
   // TODO Auto-generated method stub
   for(int i = 0; i < plrList.size(); i++)
   {
    for(int a = 0; a <gravList.size(); a++)
    {
     gravList.get(a).pull(plrList.get(i));//*********************************************
    }
    //damages everyone if they collide with other peoples bullets
    //also has a cooldown when you get hit
    if(plrList.get(i).invuln <= 0)
    {
     for(int a = 0; a <gravList.size(); a++)
     {
      if(plrList.get(i).collide(gravList.get(a)))
      {
       plrList.get(i).invuln = 0;
       for(int s = 0; s < 5; s++)
        boomList.add(new blood(plrList.get(i).x+10,plrList.get(i).y+10,plrList.get(i).shpColor));
      }
     }
     //go through each player
     for(int ii = 0; ii < plrList.size(); ii++)
     {
      //and all of that players bullets
      for(int iii = 0; iii < plrList.get(ii).bulletList.size(); iii++)
      {
       for(int a = 0; a <gravList.size(); a++)
       {
        
        gravList.get(a).pull(plrList.get(ii).bulletList.get(iii));//**********************************************
       }
       if(plrList.get(ii) != plrList.get(i))
       {
        //check everyone elses bullets
        if(plrList.get(i).collide(plrList.get(ii).bulletList.get(iii)))
        {
         
         plrList.get(ii).bulletList.remove(iii);
         for(int a = 0; a < 5; a++)
          boomList.add(new blood(plrList.get(i).x+10,plrList.get(i).y+10,plrList.get(i).shpColor));
         break;
        }
       }
      }
     }
    }
    
    
    //update everyone
    plrList.get(i).updatePosition();
    
    //makes you explode sets you to dead
    if(plrList.get(i).health <= 0 && !plrList.get(i).exploded)
    {
     for(int ii = 0; ii < 300; ii++)
     {
      boomList.add(new boom(plrList.get(i).x+10,plrList.get(i).y+10));
      plrList.get(i).exploded = true;
     }
     
     plrList.remove(i);
    }
   }
   
   //removes shrapnel
   //updates positions
   for(int i = 0; i < boomList.size(); i++)
   {
    boomList.get(i).updatePosition();
    if(Integer.parseInt(boomList.get(i).toString())<= 0)
    {
     boomList.remove(i);
    }
   }
   
   
   
   winCount--;
   if(win && winCount <=0 && celebrate)
   {
    
    double rand1 = Math.random();
    double rand2 = Math.random();
    for(int ii = 0; ii < numWin; ii++)
    {
     boomList.add(new boom(rand1*width,rand2*height));
    }
    winCount = winDelay;
   }
   
   repaint();
   
   fps = (System.currentTimeMillis()-startTime);
 }
 

 @Override
 public void mouseClicked(MouseEvent e) 
 {
  // TODO Auto-generated method stub
  
	 if(o&&!r)
	 {
		 Enemy temp = new Enemy(e.getX(),e.getY(), plrList);
		 temp.shpColor = new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255));
		 plrList.add(temp);
	 }
 }

 @Override
 public void mouseEntered(MouseEvent arg0) {
  // TODO Auto-generated method stub
  
 }

 @Override
 public void mouseExited(MouseEvent arg0) {
  // TODO Auto-generated method stub
  
 }
 
 
 @Override
 public void mousePressed(MouseEvent e) 
 {
  
  if((r&&o)&&!alt)
  {
   alt = true;
   plrList.removeAll(plrList);
   plrList.add(draw);
   //plrList.add(target);
   draw.moving = false;
   draw.shootDelay = 0;
   draw.shpColor = new Color((int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255));
  }
  
  //pew pew
  if(e.getX()<you.x)
  {
   if(hax)
    gravList.add(new Gravitron(e.getX(),e.getY(),moveGravBullet,Math.atan((e.getY()-25-you.y)/(e.getX()-you.x))-Math.PI/2));
   you.bulletList.add(new Bullet(you.x+5,you.y+5,Math.atan((e.getY()-25-you.y)/(e.getX()-you.x))-Math.PI/2));
  }
  else
  {
   if(hax)
    gravList.add(new Gravitron(e.getX(),e.getY(),moveGravBullet,Math.atan((e.getY()-25-you.y)/(e.getX()-you.x))-Math.PI*1.5));
   you.bulletList.add(new Bullet(you.x+5,you.y+5,Math.atan((e.getY()-25-you.y)/(e.getX()-you.x))-Math.PI*1.5));
  }
  
 }

 @Override
 public void mouseReleased(MouseEvent arg0) 
 {
  // TODO Auto-generated method stub
  
 }

}
