package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;

import simulator.factories.Factory;
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
		
	}
	
	public void run(double dt) {
		
	}

	public void run(double dt, OutputStream out) {
		
	}
	
}
