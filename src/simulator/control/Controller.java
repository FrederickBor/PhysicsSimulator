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
import simulator.model.PhysicsSimulator;

public class Controller {
	
	PhysicsSimulator ps;
	Factory<Body> bodyFactory;
	
	public Controller(PhysicsSimulator ps, Factory<Body> bodyFactory) {
		this.ps = ps;
		this.bodyFactory = bodyFactory;
	}
	
	public void loadBodies(InputStream in) {
		JSONObject jsonInput = new JSONObject(new JSONTokener(in));
		
		JSONArray bodiesArray = jsonInput.getJSONArray("bodies");
		
		for (int i = 0; i < bodiesArray.length(); i++) {
		    JSONObject body = bodiesArray.getJSONObject(i);
		    
		    ps.addBody(bodyFactory.createInstance(body));
		    
		}

		
	}

	public void run(double dt, OutputStream out) throws IOException{
		StringBuilder sb = new StringBuilder();
		
		sb.append("{\n\"states\" : [\n");
		
		sb.append(ps.toString());
		sb.append(",");
		
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

	public void reset(){
		ps.reset();
	}

	public void setDeltaTime(double dt){
		ps.setDeltaTime(dt);
	}

	public void addObserver(SimulatorObserver o){
		ps.addObserver(o);
	}

	public void run(int n){
		for (int i = 0; i<n; i++){
			ps.advance();
		}
	}

	public Factory<GravityLaws> getGravityLawsFactory(){
		//POR IMPLEMENTAR
	}

	public void setGravityLaws(JSONObject info){
		//POR IMPLEMENTAR
	}
	
}
