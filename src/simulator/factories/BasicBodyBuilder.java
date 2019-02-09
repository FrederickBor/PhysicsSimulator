package simulator.factories;

import org.json.JSONObject;

import simulator.model.Body;

public class BasicBodyBuilder extends Builder<Body> {

	public BasicBodyBuilder() {
		desc = "Basic Body";
		typeTag = "basic";
	}
	
	@Override
	protected Body createTheInstance(JSONObject object) {
		// TODO Auto-generated method stub
		return null;
	}

}
