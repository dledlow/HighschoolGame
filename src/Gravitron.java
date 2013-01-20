
public class Gravitron extends Thing{

	
	int size = 30;
	double max = 0.5;
	double G = 0.5;
	double speed = 5;
	
	
	public Gravitron(double x2, double y2,boolean moving,double angle) {
		super(x2, y2);
		mass = 3000;
		// TODO Auto-generated constructor stub
		if(moving)
		{
			xSpeed = Math.cos(angle-Math.PI/2) * speed;
			ySpeed = Math.sin(angle-Math.PI/2) * speed;
		}
	}
	
	public Gravitron(double x2, double y2) {
		super(x2, y2);
	}

	public void updatePosition()
	{
		x += xSpeed;
		y += ySpeed;
		
		
		wrap();
	}
	
	public void makePoly()
	{
		poly.reset();
		for(int i = 0;i<360;i+=6)
        {
            poly.addPoint((int) ((int)Math.round(size * Math.cos(i*Math.PI/180)) + x),(int)(y  + Math.round(size * Math.sin(i*Math.PI/180))));
        }
	}
	
	public double getForceX(Thing thing)
	{
		double result = 0;
		double c = (G*mass*thing.mass)/(Math.pow(thing.trueX-x,2) + Math.pow(thing.trueY-y,2));
		double theta = Math.atan(((thing.trueY-y)/(thing.trueX-x)));
		
		double xMax = Math.cos(theta)*c;
		
		if(xMax > max)
		{
			xMax = max;
		}
		if(xMax < -max)
		{
			xMax = -max;
		}
		
		
		if(thing.trueX-x < 0)
		{
			result = xMax;
		}
		else if(thing.trueX-x > 0)
		{
			result = -xMax;
		}
		
		return result;
		
	}
	
	public double getForceY(Thing thing)
	{
		double result = 0;
		double c = (G*mass*thing.mass)/(Math.pow(thing.trueX-x,2) + Math.pow(thing.trueY-y,2));
		double theta = Math.atan(((thing.trueY-y)/(thing.trueX-x)));
		
		double yMax = Math.cos(theta)*c;
		
		if(yMax > max)
		{
			yMax = max;
		}
		if(yMax < -max)
		{
			yMax = -max;
		}
		
		
		if(thing.trueY-y < 0)
		{
			result = yMax;
		}
		else if(thing.trueY-y > 0)
		{
			result = -yMax;
		}
		
		return result;
		
	}
	
	public void pull(Thing ob)
	{
		
		
		double c = (G*mass*ob.mass)/(Math.pow(ob.x-x,2) + Math.pow(ob.y-y,2));
		double theta = Math.atan(((ob.y-y)/(ob.x-x)));
		
		double xMax = Math.cos(theta)*c;
		double yMax = Math.sin(theta)*c;
		
		if(xMax > max)
		{
			xMax = max;
		}
		if(yMax > max)
		{
			yMax = max;
		}
		if(xMax < -max)
		{
			xMax = -max;
		}
		if(yMax < -max)
		{
			yMax = -max;
		}
		
		//System.out.println(theta);
		if(ob.x-x < 0)
		{
			ob.xSpeed += xMax;
			ob.ySpeed += yMax;
		}
		else if(ob.x-x > 0)
		{
			ob.xSpeed += -xMax;
			ob.ySpeed += -yMax;
		}
	}
}
