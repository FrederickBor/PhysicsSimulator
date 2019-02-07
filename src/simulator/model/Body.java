package simulator.model;

import simulator.misc.Vector;

public class Body {
	
	private String id;
	private Vector velocity;
	private Vector acceleration;
	private Vector position;
	private double mass;
	
	public Body() {
		
	}

	public Vector getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector velocity) {
		this.velocity = velocity;
	}

	public Vector getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(Vector acceleration) {
		this.acceleration = acceleration;
	}

	public Vector getPosition() {
		return position;
	}

	public void setPosition(Vector position) {
		this.position = position;
	}

	public String getId() {
		return id;
	}

	public double getMass() {
		return mass;
	}
	
	
}
