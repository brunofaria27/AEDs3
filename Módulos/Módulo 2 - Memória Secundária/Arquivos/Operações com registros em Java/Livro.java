import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

public class Livro {
    protected int idLivro;
    protected String titulo;
    protected String autor;
    protected float preco;

    public Livro(int i, String t, String a, float p) {
        this.idLivro = i;
        this.titulo = t;
        this.autor = a;
        this.preco = p;
    }

    public Livro() {
        this.idLivro = -1;
        this.titulo = "";
        this.autor = "";
        this.preco = 0F;
    }

    public String toString() {
        DecimalFormat df = new DecimalFormat("#,##0.00");

        return "\nID....: " + this.idLivro +
                "\nTítulo: " + this.titulo +
                "\nAutor.: " + this.autor +
                "\nPreço.: R$ " + df.format(this.preco);
    }

    // Descreve o livro por meio de vetor de Bytes
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);  // Tipos primitivos para Bytes
        dos.writeInt(idLivro);
        dos.writeUTF(titulo);
        dos.writeUTF(autor);
        dos.writeFloat(preco);
        return baos.toByteArray();
    }

    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        idLivro = dis.readInt();
        titulo = dis.readUTF();
        autor = dis.readUTF();
        preco = dis.readFloat();
    }

}