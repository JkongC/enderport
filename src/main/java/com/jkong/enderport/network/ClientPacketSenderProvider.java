package com.jkong.enderport.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;

public class ClientPacketSenderProvider {
    public static ClientPacketSender instance;

    public static ClientPacketSender get() {
        if (instance == null && FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            try {
                Class<?> clazz = Class.forName("com.jkong.enderport.network.PacketSender");
                instance = (ClientPacketSender) clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }
}
