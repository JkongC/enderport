package com.jkong.enderport.network;

import com.jkong.enderport.items.EnderportStickClient;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class EPClientNetworking {

    public static void Initialize() {
        ClientPlayNetworking.registerGlobalReceiver(StickTpPayload.ID,
                (payload, context) -> {
                    context.client().execute(() -> EnderportStickClient.spawnParticles(context.client().world, context.player(), payload.begin(), payload.end(), payload.userPos()));
                });
    }

}
