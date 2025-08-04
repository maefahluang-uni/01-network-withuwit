package th.mfu;

import java.io.*;
import java.net.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

// call mockup server at port 8080

public class MockWebClient {
    public static void main(String[] args) {

        // สร้าง socket ไปยัง localhost ที่ port 8080
        try (Socket socket = new Socket("localhost", 8080)) {
            
            // สร้าง output stream สำหรับส่งข้อมูลไปยัง server
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // สร้าง input stream สำหรับรับข้อมูลจาก server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // ส่ง HTTP GET request
            String request = "GET / HTTP/1.1\r\nHost: localhost\r\n\r\n";
            out.print(request);  // ใช้ print แทน println เพื่อควบคุม newline
            out.flush();         // flush เพื่อให้ข้อมูลถูกส่งทันที

            // อ่าน response จาก server แล้วแสดงผล
            String responseLine;
            while ((responseLine = in.readLine()) != null) {
                System.out.println(responseLine);
            }

        } catch (IOException e) {
            System.err.println("Error connecting to server: " + e.getMessage());
        }
    }
}
