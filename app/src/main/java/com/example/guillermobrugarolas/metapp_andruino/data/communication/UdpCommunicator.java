package com.example.guillermobrugarolas.metapp_andruino.data.communication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UdpCommunicator implements Communicator {
    private static final int PORT_RECEIVE = 31337;
    private static final int PORT_SEND = 31338;
    // TODO set actual robot IP.
    private static final String ROBOT_IP = "192.168.1.39";

    // protocol:
    // option:param1,param2,...,paramN;
    // option = MessageType.ordinal();
    private static final int MAX_BUFFER_SIZE = 128;

    private DatagramSocket rSocket;//socket to receive
    private DatagramSocket sSocket;//socket to send

    private static UdpCommunicator instance = null;

    private UdpCommunicator() throws SocketException {
        rSocket = new DatagramSocket(PORT_RECEIVE);
        try {
            sSocket = new DatagramSocket();
        } catch (Exception e) {
            rSocket.close();
            throw e;
        }
    }

    public static UdpCommunicator getInstance() throws SocketException {
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
