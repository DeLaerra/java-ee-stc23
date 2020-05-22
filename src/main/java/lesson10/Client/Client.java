package lesson10.Client;

import lesson10.Config.ChatCommands;

import java.io.*;
import java.net.*;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Клиент многопользовательского консольного чата.
 * <p>
 * При запуске происходит регистрация пользователя на сервере. Если такой ник уже занят - необходимо указать другой ник.
 * В чате можно отправлять сообщения всем пользователям, отправлять личные сообщения конкретному пользователю по команде
 * /pm (в формате /pmNickname message) и выходить из чата по команде (/quit).
 *
 * @author Marina_Larionova
 * @version 1.0.0
 * @see ChatCommands
 */
public class Client {
    private static String nickname;
    private static int portIn;
    private static int portOut;
    private static String serverIpAddress;
    private static String group;
    private final DatagramSocket socket;
    private static final int CLIENT_PORT = ThreadLocalRandom.current().nextInt(7005, 7100);


    public Client() throws IOException {
        socket = new DatagramSocket(CLIENT_PORT);
    }

    public static String getNickname() {
        return nickname;
    }

    /**
     * Метод для регистрации пользователей на сервере. Отправлет на сервер указанный никнейм с командой /reg_user.
     * Если такой никнейм уже занят - сервер отправляет ответ "Такой ник уже занят" и метод запускается повторно
     *
     * @throws IOException
     * @see ReceiverThread
     * @see ChatCommands
     */
    public void registerUser() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Укажите ник:");
        nickname = reader.readLine();

        byte[] nicknameData = (ChatCommands.REG_USER.getCommand() + nickname).getBytes();
        DatagramPacket sendPacket = new DatagramPacket(nicknameData, nicknameData.length,
                InetAddress.getByName(serverIpAddress), portOut);
        socket.connect(InetAddress.getByName(serverIpAddress), portOut);
        socket.send(sendPacket);
    }

    /**
     * Чтение свойств из файла config.properties.
     * Регистрация пользователя.
     * Запуск потоков чтения и отправки сообщений
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Properties property = new Properties();
        try {
            property.load(new FileInputStream("src/main/resources/lesson10/config.properties"));
            portIn = Integer.parseInt(property.getProperty("server.portOut"));
            portOut = Integer.parseInt(property.getProperty("server.portIn"));
            group = property.getProperty("server.group");
            serverIpAddress = property.getProperty("server.IPAddress");
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла свойств!");
        }

        Client client = new Client();
        ReceiverThread receiver = new ReceiverThread(client, InetAddress.getByName(group), portIn);
        receiver.start();
        client.registerUser();
        SenderThread sender = new SenderThread(InetAddress.getByName(serverIpAddress), portOut, client.socket);
        sender.start();
    }
}
