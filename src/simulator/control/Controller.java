package simulator.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
		
		sb.append("{\"states\": [");
		
		for (int i = 0; i < Main.getSteps(); i++) {
			ps.advance();
			sb.append(ps.toString());
			if (i != Main.getSteps() -1 ) sb.append(",");  
		}
		
		sb.append("] }");
		
		if(out != null) {
			for (Byte b : sb.toString().getBytes()) {
				out.write(b);
			}
		}
		else {
			System.out.println(sb.toString());
		}
	}
	
}
