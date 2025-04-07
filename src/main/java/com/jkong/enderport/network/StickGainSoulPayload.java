package com.jkong.enderport.network;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record StickGainSoulPayload() implements CustomPayload {
    public static final Id<StickGainSoulPayload> ID = new Id<>(Identifier.of( "enderport","stick_gain_soul_payload"));
    public static final PacketCodec<PacketByteBuf, StickGainSoulPayload> PACKET_CODEC = CustomPayload.codecOf(
            StickGainSoulPayload::write,
            StickGainSoulPayload::read
    );

    private void write(PacketByteBuf buffer) {}

    private static StickGainSoulPayload read(PacketByteBuf buffer) {
        return new StickGainSoulPayload();
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
