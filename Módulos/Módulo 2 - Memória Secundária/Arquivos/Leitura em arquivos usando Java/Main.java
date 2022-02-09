import java.io.FileOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.DataInputStream;

public class Main {

    public static void main(String[] args) {
        Livro l1 = new Livro(1, "Eu, Robô", "Isaac Asimov", 14.9F);
        Livro l2 = new Livro(2, "Eu Sou A Lenda", "Richard Matheson", 21.99F);

        System.out.println(l1);
        System.out.println(l2);

        FileOutputStream arq;
        DataOutputStream dos;

        FileInputStream arq2;
        DataInputStream dis;

        try {

            /* ESCRITA */
            arq = new FileOutputStream("dados/livros.db");
            dos = new DataOutputStream(arq);

            dos.writeInt(l1.idLivro);
            dos.writeUTF(l1.titulo);
            dos.writeUTF(l1.autor);
            dos.writeFloat(l1.preco);

            dos.writeInt(l2.idLivro);
            dos.writeUTF(l2.titulo);
            dos.writeUTF(l2.autor);
            dos.writeFloat(l2.preco);

            dos.close();
            arq.close();

            /* LEITURA */
            Livro l3 = new Livro();
            Livro l4 = new Livro();

            arq2 = new FileInputStream("dados/livros.db");
            dis = new DataInputStream(arq2);

            System.out.println("\n\nDados lidos:");

            l3.idLivro = dis.readInt();
            l3.titulo = dis.readUTF();
            l3.autor = dis.readUTF();
            l3.preco = dis.readFloat();
            System.out.println(l3);

            l4.idLivro = dis.readInt();
            l4.titulo = dis.readUTF();
            l4.autor = dis.readUTF();
            l4.preco = dis.readFloat();
            System.out.println(l4);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}