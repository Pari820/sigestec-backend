package pe.edu.sigestec.exception;


import org.springframework.http.HttpStatus;

public class NegocioException extends RuntimeException {
    private final HttpStatus status;

    public NegocioException(HttpStatus status, String mensaje) {
        super(mensaje);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public static NegocioException noExiste(String msg) {
        return new NegocioException(HttpStatus.NOT_FOUND, msg);
    }

    public static NegocioException invalido(String msg) {
        return new NegocioException(HttpStatus.BAD_REQUEST, msg);
    }

    public static NegocioException conflicto(String msg) {
        return new NegocioException(HttpStatus.CONFLICT, msg);
    }
}
