package simulator.factories;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Builder<T> {
	
	protected String typeTag, desc;
	
	protected List<Builder<T>> objects;
	
	public Builder(String typeTag, String desc) {
		this.typeTag = typeTag;
		this.desc = desc;
	}
	
	protected double[] jsonArrayToDoubleArray(JSONArray array) {
		
		double[] ret = new double[array.length()];
		
		for (int i = 0; i < ret.length; i++) {
			ret[i] = array.getDouble(i);
		}
		
		return ret;
	}
	
	public T createInstance(JSONObject info) throws IllegalArgumentException {
		T b = null;
		if (typeTag != null && typeTag.equals(info.getString("type")))
		b = createTheInstance(info.getJSONObject("data"));
		return b;
	}
	
	public JSONObject getBuilderInfo() {
		
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("type", typeTag);
		jsonObject.put("data",createData());
		jsonObject.put("desc", desc);
		
		return jsonObject;
	}
	
	protected JSONObject createData() { return new JSONObject(); }
	
	protected String getTypeTag() { return typeTag;}
	
	protected String getDesc() { return desc;}
	
	protected abstract T createTheInstance(JSONObject object);
}
