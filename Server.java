import java.io.*;
import java.net.*;

public class Server {

    public static void main(String[] args) {
        int port = 5000;

        try {
            ServerSocket serverSocket = new ServerSocket(port);

            System.out.println("=================================");
            System.out.println(" SERVER MAHASISWA");
            System.out.println(" Server berjalan pada port " + port);
            System.out.println(" Menunggu client...");
            System.out.println("=================================");

            while (true) {
                Socket socket = serverSocket.accept();

                System.out.println("Client terhubung: "
                        + socket.getInetAddress());

                // Membuat thread untuk setiap client
                new ClientHandler(socket).start();
            }

        } catch (IOException e) {
            System.out.println("Error Server: " + e.getMessage());
        }
    }
}

class ClientHandler extends Thread {

    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try {
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            PrintWriter output = new PrintWriter(
                    socket.getOutputStream(), true
            );

            String data;

            while ((data = input.readLine()) != null) {

                System.out.println("Data diterima: " + data);

                String[] mahasiswa = data.split("\\|");

                if (mahasiswa.length == 3) {

                    String nim = mahasiswa[0];
                    String nama = mahasiswa[1];
                    String jurusan = mahasiswa[2];

                    System.out.println("NIM     : " + nim);
                    System.out.println("Nama    : " + nama);
                    System.out.println("Jurusan : " + jurusan);

                    String response =
                            "Data berhasil diterima! "
                            + "NIM=" + nim
                            + ", Nama=" + nama
                            + ", Jurusan=" + jurusan;

                    output.println(response);

                } else {
                    output.println("Format data tidak valid!");
                }
            }

            socket.close();

        } catch (IOException e) {
            System.out.println("Client terputus.");
        }
    }
}