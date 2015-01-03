/**
 * Database structures and algorithms assignment. Create a tour class
 * that allows nodes to be inserted using two different heuristics. 
 * Smallest increase in tour length and nearest neighbour insertion. 
 */
public class Tour {

	private Node first;
	private Node last;
		
	/**
	 * Node represents a Node for the linked list Tour, and contains a two
	 * dimensional point object, and a pointer to the next node in the list.
	 */
	private class Node {
		
		private Point p;
		private Node next;
	}
	
	/**
	 * Tour constructor sets the first and last nodes to null
	 * in order to begin adding new nodes to the tour.
	 * 
	 */
	public Tour() {
		
		this.first = null;
		this.last = null;
	}

	/**
	 * Tour constructor to take in four points
	 * and create a square tour.
	 * 
	 * @param a is a new point in the tour
	 * @param b is a new point in the tour
	 * @param c is a new point in the tour
	 * @param d is a new point in the tour
	 */
	public Tour(Point a, Point b, Point c, Point d) {
		
		// create new nodes for each new point
		Node nodeA = new Node();
		Node nodeB = new Node();
		Node nodeC = new Node();
		Node nodeD = new Node();
		
		// set node coordinates
		nodeA.p = a;
		nodeB.p = b;
		nodeC.p = c;
		nodeD.p = d;

		// link the nodes together in a tour
		nodeA.next = nodeB;
		nodeB.next = nodeC;
		nodeC.next = nodeD;
		nodeD.next = null;

		// set the first and last nodes 
		this.first = nodeA;
		this.last = nodeD;
	}

	/**
	 * Show prints a list of all point objects in the Tour in the order they are
	 * currently in through standard output.
	 */
	public void show() {
		
		// start a the first node
		Node currentNode = this.first;
		
		// loop through the tour and print the coordinates for each node
		while (currentNode != null) {
			StdOut.printf("%s\n", currentNode.p);
			currentNode = currentNode.next;
		}
	}

	/**
	 * Draw utilizes the drawTo method in the Point class to display the points
	 * with connecting lines as a way to visualize the current Tour.
	 */
	public void draw() {
		
		// start at the first node
		Node currentNode = this.first;
		
		// loop through the tour and draw the connections
		while (currentNode != null){
			
			if (currentNode.next != null) {
				
				currentNode.p.drawTo(currentNode.next.p);
			}
			
			// update the last node 
			this.last = currentNode;
			
			// move to the next node
			currentNode = currentNode.next;
		}
		
		// draw the connection between the first and last nodes
		if(this.last != null) this.last.p.drawTo(this.first.p);		
				
	}

	/**
	 * The distance method iterates through all of the current nodes in the
	 * linked list, calculates the total length of the Tour as a double, and
	 * returns it.
	 */
	public double distance() {
		
		// start at the first node
		Node currentNode = this.first;
		
		double distance = 0.0;
		
		// loop through the tour and calculate the total distance
		while (currentNode != null) {
			
			// if the node has a next node
			if (currentNode.next != null) {
				
				// add distance to next node
				distance += currentNode.p.distanceTo(currentNode.next.p);
				
			}else {
				
				// add distance from the first node to the last node
				distance += currentNode.p.distanceTo(this.first.p);
				
			}
			
			// move to the next node
			currentNode = currentNode.next;
		}
		return distance;
	}

	/**
	 * insertSmallest reads in the next point and adds it to the current tour
	 * after the point where it results in the least possible increase in tour
	 * length. If there is more than one point that is equidistant, it will be
	 * inserted after the first point that is discovered. 
	 * 
	 */
	public void insertSmallest(Point p) {
		
		Node node = new Node();
		Node linkNode = new Node();
				
		node.p = p;

		// find the tour length
		double distance = distance();
		
		// for the first node set it to itself
		if(this.first == null) {
						
			node.next = this.first;
			this.first = node;
						
		}else{			
			
			// start search at first node
			Node currentNode = this.first; 					
			
			// add first node
			node.next = currentNode.next;
			currentNode.next = node;
			
			// get the increase in the tour length
			double increase = distance() - distance;
	
			// remove first node
			currentNode.next = node.next;
			node.next = null;
			
			// find the smallest increase
			while (currentNode != null) {
				
				// add node
				node.next = currentNode.next;
				currentNode.next = node;
				
				// if the increase is smaller than the previous measurement
				if((distance() - distance) <= increase){
					
					// store the smallest increase in tour length
					increase = distance() - distance;
					
					// store the link node
					linkNode = currentNode;
					
				}
							
				// remove the node
				currentNode.next = node.next;
				node.next = null;
				
				// move to the next node
				currentNode = currentNode.next;
			}
			
			// add this node to the tour at the link node
			node.next = linkNode.next;
			linkNode.next = node;
			
			// find the new tour length
			distance = distance();
		
		}
	}

	/**
	 * insertNearest reads in the next point and adds it to the current tour
	 * after the point to which it is closest. If there are more than one
	 * equidistant points, it will be added after the first point that is
	 * discovered.
	 */
	public void insertNearest(Point p) {
		
		Node node = new Node();
		node.p = p;
		
		// for the first node set it to itself
		if(this.first == null) {
						
			node.next = this.first;
			this.first = node;
						
		}else{			
			
			// start the search at the first node
			Node currentNode = this.first; 
			
			// set the shortest distance to the first node
			double distance = node.p.distanceTo(currentNode.p);
			
			Node nearestNode = new Node();
						
			// loop through tour and find the nearest node
			while (currentNode != null) {
				
				// if the distance to this node is shorter that the previous measurement
				if (node.p.distanceTo(currentNode.p) <= distance) {
					
					// store the new nearest node
					nearestNode = currentNode;
					
					// set the new shortest distance
					distance = node.p.distanceTo(currentNode.p); 
										
				}
				
				// move to the next node
				currentNode = currentNode.next;
			}			
			
			// add this node to the tour after the nearest node
			node.next = nearestNode.next; 			
			nearestNode.next = node;
							 
		}
	}

	/**
	 * Calculates the number of nodes in this tour 
	 * 
	 * @return int numberOfNodes is the number of nodes in the tour
	 */
	public int size() {
		
		int numberOfNodes = 0;
		
		// start at the first node
		Node currentNode = this.first;
		
		// loop through the tour and count the number of nodes
		while (currentNode != null) {
			numberOfNodes++;
			
			// move to the next node
			currentNode = currentNode.next;
		}
		
		return numberOfNodes;
	}

	/**
	 * Main for testing the four initial points in the square tour object
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// get dimensions
        int w = 600;
        int h = 600;
        StdDraw.setCanvasSize(w, h);
        StdDraw.setXscale(0, w);
        StdDraw.setYscale(0, h);
        StdDraw.setPenRadius(.005);
        
		// define 4 points forming a square
		Point a = new Point(100.0, 100.0);
		Point b = new Point(500.0, 100.0);
		Point c = new Point(500.0, 500.0);
		Point d = new Point(100.0, 500.0);

		// Set up a Tour with those four points
		// The constructor should link a->b->c->d->a
		Tour squareTour = new Tour(a, b, c, d);

		// Output the Tour
		squareTour.show();
		squareTour.draw();
		
		// turn on animation mode
        StdDraw.show(100);
	}

}