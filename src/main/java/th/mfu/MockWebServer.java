package th.mfu;

import java.io.*;
import java.net.*;

public class MockWebServer implements Runnable {

    private int port;

    public MockWebServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Mock Web Server running on port " + port + "...");

            while (true) {
                Socket clientSocket = serverSocket.accept();

                // สร้าง input/output stream
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                // อ่าน request จาก client
                String line;
                while ((line = in.readLine()) != null && !line.isEmpty()) {
                    System.out.println("[" + port + "] Received: " + line);
                }

                // สร้าง response
                String response = "HTTP/1.1 200 OK\r\n"
                        + "Content-Type: text/html\r\n"
                        + "Connection: close\r\n"
                        + "\r\n"
                        + "<html><body>Hello, Web! on Port " + port + "</body></html>";

                // ส่ง response ไปยัง client
                out.println(response);

                // ปิด socket ของ client
                clientSocket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Thread server1 = new Thread(new MockWebServer(8080));
        server1.start();

        Thread server2 = new Thread(new MockWebServer(8081));
        server2.start();

        // รอจนกว่าจะกดปุ่มอะไรบางอย่าง
        System.out.println("Press any key to stop the server...");
        try {
            System.in.read();

            // หยุด thread (คำสั่ง stop ไม่แนะนำ แต่ใช้ในงานทดลองสั้น ๆ ได้)
            server1.stop();
            server1.interrupt();

            server2.stop();
            server2.interrupt();

            System.out.println("Mock web server stopped.");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}