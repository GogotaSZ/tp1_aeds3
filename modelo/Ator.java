package  modelo;
import util.Registro;

import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class Ator implements Registro{
    
    private int id;
    private String nome;

    public Ator() {
        this.id = -1;
        this.nome = "";
    }

    public Ator(String nome) {
        this.id = -1; // O ID será atribuído automaticamente no CRUD
        this.nome = nome;
    }

    public Ator(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getID() {
        return this.id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "ID: " + id + "\nNome: " + nome;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(ba);

        dos.writeInt(id);           // Salva o ID!
        dos.writeUTF(nome);         // Salva o nome

        return ba.toByteArray();
    }

    @Override
    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bb = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bb);

        id = dis.readInt();         // Recupera o ID!
        nome = dis.readUTF();       // Recupera o nome
    }

}
