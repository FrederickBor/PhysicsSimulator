package simulator.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.launcher.Main;
import simulator.model.Body;
import simulator.model.GravityLaws;
import simulator.model.PhysicsSimulator;
import simulator.model.SimulatorObserver;

public class Controller {
	
	PhysicsSimulator ps;
	Factory<Body> bodyFactory;
	Factory<GravityLaws> gravityLawsFactory;
	
	public Controller(PhysicsSimulator ps, Factory<Body> bodyFactory, Factory<GravityLaws> gravityLawsFactory) {
		this.ps = ps;
		this.bodyFactory = bodyFactory;
		this.gravityLawsFactory = gravityLawsFactory;
	}
	
	public void loadBodies(InputStream in) {
		JSONObject jsonInput = new JSONObject(new JSONTokener(in));
		
		JSONArray bodiesArray = jsonInput.getJSONArray("bodies");
		
		for (int i = 0; i < bodiesArray.length(); i++) {
		    JSONObject body = bodiesArray.getJSONObject(i);
		    
		    ps.addBody(bodyFactory.createInstance(body));
		    
		}

		
	}

	/**
	 * Ejecuta el simulador n pasos y muestra los diferentes estados en out, utilizando el siguiente formato JSON:
	 * 
	 * { "states": [s0, s1, ... , sn] }
	 * 
	 * donde s0 es el estado del simulador antes de ejecutar ninún paso, y cada Si con i >= 1, es el estado del simulador inmediatamente después de ejecutar el i-ésimo paso de simulación.
	 * 
	 * @param dt Representa el "Tiempo real por paso" de simulación.
	 * @param out Indica donde vamos a obtener la salida de los datos de la simulación. Si es null será por consola, sino en el archivo especificado por la ruta pasada por parámetro.
	 * @throws IOException En caso de cualquier problema de E/S.
	 */
	public void run(double dt, OutputStream out) throws IOException{
		StringBuilder sb = new StringBuilder();
		
		sb.append("{\n\"states\" : [\n");
		
		sb.append(ps.toString());
		sb.append(",");
		sb.append('\n');
		
		for (int i = 0; i < Main.getSteps(); i++) {
			ps.advance();
			sb.append(ps.toString());
			if (i != Main.getSteps() -1 ) sb.append(",\n");  
		}
		
		sb.append("\n]}");
		
		PrintStream p = (out == null) ? null : new PrintStream(out);
		
		if(p != null) {
			p.print(sb.toString());
		}
		else {
			System.out.println(sb.toString());
		}
		
		if (p!=null) p.close();
	}

	/**
	 * Invoca al método reset del simulador.
	 */
	public void reset(){
		ps.reset();
	}

	/**
	 * Invoca al método setDeltaTime del simulador.
	 * @param dt "Tiempo real por paso" que le pasamos al método de PhysicsSimulator.
	 */
	public void setDeltaTime(double dt){
		ps.setDeltaTime(dt);
	}

	/**
	 * Invoca al método addObserver del simulador.
	 * @param o Nuevo observador que queremos añadir y le pasamos al método de PhysicsSimulator.
	 */
	public void addObserver(SimulatorObserver o){
		ps.addObserver(o);
	}

	/**
	 * Ejecuta n pasos del simulador sin escribir nada en consola.
	 * @param n Número de pasos que queremos que se ejecuten en la simulación.
	 */
	public void run(int n){
		for (int i = 0; i<n; i++){
			ps.advance();
		}
	}

	/**
	 * Devuelve la factoría de leyes de gravedad.
	 * @return La factoría de leyes de gravedad que se va a usar en la simulación.
	 */
	public Factory<GravityLaws> getGravityLawsFactory(){
		return gravityLawsFactory;
	}

	/**
	 * Usa la factoría de leyes de gravedad actual para crear un nuevo objeto de tipo GravityLaws a partir de info y cambia las leyes de la gravedad del simulador por él.
	 * @param info Estructura JSON que describe el objeto a crear.
	 */
	public void setGravityLaws(JSONObject info){
		GravityLaws newGravityLaw = gravityLawsFactory.createInstance(info); 
		ps.setGravityLaws(newGravityLaw);
	}
	
	public int getBodiesQuantity() {
		return ps.getBodiesQuantity();
	}
	
	public String getCurrentActiveGL() {
		return ps.getGravityLaws();
	}
	
}
