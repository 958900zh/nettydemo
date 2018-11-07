package com.demo.protocol.request;

import com.demo.protocol.Packet;
import lombok.Data;

import static com.demo.protocol.command.Command.MESSAGE_REQUEST;

@Data
public class MessageRequestPacket extends Packet {

    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
