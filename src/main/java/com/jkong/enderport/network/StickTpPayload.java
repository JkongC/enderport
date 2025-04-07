package com.jkong.enderport.network;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public record StickTpPayload(BlockPos begin, BlockPos end, Vec3d userPos) implements CustomPayload {
    public static final Id<StickTpPayload> ID = new Id<>(Identifier.of("enderport", "stick_tp"));
    public static final PacketCodec<PacketByteBuf, StickTpPayload> PACKET_CODEC = CustomPayload.codecOf(
            StickTpPayload::write,
            StickTpPayload::read
    );

    private void write(PacketByteBuf buf) {
        buf.writeBlockPos(begin);
        buf.writeBlockPos(end);
        buf.writeVec3d(userPos);
    }

    private static StickTpPayload read(PacketByteBuf buf) {
        BlockPos begin = buf.readBlockPos();
        BlockPos end = buf.readBlockPos();
        Vec3d userPos = buf.readVec3d();
        return new StickTpPayload(begin, end, userPos);
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
