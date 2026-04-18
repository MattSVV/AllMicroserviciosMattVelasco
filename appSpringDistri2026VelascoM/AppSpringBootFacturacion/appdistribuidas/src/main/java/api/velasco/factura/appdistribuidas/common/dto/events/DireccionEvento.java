/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.velasco.factura.appdistribuidas.common.dto.events;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author Mateo Velasco
 */
public class DireccionEvento {
    @JsonProperty("nombre_completo")
    private String nombreCompleto;
    private String direccion;
    // Getters y Setters clásicos

    public DireccionEvento() {
    }

    public DireccionEvento(String nombreCompleto, String direccion) {
        this.nombreCompleto = nombreCompleto;
        this.direccion = direccion;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
}
