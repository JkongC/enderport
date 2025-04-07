package com.jkong.enderport.network;

import com.jkong.enderport.items.EnderportStick;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class EPNetworking {

    public static void Initialize() {
        PayloadTypeRegistry.playS2C().register(StickTpPayload.ID, StickTpPayload.PACKET_CODEC);
        PayloadTypeRegistry.playC2S().register(StickUsePayload.ID, StickUsePayload.PACKET_CODEC);

        ServerPlayNetworking.registerGlobalReceiver(StickUsePayload.ID,
                (payload, context) -> context.server().execute(() -> EnderportStick.serverUse(context.player().getServerWorld(), context.player(), payload.hand())));
    }
}
