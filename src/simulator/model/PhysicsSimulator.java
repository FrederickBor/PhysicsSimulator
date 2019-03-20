package simulator.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PhysicsSimulator {
	private double dt;
	private GravityLaws gl;
	private double timeLapsed;
	private List<Body> bodies;
	private List<SimulatorObserver> observers;
	
	public PhysicsSimulator(GravityLaws gl, double dt) throws IllegalArgumentException{
		
		if (gl == null) throw new IllegalArgumentException();
		if (dt <= 0) throw new IllegalArgumentException();
		
		this.dt = dt;
		this.gl = gl;
		this.bodies = new ArrayList<Body>();
		this.observers = new ArrayList<SimulatorObserver>();
		timeLapsed = 0;
	}

	public void reset(){
		this.dt = 0;
		this.timeLapsed = 0;
		this.bodies = new ArrayList<Body>();

		for (SimulatorObserver ob : observers){
			o.onReset(bodies, timeLapsed, dt, gl.toString());
		}
	}

	public setDeltaTime(double dt){
		if (dt <= 0) throw new IllegalArgumentException();

		this.dt = dt;

		for (SimulatorObserver ob : observers){
			o.onDeltaTimeChanged(dt);
		}
	}

	public void setGravityLaws(GravityLaws gl){
		if (gl == null) throw new IllegalArgumentException();

		this.gl = gl;

		for (SimulatorObserver ob : observers){
			o.onGravityLawChanged(gl.toString());
		}
	}

	public void addObserver(SimulatorObserver o){
		boolean isOnList = false;

		for (SimulatorObserver ob : observers){
			if (ob == o){
				isOnList = true;
			}
		}

		if (!isOnList) {
			o.onRegister(bodies, timeLapsed, dt, gl.toString());
			observers.add(o);
		}
	}
	
	public void addBody(Body b) {
		bodies.add(b);

		for (SimulatorObserver ob : observers){
			o.onBodyAdded(bodies, b);
		}
	}
	
	public void advance() {
		gl.apply(bodies);
		for (Body body : bodies) {
			body.move(dt);
		}
		timeLapsed += dt;

		for (SimulatorObserver ob : observers){
			o.onAdvance(bodies, timeLapsed);
		}
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
