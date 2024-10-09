package com.jkong.enderport;

import net.fabricmc.fabric.api.item.v1.EnchantingContext;
import net.minecraft.block.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import static net.minecraft.sound.SoundEvents.ENTITY_ENDERMAN_TELEPORT;

public class EnderportStick extends SwordItem {


    public EnderportStick() {
        super(ToolMaterials.DIAMOND,(new Settings()).fireproof().rarity(Rarity.EPIC).attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.DIAMOND,8,-2.4F)));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand){

        int blockCount = 9;
        Vec3d posD = user.getRotationVector().normalize();
        int trytime = 0;

        for(int i = 0; i < 10; i++) {
            BlockState stateHead = world.getBlockState(user.getBlockPos().offset(Direction.Axis.X, (int)Math.round(i*posD.getX())).offset(Direction.Axis.Y, (int)Math.round(i*posD.getY())).offset(Direction.Axis.Z, (int)Math.round(i*posD.getZ())).offset(Direction.UP, 1));

            if (stateHead.isOpaque() || stateHead.getBlock() instanceof TransparentBlock || stateHead.getBlock() instanceof PaneBlock || stateHead.getBlock() instanceof LeavesBlock) {
                if (i <= 4) {
                    return TypedActionResult.pass(user.getStackInHand(hand));
                } else {
                    blockCount = i;
                }
            }
        }

        BlockPos tryPos = user.getBlockPos().offset(Direction.Axis.X, (int)Math.round(blockCount*posD.getX())).offset(Direction.Axis.Y, (int)Math.round(blockCount*posD.getY())).offset(Direction.Axis.Z, (int)Math.round(blockCount*posD.getZ()));

        while (true){
            if (trytime > 8) {
                return TypedActionResult.pass(user.getStackInHand(hand));
            }

            if (!(world.getBlockState(tryPos).getBlock() instanceof AirBlock || world.getBlockState(tryPos).getBlock() instanceof FluidBlock)) {
                tryPos = tryPos.offset(Direction.UP,1);
                trytime++;
            } else {break;}
        }

        user.setPosition(Vec3d.of(tryPos));
        user.setVelocity(0,0,0);
        user.fallDistance = 0;
        user.playSoundToPlayer(ENTITY_ENDERMAN_TELEPORT, SoundCategory.VOICE,0.6f, 0.9f);
        user.getItemCooldownManager().set(this, 5);
        user.getStackInHand(hand).damage(1,user, hand.name().equals("main_hand") ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
        return TypedActionResult.success(user.getStackInHand(hand));

    }

    @Override
    public boolean canBeEnchantedWith(ItemStack stack, RegistryEntry<Enchantment> enchantment, EnchantingContext context) {
        for (RegistryKey<Enchantment> ench : EnchantmentsAcceptable.getList()) {
            if (enchantment.matchesKey(ench)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public float getBonusAttackDamage(Entity target, float baseAttackDamage, DamageSource damageSource) {
        MinecraftServer server = target.getServer();
        if (server != null) {
            for (PlayerEntity player : server.getPlayerManager().getPlayerList()) {
                if (player.getStackInHand(Hand.MAIN_HAND).getItem() == this || player.getStackInHand(Hand.OFF_HAND).getItem() == this){
                    if (player.isSneaking()) {
                        return 12.0F;
                    }
                }
            }
        }

        return 0.0F;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target instanceof EndermanEntity && target.isDead()){
            if (attacker instanceof PlayerEntity){
                if (attacker.getStackInHand(Hand.MAIN_HAND).getItem() instanceof EnderportStick || attacker.getStackInHand(Hand.OFF_HAND).getItem() instanceof EnderportStick){
                    attacker.getStackInHand(attacker.getStackInHand(Hand.MAIN_HAND).getItem() instanceof EnderportStick ? Hand.MAIN_HAND : Hand.OFF_HAND).damage(-40,attacker,attacker.getStackInHand(Hand.MAIN_HAND).getItem() instanceof EnderportStick ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
                }
            }
        } else if ((target instanceof WardenEntity || target instanceof EnderDragonEntity || target instanceof WitherEntity) && target.isDead()){
            if (attacker instanceof PlayerEntity){
                if (attacker.getStackInHand(Hand.MAIN_HAND).getItem() instanceof EnderportStick || attacker.getStackInHand(Hand.OFF_HAND).getItem() instanceof EnderportStick){
                    attacker.getStackInHand(attacker.getStackInHand(Hand.MAIN_HAND).getItem() instanceof EnderportStick ? Hand.MAIN_HAND : Hand.OFF_HAND).damage(-1500,attacker,attacker.getStackInHand(Hand.MAIN_HAND).getItem() instanceof EnderportStick ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
                }
            }
        }
        return true;
    }
}
