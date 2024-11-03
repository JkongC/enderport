package com.jkong.enderport.blocks;

import net.minecraft.block.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class EPBlocks {
    
    public static final Block LIGHTED_AIR_BLOCK = new LightedAirBlock(AbstractBlock.Settings.copy(Blocks.AIR));

    public static void Initialize(){
        Registry.register(Registries.BLOCK, Identifier.of("enderport", "lighted_air_block"), LIGHTED_AIR_BLOCK);
    }
}
