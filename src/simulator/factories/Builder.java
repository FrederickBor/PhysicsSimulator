package simulator.factories;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Builder<T> {
	
	protected String typeTag, desc;
	
	private List<Builder<T>> objects;
	
	public Builder() {
		
	}
	
	protected double[] jsonArrayToDoubleArray(JSONArray array) {
		
		return null;		
	}
	
	//Utilizo el SuppressWarnings para quitar la advertencia en la linea
	// object = (T) builder.createTheInstance(info);
	public T createInstance(JSONObject info) throws IllegalArgumentException {
		if(info.length() == 2 && info.has("Type") && info.has("Data")) {
			T object = null;
			
			for (Builder<T> builder : objects) {
				if (builder.getTypeTag().equals(info.getString("Type"))) {
					object = builder.createTheInstance(info.getJSONObject("Data"));
				}
			}
			
			if (object == null) {throw new IllegalArgumentException();}
			
			return object;
		}
		else {
			return null;
		}
	}
	
	public JSONObject getBuilderInfo() {
		
		return null;
	}
	
	protected JSONObject createData() {
		
		return null;
	}
	
	protected String getTypeTag() { return typeTag;}
	
	protected String getDesc() { return desc;}
	
	protected abstract T createTheInstance(JSONObject object);
}