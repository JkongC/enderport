package com.jkong.enderport.items;

import com.jkong.enderport.components.EPComponents;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.function.Function;

public class EPItems {

    public static final Item ENDERPORT_STICK = register(
            "enderport_stick",
            EnderportStick::new,
            (new Item.Settings()).sword(ToolMaterial.DIAMOND, 10, -2.4F).fireproof().rarity(Rarity.EPIC).enchantable(22).component(EPComponents.ENDERSOULS, 0)
    );

    public static final Item DIMENSION_PICKAXE = register(
            "dimension_pickaxe",
            DimensionPickaxe::new,
            (new Item.Settings()).pickaxe(ToolMaterial.NETHERITE, 4.0F, -2.8F).fireproof().rarity(Rarity.EPIC).enchantable(22).component(EPComponents.ENDERSOULS, 0)
    );

    public static final Item POWDER_OF_TWIST = register(
            "powder_of_twist",
            Item::new,
            (new Item.Settings()).rarity(Rarity.EPIC)
    );

    public static final Item CRYSTAL_OF_DIMENSION = register(
            "crystal_of_dimension",
            Item::new,
            (new Item.Settings()).rarity(Rarity.EPIC)
    );

    public static Item register(String path, Function<Item.Settings, Item> factory, Item.Settings settings) {
        final RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of("enderport", path));
        return Items.register(registryKey, factory, settings);
    }

    public static void Initialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT)
                .register((itemGroup) -> itemGroup.add(EPItems.ENDERPORT_STICK));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS)
                .register((itemGroup) -> itemGroup.add(EPItems.DIMENSION_PICKAXE));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS)
                .register((itemGroup) -> itemGroup.add(EPItems.POWDER_OF_TWIST));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS)
                .register((itemGroup) -> itemGroup.add(EPItems.CRYSTAL_OF_DIMENSION));
    }
}
