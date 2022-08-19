import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class EchoClient {
    private final int port;
    private final String host;
    private EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }
    public static EchoClient connectTo(int port) {
        String localhost = "127.0.0.1";
        return new EchoClient(localhost, port);
    }

    public void run() {
        System.out.printf("send 'bye' to exit%n");
        try (Socket socket = new Socket(host, port)){
            sendMessage(socket);
        } catch (IOException e) {
            System.out.printf("Cannot connect to %s:%s ! %n", host, port);
            e.printStackTrace();
        }
    }

    private void sendMessage(Socket socket) throws IOException {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        try (PrintWriter writer = new PrintWriter(socket.getOutputStream());
             var isr = new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8))   {
            Scanner sc = new Scanner(isr);
            while (true) {
                String message  = scanner.nextLine();
                writer.write(message);
                writer.write(System.lineSeparator());
                writer.flush();
                if ("bye".equalsIgnoreCase(message)) {
                    System.out.println("Bye bye!");
                    return;
                }
                var message2 = sc.nextLine().strip();
                System.out.printf("Got from server: %s%n", message2);
            }
        } catch (NoSuchElementException ex) {
            System.out.println("Connection dropped!");
        }
    }

}
