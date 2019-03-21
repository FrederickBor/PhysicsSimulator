package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

/**
 * Esta clase implementa una factoría de obtejos basada de en Builders de tipo T (Bodies y GravityLaws). Tiene como atributo una lista de constructores de los tipos T ya mencionados.
 * 
 * @param <T> En el contexto del simulador de física este parámetro van a ser objetos de las clases Body (y subclases) y GravityLaws (y subclases).
 */
public class BuilderBasedFactory<T> implements Factory<T> {

	List<Builder<T>> builderList;
	
	/**
	 * Constructor de la clase.
	 * 
	 * @param builderList Lista de constructores de clases (y subclases) de tipo T (Bodies y GravityLaws).
	 */
	public BuilderBasedFactory(List<Builder<T>> builderList) {
		this.builderList = builderList;
		
	}
	
	@Override
	public T createInstance(JSONObject info) throws IllegalArgumentException {
		for (Builder<T> b : builderList) {
			if (b.getTypeTag().equals(info.get("type"))) {
				return b.createInstance(info);
			}
		}
		
		throw new IllegalArgumentException("Algo ha ido mal con los contructores...");
	}

	@Override
	public List<JSONObject> getInfo() {
		List<JSONObject> jsonObjects = new ArrayList<JSONObject>();
		
		for (Builder<T> b : builderList) {
			jsonObjects.add(b.getBuilderInfo());
		}
		
		return jsonObjects;
	}

}
