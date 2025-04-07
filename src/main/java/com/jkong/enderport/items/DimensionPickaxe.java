package com.jkong.enderport.items;

import com.jkong.enderport.components.EPComponents;
import com.jkong.enderport.enchantments.EnchantmentsAcceptable;
import com.jkong.enderport.manager.StatsHolder;
import net.fabricmc.fabric.api.item.v1.EnchantingContext;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DimensionPickaxe extends Item {

    public static final List<RegistryKey<Enchantment>> EnchantmentList = EnchantmentsAcceptable.getListOfPickaxe();

    public DimensionPickaxe(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerWorld world, Entity entity, @Nullable EquipmentSlot slot) {
        if (entity instanceof ServerPlayerEntity player) {
            stack.set(EPComponents.ENDERSOULS, ((StatsHolder) player).getEndersouls());
        }
    }

    @Override
    public boolean canBeEnchantedWith(ItemStack stack, RegistryEntry<Enchantment> enchantment, EnchantingContext context) {
        for (RegistryKey<Enchantment> enchantments : EnchantmentList) {
            if (enchantment.matchesKey(enchantments)) {
                return true;
            }
        }

        return false;
    }
}
