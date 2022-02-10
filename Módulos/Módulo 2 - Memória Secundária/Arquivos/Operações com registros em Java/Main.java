import java.io.FileOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Livro l1 = new Livro(1, "Eu, Robô", "Isaac Asimov", 14.90F);
        Livro l2 = new Livro(2, "Eu Sou a Lenda", "Richard Matheson", 21.99F);

        FileOutputStream arq;
        DataOutputStream dos;

        FileInputStream arq2;
        DataInputStream dis;

        byte[] ba;

        try {

            // ESCRITA
            arq = new FileOutputStream("dados/livros.db");
            dos = new DataOutputStream(arq);
            ba = l1.toByteArray();
            dos.writeInt(ba.length);    // Escrever o tamanho de Bytes de cada livro antes de suas informações para facilitar leitura
            dos.write(ba);              // Escre info dos livros

            ba = l2.toByteArray();
            dos.writeInt(ba.length);    // Escrever o tamanho de Bytes de cada livro antes de suas informações para facilitar leitura
            dos.write(ba);              // Escre info dos livros

            arq.close();

            // LEITURA
            Livro l3 = new Livro();
            Livro l4 = new Livro();
            int tam;

            arq2 = new FileInputStream("dados/livros.db");
            dis = new DataInputStream(arq2);

            tam = dis.readInt();    // Lê o tamanho antes de tudo
            ba = new byte[tam];     // Armazena tudo depois to tamanho lido, ou seja, fica com o tamanho correto do livro
            dis.read(ba);
            l3.fromByteArray(ba);

            tam = dis.readInt();    // Lê o tamanho antes de tudo
            ba = new byte[tam];     // Armazena tudo depois to tamanho lido, ou seja, fica com o tamanho correto do livro
            dis.read(ba);
            l4.fromByteArray(ba);

            System.out.println(l3);
            System.out.println(l4);

            arq2.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}