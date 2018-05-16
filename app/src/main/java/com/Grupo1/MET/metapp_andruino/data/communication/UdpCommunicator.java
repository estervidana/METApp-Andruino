package com.Grupo1.MET.metapp_andruino.data.communication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Used to send and receive UDP packets. This class implements the Communicator interface.
 */
public class UdpCommunicator implements Communicator {
    /** The port on which the Communicator will listen.*/
    private static final int PORT_RECEIVE = 31337;
    /** The port to which the Communicator will send.*/
    private static final int PORT_SEND = 31338;
    // TODO set actual robot IP.
    /** The IP to which the Communicator will send.*/
    private static final String ROBOT_IP = "192.168.127.";

    // protocol:
    // option:param1,param2,...,paramN;
    // option = MessageType.ordinal();
    /** The maximum size of a packet.*/
    private static final int MAX_BUFFER_SIZE = 128;

    /** The socket used to receive messages. It will listen on port {@value #PORT_RECEIVE}*/
    private DatagramSocket rSocket;
    /** The socket used to send messages. The messages will be sent to IP {@value #ROBOT_IP} and port {@value #PORT_SEND}*/
    private DatagramSocket sSocket;

    /** Instance of the UdpCommunicator. Only one UdpCommunicator can be active at a time.*/
    private static UdpCommunicator instance = null;

    /**
     * Private constructor.
     *
     * @throws SocketException If the sockets could not be initialized.
     */
    private UdpCommunicator() throws SocketException {
        rSocket = new DatagramSocket(PORT_RECEIVE);
        try {
            sSocket = new DatagramSocket();
        } catch (IOException e) {
            rSocket.close();
            throw e;
        }
    }

    /**
     * Static method to get the instance of the UdpCommunicator. If no instance currently exists one is created.
     *
     * @return The instance of the UdpCommunicator.
     * @throws SocketException If the sockets could not be initialized.
     */
    public synchronized static UdpCommunicator getInstance() throws SocketException {
        if(instance == null){
            instance = new UdpCommunicator();
        }
        return instance;
    }


    @Override
    public void send(String data) throws IOException {
        byte[] dataAsBytes = data.getBytes();
        InetAddress sendAddress = InetAddress.getByName(ROBOT_IP);
        DatagramPacket packet = new DatagramPacket(dataAsBytes, dataAsBytes.length, sendAddress, PORT_SEND);
        sSocket.send(packet);
    }

    @Override
    public String receive() throws IOException {
        byte[] buffer = new byte[MAX_BUFFER_SIZE];
        DatagramPacket packet = new DatagramPacket(buffer, MAX_BUFFER_SIZE);
        rSocket.receive(packet);
        return new String(buffer);
    }

    @Override
    public void close(){
        rSocket.close();
        sSocket.close();
    }
}
