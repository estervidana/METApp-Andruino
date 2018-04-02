package com.example.guillermobrugarolas.metapp_andruino.data.communication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UdpCommunicator implements Communicator {
    private static final int PORT = 31337;
    // TODO set actual robot IP.
    private static final String ROBOT_IP = "192.168.1.99";

    private static final int MAX_BUFFER_SIZE = 128;

    private DatagramSocket rSocket;//socket to receive
    private DatagramSocket sSocket;//socket to send

    private static UdpCommunicator instance = null;

    private UdpCommunicator() throws SocketException, UnknownHostException {
        rSocket = new DatagramSocket(PORT);
        try {
            sSocket = new DatagramSocket(PORT, InetAddress.getByName(ROBOT_IP));
        } catch (Exception e) {
            rSocket.close();
            throw e;
        }
    }

    public static UdpCommunicator getInstance() throws SocketException, UnknownHostException {
        if(instance == null){
            instance = new UdpCommunicator();
        }
        return instance;
    }


    @Override
    public void send(String data) throws IOException {
        byte[] dataAsBytes = data.getBytes();
        DatagramPacket packet = new DatagramPacket(dataAsBytes, dataAsBytes.length);
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
