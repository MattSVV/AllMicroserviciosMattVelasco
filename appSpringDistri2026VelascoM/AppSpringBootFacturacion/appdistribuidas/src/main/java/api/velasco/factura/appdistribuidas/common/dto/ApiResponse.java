/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.velasco.factura.appdistribuidas.common.dto;

import java.time.LocalDateTime;

public class ApiResponse<T> {
        
    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    // Constructores
    public ApiResponse() {
    }

    public ApiResponse(boolean success, String message, T data, LocalDateTime timestamp) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.timestamp = timestamp;
    }

    // Getters y Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    // Métodos estáticos
    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(
                true,
                "Operación exitosa",
                data,
                LocalDateTime.now()
        );
    }

    public static <T> ApiResponse<T> ok(T data, String mensaje) {
        return new ApiResponse<>(
                true,
                mensaje,
                data,
                LocalDateTime.now()
        );
    }

    public static <T> ApiResponse<T> error(String mensaje) {
        return new ApiResponse<>(
                false,
                mensaje,
                null,
                LocalDateTime.now()
        );
    }

    public static <T> ApiResponse<T> error(String mensaje, T data) {
        return new ApiResponse<>(
                false,
                mensaje,
                data,
                LocalDateTime.now()
        );
    }
}
