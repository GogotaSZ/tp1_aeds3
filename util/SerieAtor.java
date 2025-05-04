package util;

import java.io.*;

public class SerieAtor implements RegistroArvoreBMais<SerieAtor> {
    private int idSerie;
    private int idAtor;

    public SerieAtor() {
        this(-1, -1);
    }

    public SerieAtor(int idSerie, int idAtor) {
        this.idSerie = idSerie;
        this.idAtor = idAtor;
    }

    public int getIdSerie() {
        return idSerie;
    }

    public int getIdAtor() {
        return idAtor;
    }

    @Override
    public SerieAtor clone() {
        return new SerieAtor(idSerie, idAtor);
    }

    @Override
    public short size() {
        return 8; // dois inteiros
    }

    @Override
    public int compareTo(SerieAtor other) {
        return Integer.compare(this.idSerie, other.idSerie);
    }

    @Override
    public void fromByteArray(byte[] ba) throws IOException {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(ba));
        idSerie = dis.readInt();
        idAtor = dis.readInt();
    }

    @Override
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(idSerie);
        dos.writeInt(idAtor);
        return baos.toByteArray();
    }

    @Override
    public String toString() {
        return "SerieID: " + idSerie + " -> AtorID: " + idAtor;
    }
}
