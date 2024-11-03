package com.jkong.enderport.items;

import com.jkong.enderport.Enderport;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class EPItems {

    public static Item registerItem(String id, Item item) {
        Identifier itemID = Identifier.of(Enderport.MOD_ID, id);

        return Registry.register(Registries.ITEM,itemID,item);
    }

    public static final Item ENDERPORT_STICK = registerItem(
            "enderport_stick",
            new EnderportStick());

    public static final Item POWDER_OF_TWIST = registerItem(
            "powder_of_twist",
            new Item(new Item.Settings()));

    public static final Item CRYSTAL_OF_DIMENSION = registerItem(
            "crystal_of_dimension",
            new Item(new Item.Settings()));

    public static void Initialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT)
                .register((itemGroup) -> itemGroup.add(EPItems.ENDERPORT_STICK));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS)
                .register((itemGroup) -> itemGroup.add(EPItems.POWDER_OF_TWIST));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS)
                .register((itemGroup) -> itemGroup.add(EPItems.CRYSTAL_OF_DIMENSION));
    }
}
