package modelo;

public interface Registro {
    int getId();
    void setId(int id);
    byte[] toByteArray() throws Exception;
    void fromByteArray(byte[] ba) throws Exception;
}