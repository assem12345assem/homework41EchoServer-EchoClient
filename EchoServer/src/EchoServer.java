import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class EchoServer {
    private final int port;

    private EchoServer (int port) {
        this.port = port;
    }
    public static EchoServer bindToPort(int port) {
        return new EchoServer(port);
    }
    public void run() {
        try (var server = new ServerSocket(port)) {
            try (var clientSocket = server.accept()){
                handle(clientSocket);
            } catch (Exception e) {

            }
        } catch (Exception e) {
            System.out.printf("Most likely port %s is busy.%n", port);
            e.printStackTrace();
        }
    }

    private void handle(Socket socket) throws IOException {
        Scanner scanner = new Scanner(System.in, "UTF-8");
        try (PrintWriter writer = new PrintWriter(socket.getOutputStream());
             var isr = new InputStreamReader(socket.getInputStream(), "UTF-8");)   {
            Scanner sc = new Scanner(isr);
            while (true) {
                var message2 = sc.nextLine().strip();
                System.out.printf("Got from client: %s%n", message2);
                String message = Response.serverAction(message2);
                if(message == null) {
                    System.out.println("Bye bye!");
                    return;
                } else {
                    writer.write(message);
                    writer.write(System.lineSeparator());
                    writer.flush();
                }



            }
        } catch (NoSuchElementException ex) {
            System.out.println("Client dropped connection!");
        }
    }

}
