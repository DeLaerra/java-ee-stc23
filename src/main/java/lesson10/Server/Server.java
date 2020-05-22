package lesson10.Server;

import lesson10.Config.ChatCommands;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Сервер многопользовательского консольного чата.
 *
 * @author Marina_Larionova
 * @version 1.0.0
 */
public class Server extends Thread {
    private final DatagramSocket socket;
    private static boolean isRunning;
    private static String group;
    private static int portOut;
    private static int portIn;
    private static final Map<String, InetAddress> NICKNAME_TO_IP = new HashMap<>();

    public Server() throws SocketException {
        socket = new DatagramSocket(portIn);
    }

    /**
     * Метод добавляет новых пользователей в коллекцию NICKNAME_TO_IP по команде /reg_user.
     * Рассылает всем пользователям чата информацию о подключении нового пользователя
     * Если такой никнейм уже существует в коллекции, возвращает клиенту ответ для повторного выбора никнейма
     *
     * @param nickname      - никнейм пользователя
     * @param userIPAddress - IP адрес пользователя
     * @throws IOException
     */
    private void registerUser(String nickname, InetAddress userIPAddress) throws IOException {
        String messageToUser, messageToAll;
        if (NICKNAME_TO_IP.containsKey(nickname)) {
            messageToUser = "admin: Такой ник уже занят. Пожалуйста, укажите другой ник!";
            DatagramPacket out = new DatagramPacket(messageToUser.getBytes(), messageToUser.getBytes().length, userIPAddress, portOut);
            socket.send(out);
        } else {
            NICKNAME_TO_IP.put(nickname, userIPAddress);
            messageToAll = "admin: Пользователь " + nickname + " зашел в чат";
            DatagramPacket out = new DatagramPacket(messageToAll.getBytes(), messageToAll.getBytes().length,
                    InetAddress.getByName(group), portOut);
            socket.send(out);

            messageToUser = "admin: Добро пожаловать!";
            out = new DatagramPacket(messageToUser.getBytes(), messageToUser.getBytes().length, userIPAddress, portOut);
            socket.send(out);

            System.out.println("Добавлен пользователь " + nickname);
        }
    }

    /**
     * Метод удаляет пользователей из коллекции NICKNAME_TO_IP после команды /quit
     * Рассылает всем пользователям чата информацию об отключении пользователя
     *
     * @param nickname - никнейм пользователя
     * @throws IOException
     */
    private void unregisterUser(String nickname) throws IOException {
        InetAddress userIPAddress = NICKNAME_TO_IP.get(nickname);
        String messageToAll = "admin: Пользователь " + nickname + " покинул чат";
        DatagramPacket out = new DatagramPacket(messageToAll.getBytes(), messageToAll.getBytes().length,
                InetAddress.getByName(group), portOut);
        socket.send(out);

        String messageToUser = "admin: До свидания!";
        out = new DatagramPacket(messageToUser.getBytes(), messageToUser.getBytes().length, userIPAddress, portOut);
        socket.send(out);

        NICKNAME_TO_IP.remove(nickname);
        System.out.println("Пользователь " + nickname + " удален");
    }

    /**
     * Метод для рассылки приватных сообщений конкретным пользователям (unicast).
     * Ищет в коллекции NICKNAME_TO_IP адресата и отправляет сообщение.
     * Если не нашел, возвращает клиенту ответ о неверном выборе никнейма.
     *
     * @param sender
     * @param recipient
     * @param message
     * @throws IOException
     */
    private void sendPrivateMessage(String sender, String recipient, byte[] message) throws IOException {
        if (NICKNAME_TO_IP.containsKey(recipient)) {
            InetAddress recipientIPAddress = NICKNAME_TO_IP.get(recipient);
            String pmHeader = "Личное сообщение от пользователя " + sender + ":";
            DatagramPacket out = new DatagramPacket(pmHeader.getBytes(), pmHeader.getBytes().length, recipientIPAddress, portOut);
            socket.send(out);

            out = new DatagramPacket(message, message.length, recipientIPAddress, portOut);
            socket.send(out);
            System.out.println("Пользователь " + sender + " отправил сообщение пользователю " + recipient);
        } else {
            InetAddress senderIPAddress = NICKNAME_TO_IP.get(sender);
            String pmHeader = "Укажите правильный никнейм в формате /pmNickname message";
            DatagramPacket out = new DatagramPacket(pmHeader.getBytes(), pmHeader.getBytes().length, senderIPAddress, portOut);
            socket.send(out);
        }
    }

    /**
     * Прием и рассылка сообщений группе пользователей (multicast)
     * Добавление пользователя admin в коллекцию NICKNAME_TO_IP для последующей рассылки от его имени технических сообщений
     * Запуск методов регистрации и рассылки личных сообщений при получении команд /reg_user, /quit, /pm
     *
     * @see ChatCommands
     */
    @Override
    public void run() {
        isRunning = true;
        NICKNAME_TO_IP.put("admin", null);

        while (isRunning) {
            try {
                System.out.println("Ожидаем данные...");
                byte[] buffer = new byte[1024];
                DatagramPacket in = new DatagramPacket(buffer, buffer.length);
                socket.receive(in);

                String message = new String(in.getData(), 0, in.getLength());
                System.out.println("Сервер получил: " + message);

                if (message.contains(ChatCommands.REG_USER.getCommand())) {
                    String name = message.replace(ChatCommands.REG_USER.getCommand(), "");
                    registerUser(name, in.getAddress());

                } else if (message.contains(ChatCommands.QUIT.getCommand())) {
                    String name = message.replace(": " + ChatCommands.QUIT.getCommand(), "");
                    unregisterUser(name);

                } else if (message.contains(ChatCommands.PRIVATE_MESSAGE.getCommand())) {
                    String sender = message.substring(0, message.indexOf(":"));
                    String recipient = null;
                    try {
                        String msg = message.replace(sender + ": " + ChatCommands.PRIVATE_MESSAGE.getCommand(), "");
                        recipient = msg.substring(0, msg.indexOf(" "));
                    } catch (StringIndexOutOfBoundsException e) {
                        System.err.println("Неверный формат личного сообщения от пользователя " + sender);
                    }
                    sendPrivateMessage(sender, recipient, in.getData());

                } else {
                    DatagramPacket out = new DatagramPacket(in.getData(), in.getData().length,
                            InetAddress.getByName(group), portOut);
                    socket.send(out);
                    System.out.println("Сервер отправил: " + message);
                }
            } catch (IOException e) {
                e.printStackTrace();
                isRunning = false;
            }
        }
        socket.close();
    }

    /**
     * Чтение свойств из файла config.properties.
     * Запуск потока чтения и отправки сообщений
     *
     * @param args
     * @throws SocketException
     */
    public static void main(String[] args) throws SocketException {
        Properties property = new Properties();
        try {
            property.load(new FileInputStream("src/main/resources/lesson10/config.properties"));
            portIn = Integer.parseInt(property.getProperty("server.portIn"));
            portOut = Integer.parseInt(property.getProperty("server.portOut"));
            group = property.getProperty("server.group");
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла свойств!");
        }

        new Server().start();
    }
}
