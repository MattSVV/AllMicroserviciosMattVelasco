/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.velasco.factura.appdistribuidas.common.exception;

/**
 *
 * @author Mateo Velasco
 */
public class ApiException extends RuntimeException {
        private static final long serialVersionUID = 1L;
    
    private String mensaje;
    private int codigo;

    /**
     * Constructor con mensaje
     */
    public ApiException(String mensaje) {
        super(mensaje);
        this.mensaje = mensaje;
        this.codigo = 400;
    }

    /**
     * Constructor con mensaje y código de error
     */
    public ApiException(String mensaje, int codigo) {
        super(mensaje);
        this.mensaje = mensaje;
        this.codigo = codigo;
    }

    /**
     * Constructor con mensaje y causa
     */
    public ApiException(String mensaje, Throwable causa) {
        super(mensaje, causa);
        this.mensaje = mensaje;
        this.codigo = 400;
    }

    /**
     * Constructor completo
     */
    public ApiException(String mensaje, int codigo, Throwable causa) {
        super(mensaje, causa);
        this.mensaje = mensaje;
        this.codigo = codigo;
    }

    // Getters
    public String getMensaje() {
        return mensaje;
    }

    public int getCodigo() {
        return codigo;
    }

}
