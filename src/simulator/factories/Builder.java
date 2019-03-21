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
	
	/**
	 * Si la información suministrada por info es correcta, entonces crea un objeto de tipo T (i.e., una instancia de una subclase de T). En otro caso devuelve null para indicar que este constructor es incapaz de reconocer ese formato.
	 * En caso de que reconozca el campo type pero haya un error en alguno de los valores suministrados por la sección data, el método lanza una excepción IllegalArgumentException.
	 * 
	 * @param info Estructura JSON que describe el objeto a crear.
	 * @return Una instancia de la clase correspondiente - una instancia de un subtipo de T.
	 * @throws IllegalArgumentException En caso de que haya un error en alguno de los valores suministrados por la sección data.
	 */
	public T createInstance(JSONObject info) throws IllegalArgumentException {
		T b = null;
		if (typeTag != null && typeTag.equals(info.getString("type")))
			b = createTheInstance(info.getJSONObject("data"));
		return b;
	}
	
	/**
	 * Devuelve un objeto JSON que sirve de plantilla para el correspondiente constructor, i.e., un valor válido para el parámetro de createInstance.
	 * 
	 * @return Un objeto JSON que sirve de plantilla para el correspondiente constructor.
	 */
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
