package util;

import java.io.*;

public class ParIDEndereco implements RegistroHashExtensivel<ParIDEndereco> {

    private int id;
    private long endereco;

    public ParIDEndereco() {
        this(-1, -1L);
    }

    public ParIDEndereco(int id, long endereco) {
        this.id = id;
        this.endereco = endereco;
    }

    public int getId() {
        return id;
    }

    public long getEndereco() {
        return endereco;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEndereco(long endereco) {
        this.endereco = endereco;
    }

    @Override
    public int hashCode() {
        return Math.abs(id);
    }

    @Override
    public short size() {
        return (short) (Integer.BYTES + Long.BYTES); // 4 + 8 = 12 bytes
    }

    @Override
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(id);
        dos.writeLong(endereco);
        return baos.toByteArray();
    }

    @Override
    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        this.id = dis.readInt();
        this.endereco = dis.readLong();
    }

    @Override
    public String toString() {
        return "(" + id + ", " + endereco + ")";
    }
}
