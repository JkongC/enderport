package com.jkong.enderport.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.packet.CustomPayload;

public class PacketSender implements ClientPacketSender {
    @Override
    public <T extends CustomPayload> void send(T payload) {
        ClientPlayNetworking.send(payload);
    }
}
