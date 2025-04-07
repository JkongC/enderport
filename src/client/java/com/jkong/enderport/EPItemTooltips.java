package com.jkong.enderport;

import com.jkong.enderport.components.EPComponents;
import com.jkong.enderport.items.EPItems;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class EPItemTooltips {
    private EPItemTooltips() {}

    public static void initializeTooltip() {
        ItemTooltipCallback.EVENT.register((stack, context, type, tooltip) -> {
            if (stack.getItem() == EPItems.DIMENSION_PICKAXE)
            {
                int soulCount = stack.getOrDefault(EPComponents.ENDERSOULS, 0);
                tooltip.add(Text.translatable("item.enderport.dimension_pickaxe.soulcounter", soulCount).formatted(Formatting.DARK_PURPLE));
            }
        });

        ItemTooltipCallback.EVENT.register((stack, context, type, tooltip) -> {
            if (stack.getItem() == EPItems.ENDERPORT_STICK)
            {
                int soulCount = stack.getOrDefault(EPComponents.ENDERSOULS, 0);
                tooltip.add(Text.translatable("item.enderport.dimension_pickaxe.soulcounter", soulCount).formatted(Formatting.DARK_PURPLE));
            }
        });
    }
}
