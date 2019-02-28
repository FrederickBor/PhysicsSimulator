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
			Vector a, vectorToOrigin, di, aux;
			
			//obtenemos la dimensión del vector
			int dim = i.getVelocity().dim();
			
			//Creamos un nuevo vector con coordenadas en el origen
			aux = new Vector(dim);
			
			//Calculamos el vector con dirección al origen
			vectorToOrigin = aux.minus(i.getPosition());
			
			//Calculamos el vector unitario con dirección al origen
			di = vectorToOrigin.direction();
			
			//Calculamos la nueva aceleración del cuerpo
			a = di.scale(-g);
			
			//Se coloca la nueva aceleración en el cuerpo
			i.setAcceleration(a);		
			
		}

	}

}
