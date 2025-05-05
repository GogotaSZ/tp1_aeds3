package modelo;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import util.Registro;
import util.RegistroHashExtensivel;

public class Episodio implements RegistroHashExtensivel<Episodio>, Registro{

    private int id;
    private int idSerie;
    private String nome;
    private int temporada;
    private LocalDate dataLancamento;
    private int duracao;
    private String sinopse;

    public Episodio() {
        this.id = -1;
        this.idSerie = -1;
        this.nome = "";
        this.temporada = 0;
        this.dataLancamento = LocalDate.now();
        this.duracao = 0;
        this.sinopse = "";
    }

    public Episodio(int idSerie, String nome, int temporada, LocalDate dataLancamento, int duracao, String sinopse) {
        this.id = -1;
        this.idSerie = idSerie;
        this.nome = nome;
        this.temporada = temporada;
        this.dataLancamento = dataLancamento;
        this.duracao = duracao;
        this.sinopse = sinopse;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdSerie() {
        return idSerie;
    }

    public void setIdSerie(int idSerie) {
        this.idSerie = idSerie;
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public String getSinopse() {
        return sinopse;
    }

    public int getTemporada() { return temporada;}

    // Serialização
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeInt(id);
        dos.writeInt(idSerie);
        dos.writeUTF(nome);
        dos.writeInt(temporada);
        dos.writeLong(dataLancamento.toEpochDay());
        dos.writeInt(duracao);
        dos.writeUTF(sinopse);

        return baos.toByteArray();
    }

    // Desserialização
    public void fromByteArray(byte[] b) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        DataInputStream dis = new DataInputStream(bais);

        id = dis.readInt();
        idSerie = dis.readInt();
        nome = dis.readUTF();
        temporada = dis.readInt();
        dataLancamento = LocalDate.ofEpochDay(dis.readLong());
        duracao = dis.readInt();
        sinopse = dis.readUTF();
    }
    @Override
    public short size() {
        try {
            return (short) this.toByteArray().length;
        } catch (Exception e) {
            return 0;
        }
    }


    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "\nID.............: " + id +
                "\nID da Série....: " + idSerie +
                "\nNome...........: " + nome +
                "\nTemporada......: " + temporada +
                "\nData Lançamento: " + dataLancamento.format(formatter) +
                "\nDuração........: " + duracao + " min" +
                "\nSinopse........: " + sinopse;
    }
    @Override
    public int hashCode() {
        return Objects.hash(nome, temporada, duracao);
    }

}

