package simulator.model;

import simulator.misc.Vector;

public class Body {
	
	protected String id;
	protected Vector velocity;
	protected Vector acceleration;
	protected Vector position;
	protected double mass;
	
	public Body() {
		this.id = "";
		this.velocity = new Vector(0);
		this.acceleration = new Vector(0);
		this.position = new Vector(0);
		this.mass = 0.0;
	}
	
	public Body(String id, Vector vel, Vector acc, Vector pos, double mass) {
		this.id = id;
		this.velocity = vel;
		this.acceleration = acc;
		this.position = pos;
		this.mass = mass;
	}

	public String getId() {
		return id;
	}
	
	public Vector getVelocity() {
		return  new Vector(this.velocity);
	}
	
	public Vector getAcceleration() {
		return new Vector(this.acceleration);
	}

	public Vector getPosition() {
		return new Vector(this.position);
	}

	double getMass() {
		return mass;
	}

	void setVelocity(Vector v) {
		this.velocity = new Vector(v);
	}

	void setAcceleration(Vector a) {
		this.acceleration = new Vector(a);
	}
	
	void setPosition(Vector p) {
		this.position = new Vector(p);
	}
	
	void move(double t) {
		Vector auxV = new Vector(this.velocity.scale(t));
		Vector auxA = new Vector(this.acceleration.scale((t * t) / 2));
		auxA.plus(auxV);
		this.position = this.position.plus(auxA);
		
		this.velocity =  this.velocity.plus(new Vector(this.acceleration.scale(t)));
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{ \"id: \" ");
		sb.append(this.id);
		sb.append(", \"mass: \" ");
		sb.append(this.mass);
		sb.append(", \"pos: \" ");
		sb.append(this.position.toString());
		sb.append(", \"vel: \" ");
		sb.append(this.velocity.toString());
		sb.append(", \"acc: \" ");
		sb.append(this.acceleration.toString());
		sb.append(" }");
		
		return sb.toString();
	}
	
	
}
