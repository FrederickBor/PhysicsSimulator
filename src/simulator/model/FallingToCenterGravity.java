package simulator.model;

import java.util.List;

import simulator.misc.Vector;

public class FallingToCenterGravity implements GravityLaws {
	
	//Gravedad
	private double g;
	
	public FallingToCenterGravity(){
		g = 9.81;
	}
	
	@Override
	public void apply(List<Body> bodies) {
		
		for (Body i: bodies) {
			//Calculamos la nueva aceleracion del cuerpo
			Vector a, v, di;
			v = i.getVelocity();
			di = v.direction();
			a = di.scale(-g);
			
			i.setAcceleration(a);		
			
		}

	}

}
