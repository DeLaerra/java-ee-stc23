package lesson10.Client;

import lesson10.Config.ChatCommands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

/**
 * Класс для отправки сообщений на сервер
 *
 * @author Marina_Larionova
 * @version 1.0.0
 * @see ChatCommands
 */
public class SenderThread extends Thread {
    private final InetAddress serverIPAddress;
    private final DatagramSocket socket;
    private final int portOut;
    private final String nickname;

    public SenderThread(InetAddress serverIPAddress, int portOut, DatagramSocket socket) {
        this.serverIPAddress = serverIPAddress;
        this.portOut = portOut;
        this.nickname = Client.getNickname();
        this.socket = socket;
    }

    /**
     * Метод для отправки сообщений на сервер.
     * При отправке команды /quit закрывает сокет и завершается
     *
     * @see ChatCommands
     */
    @Override
    public void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        socket.connect(serverIPAddress, portOut);

        while (true) {
            try {
                String message = nickname + ": " + reader.readLine();
                if (message.equals(nickname + ": " + ChatCommands.QUIT.getCommand())) {
                    byte[] sendData = message.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverIPAddress, portOut);
                    socket.send(sendPacket);
                    socket.close();
                    return;
                } else {
                    byte[] sendData = message.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverIPAddress, portOut);
                    socket.send(sendPacket);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

