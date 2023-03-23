package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IAlquileres;

public class Alquileres implements IAlquileres {

	private List<Alquiler> coleccionAlquileres;

	public Alquileres() {

		coleccionAlquileres = new ArrayList<>();
	}

	@Override
	public List<Alquiler> get() {
		return new ArrayList<>(coleccionAlquileres);
	}

	@Override
	public List<Alquiler> get(Cliente cliente) {
		List<Alquiler> listaTemporal = new ArrayList<>();

		for (Alquiler alquiler : coleccionAlquileres) {
			if (alquiler.getCliente().equals(cliente)) {
				listaTemporal.add(alquiler);
			}
		}
		return listaTemporal;

	}

	@Override
	public List<Alquiler> get(Vehiculo turismo) {
		List<Alquiler> listaTemporal = new ArrayList<>();

		for (Alquiler alquiler : coleccionAlquileres) {
			if (alquiler.getVehiculo().equals(turismo)) {
				listaTemporal.add(alquiler);
			}
		}
		return listaTemporal;
	}

	@Override
	public int getCantidad() {
		return coleccionAlquileres.size();
	}

	private void comprobarAlquiler(Cliente cliente, Vehiculo turismo, LocalDate fechaAlquiler)
			throws OperationNotSupportedException {
		for (Alquiler alquiler : coleccionAlquileres) {
			if (alquiler.getFechaDevolucion() == null) {
				// AUN NO SE HA DEVUELTO, SI ENTRA POR EL BLOQUE DE ELSE SI SE DEVUELVE
				if (alquiler.getCliente().equals(cliente)) {
					throw new OperationNotSupportedException("ERROR: El cliente tiene otro alquiler sin devolver.");
				}
				if (alquiler.getVehiculo().equals(turismo)) {
					throw new OperationNotSupportedException("ERROR: El turismo está actualmente alquilado.");

				}
			} else {
				if (alquiler.getCliente().equals(cliente) && (alquiler.getFechaDevolucion().isAfter(fechaAlquiler)
						|| alquiler.getFechaDevolucion() == fechaAlquiler)) {
					throw new OperationNotSupportedException("ERROR: El cliente tiene un alquiler posterior.");
				}
				if (alquiler.getVehiculo().equals(turismo) && (alquiler.getFechaDevolucion().isAfter(fechaAlquiler)
						|| alquiler.getFechaDevolucion() == fechaAlquiler)) {
					throw new OperationNotSupportedException("ERROR: El turismo tiene un alquiler posterior.");
				}
			}
		}
	}

	@Override
	public void insertar(Alquiler alquiler) throws OperationNotSupportedException {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede insertar un alquiler nulo.");
		}

		comprobarAlquiler(alquiler.getCliente(), alquiler.getVehiculo(), alquiler.getFechaAlquiler());
		coleccionAlquileres.add(alquiler);
	}

	@Override
	public void devolver(Cliente cliente, LocalDate fechaDevolucion) throws OperationNotSupportedException {

		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede devolver un alquiler de un cliente nulo.");
		}
		if (getAlquilerAbierto(cliente) == null) {
			throw new OperationNotSupportedException("ERROR: No existe ningún alquiler abierto para ese cliente.");
		}
	}

	private Alquiler getAlquilerAbierto(Cliente cliente) {
		Alquiler alquilerEncontrado = null;
		Iterator<Alquiler> iterador = coleccionAlquileres.iterator();

		while (iterador.hasNext() && alquilerEncontrado == null) {
			Alquiler alquilerRecorrido = iterador.next();

			if (alquilerRecorrido.getCliente().equals(cliente) && alquilerRecorrido.getFechaDevolucion() == null) {
				alquilerEncontrado = alquilerRecorrido;
			}
		}
		return alquilerEncontrado;
	}

	@Override
	public void devolver(Vehiculo vehiculo, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: No se puede devolver un alquiler de un vehículo nulo.");
		}

		if (getAlquilerAbierto(vehiculo) == null) {
			throw new OperationNotSupportedException("ERROR: No existe ningún alquiler abierto para ese vehículo.");
		}
	}

	private Alquiler getAlquilerAbierto(Vehiculo vehiculo) {
		Alquiler alquilerEncontrado = null;
		Iterator<Alquiler> iterador = coleccionAlquileres.iterator();

		while (iterador.hasNext() && alquilerEncontrado == null) {
			Alquiler alquilerRecorrido = iterador.next();

			if (alquilerRecorrido.getVehiculo().equals(vehiculo) && alquilerRecorrido.getFechaDevolucion() == null) {
				alquilerEncontrado = alquilerRecorrido;
			}
		}
		return alquilerEncontrado;
	}

	@Override
	public void borrar(Alquiler alquileres) throws OperationNotSupportedException {
		if (alquileres == null) {
			throw new NullPointerException("ERROR: No se puede borrar un alquiler nulo.");
		}
		int indice = coleccionAlquileres.indexOf(alquileres);
		if (indice == -1) {
			throw new OperationNotSupportedException("ERROR: No existe ningún alquiler igual.");
		}
		coleccionAlquileres.remove(indice);
	}

	@Override
	public Alquiler buscar(Alquiler alquiler) {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede buscar un alquiler nulo.");
		}
		int indice = coleccionAlquileres.indexOf(alquiler);
		Alquiler aux = null;
		if (indice != -1) {
			aux = coleccionAlquileres.get(indice);
		}
		return aux;
	}

}
