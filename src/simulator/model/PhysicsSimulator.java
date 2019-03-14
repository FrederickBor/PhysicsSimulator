package simulator.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PhysicsSimulator {
	private double dt;
	private GravityLaws gl;
	private double timeLapsed;
	private List<Body> bodies;
	
	public PhysicsSimulator(GravityLaws gl, double dt) throws IllegalArgumentException{
		
		if (gl == null) throw new IllegalArgumentException();
		if (dt <= 0) throw new IllegalArgumentException();
		
		this.dt = dt;
		this.gl = gl;
		this.bodies = new ArrayList<Body>();
		timeLapsed = 0;
	}
	
	public void addBody(Body b) {
		bodies.add(b);
	}
	
	public void advance() {
		gl.apply(bodies);
		for (Body body : bodies) {
			body.move(dt);
		}
		timeLapsed += dt;
	}
	
	public String toString() {
		StringBuilder s = new StringBuilder();
		
		s.append("{ \"time\": " + timeLapsed + ",");
		s.append("\"bodies\":[");
		
		Iterator<Body> iterator = bodies.iterator();
		
		while(iterator.hasNext()) {
			s.append(iterator.next().toString());
			
			if(iterator.hasNext()) 
				s.append(",");
		}
		
		s.append("]}");
		
		return s.toString();
	}
}
