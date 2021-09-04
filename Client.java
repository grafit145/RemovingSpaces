package ru.netology;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 8888);
        final SocketChannel socketChannel = SocketChannel.open();

        socketChannel.connect(socketAddress);

        try (Scanner scanner = new Scanner(System.in)) {
            final ByteBuffer inputBuffer = ByteBuffer.allocate(1024);
            String msg;
            while (true) {
                System.out.println("Введите сообщения для сервера, для завершения введите end");
                msg = scanner.nextLine();
                if ("end".equals(msg)) break;

                socketChannel.write(ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8)));
                Thread.sleep(2000);

                int bytesCount = socketChannel.read(inputBuffer);
                System.out.println(new String(inputBuffer.array(), 0, bytesCount, StandardCharsets.UTF_8).
                        trim());
                inputBuffer.clear();

            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            socketChannel.close();
        }

    }
}
