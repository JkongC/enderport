package com.jkong.enderport.components;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class EPComponents {
    public static final ComponentType<Integer> ENDERSOULS = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of("enderport", "endersouls"),
            ComponentType.<Integer>builder().codec(Codec.INT).build()
    );
    public static final int MAX_ENDERSOULS = 200;

    public static final ComponentType<Integer> SOULS_TO_BE_ADDED = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of("enderport", "souls_to_be_added"),
            ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    public static void Initialize(){}

    public static int getMaxEndersouls() {
        return MAX_ENDERSOULS;
    }
}
