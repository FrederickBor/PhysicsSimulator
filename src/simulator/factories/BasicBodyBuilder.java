package simulator.factories;

import simulator.misc.Vector;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.Body;

public class BasicBodyBuilder extends Builder<Body> {

	public BasicBodyBuilder() {
		desc = "Basic Body";
		typeTag = "basic";
	}
	
	@Override
	protected Body createTheInstance(JSONObject data) {
		//Obtenemos el ID
		String id = data.getString("id");
		
		//Obtenemos la masa
		double mass = data.getDouble("mass");
		
		//Obtenemos los datos del vector posicion y lo creamos
		JSONArray aux = data.getJSONArray("pos");
		double [] arrayData = new double[data.length()];
		for (int i = 0; i < aux.length(); i++) {
		     arrayData[i] = aux.getLong(i); 
		}
		Vector pos = new Vector(arrayData);
		
		//Obtenemos los datos del vector velocidad y lo creamos
		aux = data.getJSONArray("vel");
		arrayData = new double[data.length()];
		for (int i = 0; i < aux.length(); i++) {
		     arrayData[i] = aux.getLong(i); 
		}
		Vector vel = new Vector(arrayData);
		
		//Creamos un vector vacio para la aceleracion
		Vector acc = new Vector(data.length());
		
		//Creamos el nuevo cuerpo
		return new Body(id, vel, acc, pos, mass);
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
