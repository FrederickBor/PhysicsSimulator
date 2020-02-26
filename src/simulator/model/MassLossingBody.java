package simulator.model;

import simulator.misc.Vector;

public class MassLossingBody extends Body {
	
	protected double lossFactor;
	protected double lossFrequency;
	protected double contador;

	public MassLossingBody() {
		super();
		this.lossFactor = 0.0;
		this.lossFrequency = 0.0;
		this.contador = 0.0;
	}

	public MassLossingBody(String id, Vector vel, Vector acc, Vector pos, double mass, double lFactor, double lFrequ) {
		super(id, vel, acc, pos, mass);
		this.lossFactor = lFactor;
		this.lossFrequency = lFrequ;
		this.contador = 0.0;
	}
	
	public double getLossFactor() {
		return this.lossFactor;
	}
	
	public double getLossFrequency() {
		return this.lossFrequency;
	}
	
	/**
	 * Metodo que mueve el objeto y le aplica la ley de gravedad activa en la simulacion.
	 * En este caso llama al metodo move() de la clase padre y si ademas ha trancurrido el
	 * tiempo necesario reduce la masa del cuerpo.
	 * 
	 * @param t Intervalo de tiempo que transcurre en el movimiento.
	 */
	void move(double t) {
		super.move(t);
		this.contador += t;
		if (contador >= lossFrequency) {
			this.mass *= (1 - lossFactor);
			this.contador = 0.0;
		}
			
	}

}
