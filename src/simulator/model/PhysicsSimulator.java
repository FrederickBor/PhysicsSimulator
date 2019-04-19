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
	
	public PhysicsSimulator(GravityLaws gl, double dt) throws IllegalArgumentException {
		
		if (gl == null) throw new IllegalArgumentException("Tienes que poner una GravityLaw");
		if (dt <= 0) throw new IllegalArgumentException("El Tiempo real por paso no puede ser menor o igual que 0");
		
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
			ob.onReset(bodies, timeLapsed, dt, gl.toString());
		}
	}

	public void setDeltaTime(double dt){
		if (dt <= 0) throw new IllegalArgumentException();

		this.dt = dt;

		for (SimulatorObserver ob : observers){
			ob.onDeltaTimeChanged(dt);
		}
	}

	public void setGravityLaws(GravityLaws gl){
		if (gl == null) throw new IllegalArgumentException();

		this.gl = gl;

		for (SimulatorObserver ob : observers){
			ob.onGravityLawChanged(gl.toString());
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
	
	/**
	 * Añade el cuerpo b al simulador. El metodo comprueba que no existe ningun otro cuerpo con el mismo identificador. Si existiera, lanza una excepción del tipo IllegalArgumentException.
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

		for (SimulatorObserver ob : observers){
			ob.onBodyAdded(bodies, b);
		}
	}
	
	/**
	 * Aplica un paso de simulación, i.e., primero llama al mÃ©todo apply de las leyes de la gravedad, despuÃ©s llama a move de cada cuerpo, donde dt es el tiempo real por paso, y finalmente incrementa el tiempo actual en dt segundos.
	 */
	public void advance() {
		gl.apply(bodies);
		for (Body body : bodies) {
			body.move(dt);
		}
		timeLapsed += dt;

		for (SimulatorObserver ob : observers){
			ob.onAdvance(bodies, timeLapsed);
		}
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
	
	public int getBodiesQuantity() {
		return bodies.size();
	}
}
