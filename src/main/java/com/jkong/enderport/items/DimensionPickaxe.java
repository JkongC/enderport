package com.jkong.enderport.items;

import com.jkong.enderport.components.EPComponents;
import com.jkong.enderport.enchantments.EnchantmentsAcceptable;
import com.jkong.enderport.manager.StatsHolder;
import net.fabricmc.fabric.api.item.v1.EnchantingContext;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;

import java.util.List;

public class DimensionPickaxe extends PickaxeItem {

    public static final List<RegistryKey<Enchantment>> EnchantmentList = EnchantmentsAcceptable.getListOfPickaxe();

    public DimensionPickaxe() {
        super(ToolMaterials.NETHERITE, (new Settings()).fireproof().rarity(Rarity.EPIC).component(EPComponents.ENDERSOULS, 0).attributeModifiers(PickaxeItem.createAttributeModifiers(ToolMaterials.NETHERITE,4.0F,-2.8F)));
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof PlayerEntity player && world instanceof ServerWorld) {
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

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return false;
    }

    @Override
    public int getEnchantability() {
        return 22;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if (stack.contains(EPComponents.ENDERSOULS)) {
            int soulcount = stack.getOrDefault(EPComponents.ENDERSOULS, 0);
            tooltip.add(Text.translatable("item.enderport.dimension_pickaxe.soulcounter", soulcount).formatted(Formatting.DARK_PURPLE));
        }
    }
}
