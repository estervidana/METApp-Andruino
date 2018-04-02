package com.example.guillermobrugarolas.metapp_andruino.data.communication;

import java.io.IOException;

public interface Communicator {

    void send(String data) throws IOException;

    String receive() throws IOException;

    void close();
}
