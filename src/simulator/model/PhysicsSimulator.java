package simulator.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class PhysicsSimulator {
	private double dt;
	private GravityLaws gl;
	private double timeLapsed;
	private List<Body> bodies;
	
	public PhysicsSimulator(GravityLaws gl, double dt) throws IllegalArgumentException {
		
		if (gl == null) throw new IllegalArgumentException("Tienes que poner una GravityLaw");
		if (dt <= 0) throw new IllegalArgumentException("El Tiempo real por paso no puede ser menor o igual que 0");
		
		this.dt = dt;
		this.gl = gl;
		this.bodies = new ArrayList<Body>();
		timeLapsed = 0;
	}
	
	/**
	 * Añade el cuerpo b al simulador. El método comprueba que no existe ningún otro cuerpo con el mismo identificador. Si existiera, lanza una excepción del tipo IllegalArgumentException.
	 * 
	 * @param b Cuerpo que vamos a añadir a la simulación.
	 * @throws IllegalArgumentException En caso de que haya dos cuerpos con el mismo id.
	 */
	public void addBody(Body b) throws IllegalArgumentException {
		
		for (Body body: bodies) {
			if (b.id.equals(body.id))
				throw new IllegalArgumentException("Parece que hay dos id iguales...");
		}
		
		bodies.add(b);
	}
	
	/**
	 * Aplica un paso de simulación, i.e., primero llama al método apply de las leyes de la gravedad, después llama a move de cada cuerpo, donde dt es el tiempo real por paso, y finalmente incrementa el tiempo actual en dt segundos.
	 */
	public void advance() {
		gl.apply(bodies);
		for (Body body : bodies) {
			body.move(dt);
		}
		timeLapsed += dt;
	}
	
	/**
	 * Devuelve un String que representa un estado del simulador, utilizando el siguiente formato JSON:
	 * 
	 * { "time": T, "bodies": [json1, json2, ...] }
	 * 
	 * @return Un String que representa un estado del simulador.
	 */
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
