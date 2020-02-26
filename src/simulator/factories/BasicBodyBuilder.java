package simulator.factories;

import simulator.misc.Vector;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.Body;

public class BasicBodyBuilder extends Builder<Body> {

	public BasicBodyBuilder() {
		super("basic","Basic Body");
	}
	
	@Override
	protected Body createTheInstance(JSONObject data) {
		//Obtenemos el ID
		String id = data.getString("id");
		
		//Obtenemos la masa
		double mass = data.getDouble("mass");
		
		//Obtenemos los datos del vector posicion y lo creamos
		Vector pos = new Vector(jsonArrayToDoubleArray(data.getJSONArray("pos")));
		
		//Obtenemos los datos del vector velocidad y lo creamos
		Vector vel = new Vector(jsonArrayToDoubleArray(data.getJSONArray("vel")));
		
		//Creamos un vector vacio para la aceleracion
		Vector acc = new Vector(jsonArrayToDoubleArray(data.getJSONArray("vel")).length);
		
		//Creamos el nuevo cuerpo
		return new Body(id, vel, acc, pos, mass);
	}
	
	@Override
	protected JSONObject createData() {
		JSONObject data = new JSONObject();
		
		data.put("id", "identifier");
		data.put("pos", "position");
		data.put("vel", "velocity");
		data.put("mass", "mass_");
		
		return data;
	}
	
	public JSONObject getBuilderInfo() {
		JSONObject jsonObject = super.getBuilderInfo();
		JSONObject data = new JSONObject();
		
		data.append("id", "b1");
		JSONArray pos = new JSONArray();
		pos.put(0.0e00);
		pos.put(0.0e00);
		data.append("pos", pos);
		JSONArray vel = new JSONArray();
		vel.put(0.5e04);
		vel.put(0.0e00);
		data.append("vel", vel);
		data.append("mass", 5.97e24);
		
		
		jsonObject.put("data", data);
		
		return jsonObject;
	}

}
