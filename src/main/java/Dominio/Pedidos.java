package Dominio;

import java.util.ArrayList;
import java.util.List;

public class Pedidos {
	private List<Pedido> pedidos;

	public Pedidos() {
		this.pedidos = new ArrayList<>();
	}

	public void agregarPedido(Pedido pedido) {
		pedidos.add(pedido);
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public static class Pedido {
		private Cliente cliente;
		private String menu;

		public Pedido(Cliente cliente, String menu) {
			this.cliente = cliente;
			this.menu = menu;
		}

		public Cliente getCliente() {
			return cliente;
		}

		public String getMenu() {
			return menu;
		}
	}

	public void procesarPedido(Cliente cliente, String tipoMenu) {
		if (tipoMenu != null) {
			Pedido pedido = new Pedido(cliente, tipoMenu);
			agregarPedido(pedido);
			System.out.println("Pedido registrado exitosamente.");
		}
	}
}
