package simulator.factories;

import org.json.JSONObject;

import simulator.model.GravityLaws;
import simulator.model.NewtonUniversalGravity;;

public class NewtonUniversalGravitationBuilder extends Builder<GravityLaws> {

	public NewtonUniversalGravitationBuilder() {
		desc = "Newton's Law of Universal Gravitation";
		typeTag = "nlug";
	}
	
	@Override
	protected GravityLaws createTheInstance(JSONObject object) {
		// TODO Auto-generated method stub
		return new NewtonUniversalGravity();
	}

	public JSONObject getBuilderInfo() {
		JSONObject jsonObject = super.getBuilderInfo();
		jsonObject.put("data", new JSONObject());
		
		return jsonObject;
	}
}
