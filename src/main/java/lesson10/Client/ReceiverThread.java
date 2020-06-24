package lesson10.Client;

import lesson10.Config.ChatCommands;

import java.io.IOException;
import java.net.*;

/**
 * Класс для приема сообщений с сервера
 *
 * @author Marina_Larionova
 * @version 1.0.0
 * @see ChatCommands
 */
public class ReceiverThread extends Thread {
    private final InetAddress group;
    private final MulticastSocket socket;
    private final Client client;

    public ReceiverThread(Client client, InetAddress group, int port) throws IOException {
        socket = new MulticastSocket(port);
        this.group = group;
        socket.joinGroup(group);
        this.client = client;
    }

    /**
     * Метод для приема сообщений с сервера.
     * При получении "admin: Такой ник уже занят" вызывает метод registerUser() экземпляра клиента
     * При получении "admin: До свидания!" закрывает сокет и завершается
     *
     * @see Client
     */
    @Override
    public void run() {
        byte[] receiveData = new byte[1024];

        try {
            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);

                String message = new String(receiveData, receivePacket.getOffset(), receivePacket.getLength());
                if (message.contains("admin: Такой ник уже занят")) {
                    System.out.println(message);
                    client.registerUser();

                } else if (message.contains("admin: До свидания!")) {
                    System.out.println(message);
                    socket.leaveGroup(group);
                    socket.close();
                    return;

                } else {
                    System.out.println(message);
                }
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
    }
}
