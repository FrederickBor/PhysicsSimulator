package simulator.factories;


import org.json.JSONObject;

import simulator.model.FallingToCenterGravity;
import simulator.model.GravityLaws;

public class FallingToCenterGravityBuilder extends Builder<GravityLaws> {

	public FallingToCenterGravityBuilder() {
		desc = "Falling to Center Gravity Law";
		typeTag = "ftcg";
	}
	
	@Override
	protected GravityLaws createTheInstance(JSONObject object) {
		return new FallingToCenterGravity();
	}
	
	public JSONObject getBuilderInfo() {
		JSONObject jsonObject = super.getBuilderInfo();
		jsonObject.put("data", new JSONObject());
		
		return jsonObject;
	}

}
