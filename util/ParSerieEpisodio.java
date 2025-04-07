import util.RegistroArvoreBMais;

import java.io.*;

public class ParSerieEpisodio implements RegistroArvoreBMais<ParSerieEpisodio> {
    private int idSerie;
    private int idEpisodio;

    public ParSerieEpisodio() {
        this(-1, -1);
    }

    public ParSerieEpisodio(int idSerie, int idEpisodio) {
        this.idSerie = idSerie;
        this.idEpisodio = idEpisodio;
    }

    public int getIdSerie() {
        return idSerie;
    }

    public int getIdEpisodio() {
        return idEpisodio;
    }

    @Override
    public short size() {
        return 8;
    }

    @Override
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(ba);
        dos.writeInt(idSerie);
        dos.writeInt(idEpisodio);
        return ba.toByteArray();
    }

    @Override
    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bb = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bb);
        idSerie = dis.readInt();
        idEpisodio = dis.readInt();
    }

    @Override
    public int compareTo(ParSerieEpisodio o) {
        int c = Integer.compare(this.idSerie, o.idSerie);
        if (c != 0) return c;
        return Integer.compare(this.idEpisodio, o.idEpisodio);
    }

    @Override
    public ParSerieEpisodio clone() {
        return new ParSerieEpisodio(this.idSerie, this.idEpisodio);
    }

    @Override
    public String toString() {
        return "(" + idSerie + ", " + idEpisodio + ")";
    }
}
