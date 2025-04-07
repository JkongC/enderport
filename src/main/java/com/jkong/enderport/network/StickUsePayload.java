package com.jkong.enderport.network;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;

public record StickUsePayload(Hand hand) implements CustomPayload {
    public static final Id<StickUsePayload> ID = new Id<>(Identifier.of("enderport", "stick_use"));
    public static final PacketCodec<PacketByteBuf, StickUsePayload> PACKET_CODEC = CustomPayload.codecOf(
            StickUsePayload::write,
            StickUsePayload::read
    );

    private void write(PacketByteBuf buf) {
        buf.writeInt(hand == Hand.MAIN_HAND ? 0 : 1);
    }

    private static StickUsePayload read(PacketByteBuf buf) {
        return new StickUsePayload(buf.readInt() == 0 ? Hand.MAIN_HAND : Hand.OFF_HAND);
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}