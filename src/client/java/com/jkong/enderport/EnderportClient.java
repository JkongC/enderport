package com.jkong.enderport;

import com.jkong.enderport.blockentities.EPClientBlockEntities;
import com.jkong.enderport.blocks.EPClientBlocks;
import com.jkong.enderport.components.EPComponents;
import com.jkong.enderport.items.EPItems;
import com.jkong.enderport.network.EPClientNetworking;
import net.fabricmc.api.ClientModInitializer;

import static com.jkong.enderport.EPItemTooltips.initializeTooltip;

public class EnderportClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		EPItems.Initialize();
		EPComponents.Initialize();
		EPClientNetworking.Initialize();
		EPClientBlocks.Initialize();
		EPClientBlockEntities.Initialize();

		initializeTooltip();
	}
}