package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector;
import simulator.model.Body;
import simulator.model.MassLossingBody;

public class MassLossingBodyBuilder extends Builder<Body> {

	public MassLossingBodyBuilder() {
		super("mlb", "Mass Lossing Body");
	}
	
	@Override
	protected Body createTheInstance(JSONObject data) {
		//Obtenemos el ID
		String id = data.getString("id");
		
		//Obtenemos la masa
		double mass = data.getDouble("mass");
		
		//Obtenemos factor de perdida de masa
		double lFactor = data.getDouble("factor");
		
		//Obtenemos el frecuencia de oerdida de masa
		double lFrequ = data.getDouble("freq");
		
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
		return new MassLossingBody(id, vel, acc, pos, mass, lFactor, lFrequ);
	}
	
	@Override
	protected JSONObject createData() {
		JSONObject data = new JSONObject();
		
		data.put("id", "identifier");
		data.put("pos", "position");
		data.put("vel", "velocity");
		data.put("mass", "mass_");
		data.put("freq", "frequency");
		data.put("factor", "reduction factor");
		
		return data;
	}
	
	public JSONObject getBuilderInfo() {
		JSONObject jsonObject = super.getBuilderInfo();
		JSONObject data = new JSONObject();
		
		data.append("id", "b1");
		JSONArray pos = new JSONArray();
		pos.put(-3.5e10);
		pos.put(0.0e00);
		data.append("pos", pos);
		JSONArray vel = new JSONArray();
		vel.put(0.0e00);
		vel.put(1.4e03);
		data.append("vel", vel);
		data.append("mass", 3.0e28);
		data.append("freq", 1e3);
		data.append("factor", 1e-3);
		
		
		jsonObject.put("data", data);
		
		return jsonObject;
	}

}
