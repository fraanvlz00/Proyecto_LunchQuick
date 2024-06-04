package Dominio;

public class InicioSesion {
	private Object _correoInstitucional;
	private Object _contrasena;
	private Object _pedidos;
	public Cliente _unnamed_Cliente_;
	public UsuarioAdmin _unnamed_UsuarioAdmin_;

	public void inicioSesion() {
		throw new UnsupportedOperationException();
	}

	public void setCorreoInstitucional(Object aCorreoInstitucional) {
		this._correoInstitucional = aCorreoInstitucional;
	}

	public void getCorreoInstitucional() {
		return this._correoInstitucional;
	}

	public void setContrasena(Object aContrasena) {
		this._contrasena = aContrasena;
	}

	public void getContrasena() {
		return this._contrasena;
	}
}