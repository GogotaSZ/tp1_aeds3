package util;

import java.io.*;

public class AtorSerie implements RegistroArvoreBMais<AtorSerie> {
    private int idAtor;
    private int idSerie;

    public AtorSerie() {
        this(-1, -1);
    }

    public AtorSerie(int idAtor, int idSerie) {
        this.idAtor = idAtor;
        this.idSerie = idSerie;
    }

    public int getIdAtor() {
        return idAtor;
    }

    public int getIdSerie() {
        return idSerie;
    }

    @Override
    public AtorSerie clone() {
        return new AtorSerie(idAtor, idSerie);
    }

    @Override
    public short size() {
        return 8;
    }

    @Override
    public int compareTo(AtorSerie other) {
        return Integer.compare(this.idAtor, other.idAtor);
    }

    @Override
    public void fromByteArray(byte[] ba) throws IOException {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(ba));
        idAtor = dis.readInt();
        idSerie = dis.readInt();
    }

    @Override
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(idAtor);
        dos.writeInt(idSerie);
        return baos.toByteArray();
    }

    @Override
    public String toString() {
        return "AtorID: " + idAtor + " -> SerieID: " + idSerie;
    }
}
