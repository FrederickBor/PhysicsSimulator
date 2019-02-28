package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector;
import simulator.model.Body;
import simulator.model.MassLossingBody;

public class MassLossingBodyBuilder extends Builder<Body> {

	public MassLossingBodyBuilder() {
		desc = "Mass Lossing Body";
		typeTag = "mlb";
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

}
