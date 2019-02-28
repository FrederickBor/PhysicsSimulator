package simulator.model;

import java.util.List;

public class PhysicsSimulator {
	private double dt;
	private GravityLaws gl;
	private double timeLapsed;
	private List<Body> bodies;
	
	public PhysicsSimulator(GravityLaws gl, double dt){
		this.dt = dt;
		this.gl = gl;
		timeLapsed = 0;
	}
	
	public void addBody(Body b) {
		bodies.add(b);
	}
	
	public void advance() {
		gl.apply(bodies);
		for (Body body : bodies) {
			body.move(timeLapsed);
		}
		
		//TENGO DUDAS CON ESTO
		if(timeLapsed >= dt ) {
			timeLapsed = 0;
		}
		else {
			timeLapsed ++;
		}
	}
	
	public String toString() {
		StringBuilder s = new StringBuilder();
		//s.append("HOLA")
		return s.toString();
	}
}
