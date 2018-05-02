package com.Grupo1.MET.metapp_andruino.data.communication;

/**
 * Enumerates the types of messages that can be sent.
 */
public enum MessageType {
    // Enum ordinal values are automatically assigned starting with 0 for the first value
    // and sequentially increases by one.
    // Note. If a new Enum value is added all value after the new value will be modified.
    /*sent by Robot*/TEMP, BUMP, WALL, POS, COLLISION, ACCEL,
    /*sent by App*/MODE, TURN, MOVE, SPEED, STOP, FIGURE,
    /*sent by both*/LEDS, CTRL_MODE
}
