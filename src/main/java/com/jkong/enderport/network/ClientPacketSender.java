package com.jkong.enderport.network;

import net.minecraft.network.packet.CustomPayload;

public interface ClientPacketSender {
    <T extends CustomPayload> void send(T payload);
}
