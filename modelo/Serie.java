package modelo;

import java.io.*;

public class Serie implements Registro {
    private int id;
    private String nome;
    private int anoLancamento;
    private String sinopse;
    private String streaming;

    public Serie() {
        this.id = -1;
        this.nome = "";
        this.anoLancamento = 0;
        this.sinopse = "";
        this.streaming = "";
    }

    public Serie(String nome, int anoLancamento, String sinopse, String streaming) {
        this.nome = nome;
        this.anoLancamento = anoLancamento;
        this.sinopse = sinopse;
        this.streaming = streaming;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getAnoLancamento() { return anoLancamento; }
    public void setAnoLancamento(int anoLancamento) { this.anoLancamento = anoLancamento; }

    public String getSinopse() { return sinopse; }
    public void setSinopse(String sinopse) { this.sinopse = sinopse; }

    public String getStreaming() { return streaming; }
    public void setStreaming(String streaming) { this.streaming = streaming; }

    @Override
    public String toString() {
        return "ID: " + id +
               "\nNome: " + nome +
               "\nAno de Lançamento: " + anoLancamento +
               "\nSinopse: " + sinopse +
               "\nStreaming: " + streaming;
    }

    @Override
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(ba);
        dos.writeInt(id);
        dos.writeUTF(nome);
        dos.writeInt(anoLancamento);
        dos.writeUTF(sinopse);
        dos.writeUTF(streaming);
        return ba.toByteArray();
    }

    @Override
    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bb = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bb);
        id = dis.readInt();
        nome = dis.readUTF();
        anoLancamento = dis.readInt();
        sinopse = dis.readUTF();
        streaming = dis.readUTF();
    }
}
