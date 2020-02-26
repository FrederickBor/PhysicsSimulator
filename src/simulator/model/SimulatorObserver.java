package simulator.model;

import java.util.List;

/**
 * Para representar a los observadores del simulador vamos a emplear esta interfaz que incluye varios tipos de notificaciones. Los nombres de los métodos dan una idea del tipo de eventos que notifican.
 */
public interface SimulatorObserver {
	
	/**
	 * Notifica cuando registramos un nuevo oyente.
	 *  
	 * @param bodies La lista de cuerpos actual.
	 * @param time Es el tiempoa actual del simulador.
	 * @param dt Tiempo por paso actual del simulador.
	 * @param gLawsDesc Un String que describe las leyes de gravedad actuales (se obtiene invocando a toString() de la ley).
	 */
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc);
	
	/**
	 * Notifica hacemos un reset del simulador.
	 *  
	 * @param bodies La lista de cuerpos actual.
	 * @param time Es el tiempoa actual del simulador.
	 * @param dt Tiempo por paso actual del simulador.
	 * @param gLawsDesc Un String que describe las leyes de gravedad actuales (se obtiene invocando a toString() de la ley).
	 */
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc);
	
	/**
	 * Notifica cuando añadimos un nuevo cuerpo a la simulación.
	 *  
	 * @param bodies La lista de cuerpos actual.
	 * @param b El cuerpo que vamos a añadir.
	 */
	public void onBodyAdded(List<Body> bodies, Body b);
	
	/**
	 * Notifica cuando se invoca el método advance de PhysicsSimulator desde run de Controller.
	 *  
	 * @param bodies La lista de cuerpos actual.
	 * @param time Es el tiempoa actual del simulador.
	 */
	public void onAdvance(List<Body> bodies, double time);
	
	/**
	 * Notifica cuando cambiamos el "Tiempo real por paso" de la simulación.
	 *  
	 * @param dt Tiempo por paso actual del simulador.
	 */
	public void onDeltaTimeChanged(double dt);
	
	/**
	 * Notifica cuando cambiamos la ley de gravedad de la simulación.
	 *  
	 * @param gLawsDesc Un String que describe las leyes de gravedad actuales (se obtiene invocando a toString() de la ley).
	 */
	public void onGravityLawChanged(String gLawsDesc);
}
