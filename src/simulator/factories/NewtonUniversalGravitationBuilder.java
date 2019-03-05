package simulator.factories;

import org.json.JSONObject;

import simulator.model.GravityLaws;
import simulator.model.NewtonUniversalGravity;;

public class NewtonUniversalGravitationBuilder extends Builder<GravityLaws> {

	public NewtonUniversalGravitationBuilder() {
		super("nlug","Newton's Law of Universal Gravitation");
	}
	
	@Override
	protected GravityLaws createTheInstance(JSONObject object) {
		return new NewtonUniversalGravity();
	}

	public JSONObject getBuilderInfo() {
		JSONObject jsonObject = super.getBuilderInfo();
		jsonObject.put("data", new JSONObject());
		
		return jsonObject;
	}
}
