package simulator.factories;

import org.json.JSONObject;

import simulator.model.GravityLaws;
import simulator.model.NoGravity;

public class NoGravityBuilder extends Builder<GravityLaws> {

	public NoGravityBuilder() {
		desc = "No Gravity Law";
		typeTag = "ng";
	}
	
	@Override
	protected GravityLaws createTheInstance(JSONObject object) {
		// TODO Auto-generated method stub
		return new NoGravity();
	}

}