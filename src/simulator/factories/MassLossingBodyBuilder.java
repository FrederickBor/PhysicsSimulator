package simulator.factories;

import org.json.JSONObject;

import simulator.model.Body;
import simulator.model.MassLossingBody;

public class MassLossingBodyBuilder extends Builder<Body> {

	public MassLossingBodyBuilder() {
		desc = "Mass Lossing Body";
		typeTag = "mlb";
	}
	
	@Override
	protected Body createTheInstance(JSONObject object) {
		// TODO Auto-generated method stub
		return new MassLossingBody();
	}

}
