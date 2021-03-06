import java.util.Random;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.RedBlackBST;

/**
 * @author Niles McNabb, Ethan Chaiko, Benjamin Rankin
 */
public class PointST<Value>
{
	private RedBlackBST<Point2D, Value> st; //Red Black BST to hold 2D point and values
	
	 // construct an empty symbol table of points
	public PointST()                                
	{
		st = new RedBlackBST<Point2D, Value>();
	}
	
	// is the symbol table empty?
	public boolean isEmpty()                       
	{
		return st.isEmpty();
	}
	
	 // number of points 
	public int size()                          
	{
		return st.size();
	}
	
	 // associate the value val with point p
	public void put(Point2D p, Value val)     
	{
		if (p == null || val == null)
			throw new NullPointerException();
		
		st.put(p, val);
	}
	
	// value associated with point p 
	public Value get(Point2D p)                 
	{
		if (p == null)
			throw new NullPointerException();
		
		return st.get(p);
	}
	 
	// does the symbol table contain point p? 
	public boolean contains(Point2D p)            
	{
		if (p == null)
			throw new NullPointerException();
		
		return st.contains(p);
	}
	   
	// all points in the symbol table 
	public Iterable<Point2D> points()                        
	{
		return st.keys();
	}
	 
	// all points that are inside the rectangle 
	public Iterable<Point2D> range(RectHV rect)             
	{
		if (rect == null)
			throw new NullPointerException();
		
		Queue<Point2D> q = new Queue<Point2D>();
		
		for (Point2D p : st.keys()) //Iterate through all points in the table
		{
			if (rect.contains(p)) //If the current point is in the rectangle
			{
				q.enqueue(p); //Add the current point to the queue
			}
		}
		
		return q;
 	}
	
	// a nearest neighbor to point p; null if the symbol table is empty 
	public Point2D nearest(Point2D p) 
	{
		if (p == null)
			throw new NullPointerException();
		
		Point2D nearest = null;
		Double n = null;
		
		for (Point2D s : st.keys()) //Iterate through all points in the table
		{
			if (n == null) //If nearest has not been determined
			{
				n = p.distanceSquaredTo(s); //Set n to the current distance
				nearest = s; //Set nearest to the current point
			}
			
			if (n > p.distanceSquaredTo(s)) //If n is greater than the current distance
			{
				n = p.distanceSquaredTo(s); //Set n to the current distance
				nearest = s; //Set nearest to the current point
			}
		}
		
		return nearest;
	}
	
	public static void main(String[] args)
	{
		String filename = "C:\\TestDocs\\input1M.txt";
        In in = new In(filename);
        
        PointST<Integer> st = new PointST<Integer>();
        for (int i = 0; !in.isEmpty(); i++) 
        {
        	double x = in.readDouble();
        	double y = in.readDouble();
        	Point2D p = new Point2D(x, y);
        	st.put(p, i);
        }
        Random r = new Random();
        
        int calls = 5000;
        
        long start = System.currentTimeMillis();
        for (int i = 0; i < calls; i++)
        {
        	Point2D p = new Point2D(r.nextDouble(), r.nextDouble());
        	st.nearest(p);
//        	System.out.println(st.nearest(p));
        }
        long fin = System.currentTimeMillis();
        double seconds = (fin - start)/ 1000.0;
        double results = calls/seconds;
        
        System.out.println("Total number of calls: " + calls);
        System.out.println("Time: " + seconds);
        System.out.println("Calls per second: " + results);
	}

}
