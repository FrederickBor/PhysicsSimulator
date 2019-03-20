package simulator.model;

import java.util.List;

import simulator.misc.Vector;

public class NewtonUniversalGravity implements GravityLaws {
	
	private double G;
	
	public NewtonUniversalGravity(){
		G = 6.67E-11;	
	}

	@Override
	public void apply(List<Body> bodies) {
		for (Body body: bodies) {
			int dim = body.getAcceleration().dim();
			//Si la masa del cuerpo es 0 entonces
			//la aceleracion y la velocidad
			// se vuelven [0, ..., 0]
			if (body.getMass() == 0){
				body.setAcceleration(new Vector(dim));
				body.setVelocity(new Vector(dim));
			}
			else {
				Vector allForces = sumOfForces(body, bodies, dim);
				Vector newAccelerationVector = allForces.scale(1/body.getMass());
				body.setAcceleration(newAccelerationVector);
			}			
		}
	}
	
	private Vector sumOfForces(Body i, List<Body> bodies, int dim) {
		Vector suma = new Vector(dim);
		
		for (Body j: bodies) {
			//Calculamos el vector de la fuerza que aplica
			// j sobre i
			if (i != j )
				suma = suma.plus(calculateFij(i,j,dim));
			
		}
		
		return suma;
	}
	
	private Vector calculateFij(Body i, Body j, int dim) {
		
		//Calculamos el Vector dij
		Vector pi, pj;
		pi = i.getPosition();
		pj = j.getPosition();
		
		Vector dijVector = (pj.minus(pi)).direction();
		
		//Calculamos Fij
		double mi,mj;
		mi = i.getMass();
		mj = j.getMass();
		
		double denominador = pj.distanceTo(pi);
		double Fij = (G * mi * mj ) / (denominador * denominador);
		
		//Calculamos dij * Fij
		
		return dijVector.scale(Fij);
	}

	public String toString() {
		return "Newton Universal Gravitation Law";
	}
}
