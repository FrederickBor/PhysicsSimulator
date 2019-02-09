package simulator.model;

public class PhysicsSimulator {
	private double dt;
	private GravityLaws gl;
	
	PhysicsSimulator(GravityLaws gl, double dt){
		this.dt = dt;
		this.gl = gl;
	}
	
	public void addBody(Body b) {
		
	}
	
	public void advance() {
		
	}
	
	public String toString() {
		StringBuilder s = new StringBuilder();
		//s.append("HOLA")
		return s.toString();
	}
}
