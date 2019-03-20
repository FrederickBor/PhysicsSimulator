package simulator.model;

import java.util.List;

public class FallingToCenterGravity implements GravityLaws {
	
	//Gravedad
	private double g;
	
	public FallingToCenterGravity(){
		g = 9.81;
	}
	
	@Override
	public void apply(List<Body> bodies) {
		for (Body i: bodies) {			
			i.setAcceleration(i.getPosition().direction().scale(-g));			
		}
	}

	public String toString() {
		return "Falling To Center Gravity Law";
	}

}
