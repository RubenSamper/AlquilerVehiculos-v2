package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros;

import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IClientes;

public class Clientes implements IClientes {

	// private Cliente cliente;

	private List<Cliente> coleccionClientes;

	public Clientes() {

		coleccionClientes = new ArrayList<>();

	}

	@Override
	public List<Cliente> get() {
		return new ArrayList<>(coleccionClientes);
	}

	@Override
	public int getCantidad() {
		return coleccionClientes.size();
	}

	@Override
	public void insertar(Cliente cliente) throws OperationNotSupportedException {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede insertar un cliente nulo.");
		}
		int indice = coleccionClientes.indexOf(cliente);

		if (indice != -1) {
			throw new OperationNotSupportedException("ERROR: Ya existe un cliente con ese DNI.");

		}

		coleccionClientes.add(cliente);
	}

	@Override
	public Cliente buscar(Cliente cliente) {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede buscar un cliente nulo.");

		}
		int indice = coleccionClientes.indexOf(cliente);
		Cliente aux = null;
		if (indice != -1) {
			aux = coleccionClientes.get(indice);
		}
		return aux;
	}

	@Override
	public void borrar(Cliente cliente) throws OperationNotSupportedException {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede borrar un cliente nulo.");
		}
		int indice = coleccionClientes.indexOf(cliente);
		if (indice == -1) {
			throw new OperationNotSupportedException("ERROR: No existe ningún cliente con ese DNI.");
		}

		coleccionClientes.remove(indice);
	}

	@Override
	public void modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException {

		int indice = coleccionClientes.indexOf(cliente);

		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede modificar un cliente nulo.");
		}

		if (indice == -1) {
			throw new OperationNotSupportedException("ERROR: No existe ningún cliente con ese DNI.");
		}

		if ((nombre != null) && (!nombre.isBlank())) {
			buscar(cliente).setNombre(nombre);
		}
		if ((telefono != null) && (!telefono.isBlank())) {
			buscar(cliente).setTelefono(telefono);
		}
	}
}