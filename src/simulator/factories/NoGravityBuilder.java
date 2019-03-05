package simulator.factories;

import org.json.JSONObject;

import simulator.model.GravityLaws;
import simulator.model.NoGravity;

public class NoGravityBuilder extends Builder<GravityLaws> {

	public NoGravityBuilder() {
		super("ng","No Gravity Law");
	}
	
	@Override
	protected GravityLaws createTheInstance(JSONObject object) {
		// TODO Auto-generated method stub
		return new NoGravity();
	}
	
	public JSONObject getBuilderInfo() {
		JSONObject jsonObject = super.getBuilderInfo();
		jsonObject.put("data", new JSONObject());
		
		return jsonObject;
	}

}
