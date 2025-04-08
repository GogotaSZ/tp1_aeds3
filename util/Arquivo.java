package util;

import java.io.*;
import java.lang.reflect.Constructor;

public class Arquivo<T extends Registro> {
    final int TAM_CABECALHO = 12;
    protected RandomAccessFile arquivo;
    protected String nomeArquivo;
    protected Constructor<T> construtor;
    protected HashExtensivel<ParIDEndereco> indiceDireto;

    public Arquivo(String nomeBase, Constructor<T> c) throws Exception {
        File pasta = new File("dados/" + nomeBase);
        if (!pasta.exists()) pasta.mkdirs();

        this.nomeArquivo = "dados/" + nomeBase + "/" + nomeBase + ".db";
        this.construtor = c;
        this.arquivo = new RandomAccessFile(this.nomeArquivo, "rw");

        if (arquivo.length() < TAM_CABECALHO) {
            arquivo.writeInt(0);       // último ID gerado
            arquivo.writeLong(-1);     // ponteiro para lista de excluídos
        }

        this.indiceDireto = new HashExtensivel<>(
                ParIDEndereco.class.getConstructor(),
                4,
                "dados/" + nomeBase + "/" + nomeBase + ".d.db",
                "dados/" + nomeBase + "/" + nomeBase + ".c.db"
        );
    }

    public int create(T obj) throws Exception {
        arquivo.seek(0);
        int novoId = arquivo.readInt() + 1;
        arquivo.seek(0);
        arquivo.writeInt(novoId);
        obj.setId(novoId);
        byte[] ba = obj.toByteArray();

        long endereco = getEspacoLivre(ba.length);
        if (endereco == -1) {
            arquivo.seek(arquivo.length());
            endereco = arquivo.getFilePointer();
            arquivo.writeByte(' ');
            arquivo.writeShort(ba.length);
            arquivo.write(ba);
        } else {
            arquivo.seek(endereco);
            arquivo.writeByte(' ');
            arquivo.skipBytes(2);
            arquivo.write(ba);
        }

        indiceDireto.create(new ParIDEndereco(novoId, endereco));
        return novoId;
    }

    public T read(int id) throws Exception {
        ParIDEndereco par = indiceDireto.read(id);
        if (par == null) return null;

        arquivo.seek(par.getEndereco());
        byte lapide = arquivo.readByte();
        if (lapide != ' ') return null;

        short tam = arquivo.readShort();
        byte[] ba = new byte[tam];
        arquivo.readFully(ba);

        T obj = construtor.newInstance();
        obj.fromByteArray(ba);
        return obj;
    }

    public boolean update(T novo) throws Exception {
        ParIDEndereco par = indiceDireto.read(novo.getId());
        if (par == null) return false;

        arquivo.seek(par.getEndereco());
        byte lapide = arquivo.readByte();
        if (lapide != ' ') return false;

        short tam = arquivo.readShort();
        byte[] novoBa = novo.toByteArray();

        if (novoBa.length <= tam) {
            arquivo.write(novoBa);
        } else {
            // Marca o antigo como excluído
            arquivo.seek(par.getEndereco());
            arquivo.writeByte('*');
            adicionarEspacoLivre(tam, par.getEndereco());

            // Grava novo no fim ou espaço livre
            long novoEnd = getEspacoLivre(novoBa.length);
            if (novoEnd == -1) {
                arquivo.seek(arquivo.length());
                novoEnd = arquivo.getFilePointer();
                arquivo.writeByte(' ');
                arquivo.writeShort(novoBa.length);
                arquivo.write(novoBa);
            } else {
                arquivo.seek(novoEnd);
                arquivo.writeByte(' ');
                arquivo.skipBytes(2);
                arquivo.write(novoBa);
            }

            indiceDireto.update(new ParIDEndereco(novo.getId(), novoEnd));
        }

        return true;
    }

    public boolean delete(int id) throws Exception {
        ParIDEndereco par = indiceDireto.read(id);
        if (par == null) return false;

        arquivo.seek(par.getEndereco());
        byte lapide = arquivo.readByte();
        if (lapide != ' ') return false;

        short tam = arquivo.readShort();
        arquivo.writeByte('*'); // lápide
        adicionarEspacoLivre(tam, par.getEndereco());

        return indiceDireto.delete(id);
    }

    public int getUltimoID() throws IOException {
        arquivo.seek(0);
        return arquivo.readInt();
    }

    public void close() throws Exception {
        arquivo.close();
        indiceDireto.close();
    }

    // Lista encadeada para espaços livres reutilizáveis
    private long getEspacoLivre(int tamanhoNecessario) throws Exception {
        arquivo.seek(4);
        long atual = arquivo.readLong();
        long anterior = 4;

        while (atual != -1) {
            arquivo.seek(atual + 1);
            short tam = arquivo.readShort();
            long proximo = arquivo.readLong();

            if (tam >= tamanhoNecessario) {
                arquivo.seek(anterior);
                arquivo.writeLong(proximo);
                return atual;
            }

            anterior = atual + 3;
            atual = proximo;
        }

        return -1;
    }

    private void adicionarEspacoLivre(int tamanho, long endereco) throws Exception {
        arquivo.seek(4);
        long primeiro = arquivo.readLong();
        arquivo.seek(4);
        arquivo.writeLong(endereco);
        arquivo.seek(endereco + 1);
        arquivo.writeShort(tamanho);
        arquivo.writeLong(primeiro);
    }
}
