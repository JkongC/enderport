package com.jkong.enderport;

import com.jkong.enderport.items.EPItems;
import net.fabricmc.api.ClientModInitializer;

public class EnderportClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		EPItems.Initialize();
	}
}