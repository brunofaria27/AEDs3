import java.io.FileOutputStream;
import java.io.DataOutputStream;

public class Main {
    public static void main(String[] args) {
        Livro l1 = new Livro(1, "Eu, Robô", "Isaac Asimov", 14.9F);
        Livro l2 = new Livro(2, "Eu Sou A Lenda", "Richard Matheson", 21.99F);

        System.out.println(l1);
        System.out.println(l2);

        FileOutputStream arq;
        DataOutputStream dos; // Conversão dos tipos primitivos em sequencia de Bytes

        try {

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

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
