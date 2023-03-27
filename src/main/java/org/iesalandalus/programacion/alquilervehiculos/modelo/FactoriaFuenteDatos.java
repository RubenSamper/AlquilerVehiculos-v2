package org.iesalandalus.programacion.alquilervehiculos.modelo;

import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IFuenteDatos;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros.FuenteDatosMemoria;

public enum FactoriaFuenteDatos {

	FICHEROS {
		
		@Override
		public IFuenteDatos crear() {
			// TODO Auto-generated method stub
			return new FuenteDatosMemoria();
		}
	};
	
	public abstract IFuenteDatos crear();
}
