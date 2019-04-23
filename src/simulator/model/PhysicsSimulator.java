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

	/**
	 * Vacía la lista de cuerpos y pone el tiempo a 0,0.
	 * Además envía una notificación onReset a todos los observadores.
	 */
	public void reset(){
		this.timeLapsed = 0;
		this.bodies = new ArrayList<Body>();

		for (SimulatorObserver ob : observers){
			ob.onReset(bodies, timeLapsed, dt, gl.toString());
		}
	}

	/**
	 * Cambia el "Tiempo real por paso" (delta-time de aquí en adelante) a dt. 
	 * Si dt tiene un valor no válido lanza una excepción de tipo IllegalArgumentException.
	 * Además envía una notificación onDeltaTimeChanged a todos los observadores.
	 * @param dt Nuevo tiempo real por paso a aplicar en la simulación.
	 */
	public void setDeltaTime(double dt){
		if (dt <= 0) throw new IllegalArgumentException();

		this.dt = dt;

		for (SimulatorObserver ob : observers){
			ob.onDeltaTimeChanged(dt);
		}
	}

	/**
	 * Cambia las leyes de gravedad del simulador a gravityLaws. 
	 * Lanza una IllegalArgumentException si el valor no es válido, es decir, si es null.
	 * Además envía una notificación onGravityLawsChanged a todos los observadores.
	 * @param gl Las nuevas leyes de gravedad a aplicar en la simulación.
	 */
	public void setGravityLaws(GravityLaws gl){
		if (gl == null) throw new IllegalArgumentException();

		this.gl = gl;

		for (SimulatorObserver ob : observers){
			ob.onGravityLawChanged(gl.toString());
		}
	}

	/**
	 * Añade o a la lista de observadores, si no está ya en ella. 
	 * Además envía una notificación onRegister al observador que se acaba de registrar para pasarle el estado actual del simulador.
	 * @param o Nuevo observador que queremos añadir.
	 */
	public void addObserver(SimulatorObserver o) {
		if (!observers.contains(o)) {
			observers.add(o);
			o.onRegister(bodies, timeLapsed, dt, gl.toString());
		}
	}

	
	/**
	 * Añade el cuerpo b al simulador. 
	 * El metodo comprueba que no existe ningun otro cuerpo con el mismo identificador. Si existiera, lanza una excepción del tipo IllegalArgumentException. 
	 * Además envía una notificación onBodyAdded a todos los observadores.
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
	 * Aplica un paso de simulación, i.e., primero llama al método apply de las leyes de la gravedad, después llama a move de cada cuerpo, donde dt es el tiempo real por paso, y finalmente incrementa el tiempo actual en dt segundos.
	 * Además envía una notificación onAdvance a todos los observadores.
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
