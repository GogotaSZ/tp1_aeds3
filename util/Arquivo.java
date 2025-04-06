// util/Arquivo.java
package util;

import java.io.*;
import java.lang.reflect.Constructor;
import modelo.Registro;

public class Arquivo<T extends Registro> {
    private RandomAccessFile arquivo;
    private Constructor<T> construtor;

    public Arquivo(String nomeArquivo, Constructor<T> c) throws Exception {
        this.construtor = c;
        arquivo = new RandomAccessFile(nomeArquivo, "rw");
    }

    public int create(T obj) throws Exception {
        arquivo.seek(arquivo.length());
        int id = (int) (arquivo.length() / 1024); // simples, só para testar
        obj.setId(id);
        byte[] ba = obj.toByteArray();
        arquivo.writeInt(ba.length);
        arquivo.write(ba);
        return id;
    }

    public T read(int id) throws Exception {
        arquivo.seek(0);
        while (arquivo.getFilePointer() < arquivo.length()) {
            int tam = arquivo.readInt();
            byte[] ba = new byte[tam];
            arquivo.readFully(ba);
            T obj = construtor.newInstance();
            obj.fromByteArray(ba);
            if (obj.getId() == id) {
                return obj;
            }
        }
        return null;
    }

    public boolean update(T novo) throws Exception {
        delete(novo.getId());
        create(novo);
        return true;
    }

    public boolean delete(int id) throws Exception {
        // versão simples: não remove de verdade
        return true;
    }
}
