package com.jkong.enderport.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class EPClientBlocks {
    public static final Block LIGHTED_AIR_BLOCK = register("lighted_air_block", LightedAirBlock::new, AbstractBlock.Settings.create()
            .air()
            .noCollision()
            .luminance(state -> 0)
            .nonOpaque()
            .dropsNothing()
            .solidBlock(Blocks::never)
            .suffocates(Blocks::never)
            .blockVision(Blocks::never));

    private static Block register(String path, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
        final Identifier identifier = Identifier.of("enderport", path);
        final RegistryKey<Block> registryKey = RegistryKey.of(RegistryKeys.BLOCK, identifier);

        return Blocks.register(registryKey, factory, settings);
    }

    public static void Initialize() {

    }
}
