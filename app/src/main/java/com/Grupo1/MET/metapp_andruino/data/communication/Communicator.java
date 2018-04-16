package com.Grupo1.MET.metapp_andruino.data.communication;

import java.io.IOException;

/**
 * Interface that must be implemented by any class that acts as a communication bridge, eg. UDP, NFC, Bluetooth
 */
public interface Communicator {

    /**
     * Sends data through the peripheral.
     * @param data The data to send.
     * @throws IOException If the communication goes awry.
     */
    void send(String data) throws IOException;

    /**
     * Reads the peripheral. This method is blocking.
     * @return Message received.
     * @throws IOException If the communication goes awry.
     */
    String receive() throws IOException;

    /**
     * Closes the communication.
     */
    void close();
}
