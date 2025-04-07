package com.jkong.enderport.items;

import com.jkong.enderport.components.EPComponents;
import com.jkong.enderport.enchantments.EnchantmentsAcceptable;
import com.jkong.enderport.manager.StatsHolder;
import com.jkong.enderport.network.ClientPacketSenderProvider;
import com.jkong.enderport.network.StickTpPayload;
import com.jkong.enderport.network.StickUsePayload;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.item.v1.EnchantingContext;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static net.minecraft.sound.SoundEvents.ENTITY_ENDERMAN_TELEPORT;

public class EnderportStick extends Item {

    public static final List<RegistryKey<Enchantment>> EnchantmentList = EnchantmentsAcceptable.getListOfStick();

    public EnderportStick(Item.Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand){
        if (world.isClient()) {
            clientUse(hand);
        }
        return ActionResult.SUCCESS;
    }

    @Environment(EnvType.CLIENT)
    public static void clientUse(Hand hand) {
        ClientPacketSenderProvider.get().send(new StickUsePayload(hand));
    }

    public static void serverUse(ServerWorld world, ServerPlayerEntity user, Hand hand) {
        int blockCount = 9;
        Vec3d posD = user.getRotationVector();
        BlockPos originalPos = user.getBlockPos();
        int trytime = 0;

        for(int i = 0; i < 10; i++) {
            BlockPos posHead = originalPos.offset(Direction.Axis.X, (int)Math.round(i*posD.getX())).offset(Direction.Axis.Y, (int)Math.round(i*posD.getY())).offset(Direction.Axis.Z, (int)Math.round(i*posD.getZ())).offset(Direction.UP, 1);
            BlockState stateHead = world.getBlockState(posHead);

            if (stateHead.getBlock().collidable) {
                if (i <= 4) {
                    return;
                } else {
                    blockCount = i;
                }
            }
        }

        BlockPos tryPos = originalPos.offset(Direction.Axis.X, (int)Math.round(blockCount*posD.getX())).offset(Direction.Axis.Y, (int)Math.round(blockCount*posD.getY())+1).offset(Direction.Axis.Z, (int)Math.round(blockCount*posD.getZ()));

        while (true){
            if (trytime > 8) {
                return;
            }

            if (world.getBlockState(tryPos).getBlock().collidable) {
                tryPos = tryPos.offset(Direction.UP,1);
                trytime++;
            } else {break;}
        }

        Vec3d newPos = tryPos.toCenterPos();
        user.teleport(user.getServerWorld(), newPos.x, newPos.y, newPos.z, Set.of(), user.getYaw(), user.getPitch(), false);
        user.setVelocity(0,0,0);
        user.fallDistance = 0;
        user.playSoundToPlayer(ENTITY_ENDERMAN_TELEPORT, SoundCategory.VOICE,0.6f, 0.9f);
        user.getItemCooldownManager().set(user.getStackInHand(hand), 6);
        ((StatsHolder) user).setStillTicks(40);
        user.getStackInHand(hand).damage(1, user, hand.name().equals("main_hand") ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);

        Vec3d currentPos = user.getPos();
        for (ServerPlayerEntity player : PlayerLookup.tracking(world, user.getBlockPos())) {
            if (player.getPos().isWithinRangeOf(currentPos, 128.0, 128.0)) {
                ServerPlayNetworking.send(player, new StickTpPayload(originalPos, tryPos, currentPos));
            }
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
    public float getBonusAttackDamage(Entity target, float baseAttackDamage, DamageSource damageSource) {
        LivingEntity attacker = (LivingEntity) damageSource.getAttacker();
        ItemStack attackerStack;
        int soul;

        if (attacker != null && attacker.isSneaking() && (attacker.getStackInHand(Hand.MAIN_HAND).getItem() == this || attacker.getStackInHand(Hand.OFF_HAND).getItem() == this)) {
            if (attacker.getStackInHand(Hand.MAIN_HAND).getItem() == this) {
                attackerStack = attacker.getStackInHand(Hand.MAIN_HAND);
            } else {
                attackerStack = attacker.getStackInHand(Hand.OFF_HAND);
            }

            soul = attackerStack.getOrDefault(EPComponents.ENDERSOULS, 0);
            if (soul >= 60) {
                if (soul >= 100) {
                    if (soul == EPComponents.getMaxEndersouls()) {
                        damageThis(attackerStack, attacker, 80);
                        return 200.0F;
                    }
                    damageThis(attackerStack, attacker, 50);
                    return 50.0F + (soul - 100);
                }
                damageThis(attackerStack, attacker, 30);
                return 40.0F;
            }
            damageThis(attackerStack, attacker, 20);
            return 10.0F;
        }

        return 0.0F;
    }

    @Override
    public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {

        if (!target.isDead() || !(attacker instanceof PlayerEntity player)) return;

        if (target instanceof EndermanEntity){
            damageThis(stack, player, -40);
        } else if (target instanceof WardenEntity || target instanceof EnderDragonEntity || target instanceof WitherEntity){
            damageThis(stack, player, -1500);
            if (player instanceof ServerPlayerEntity) {
                for (ServerPlayerEntity eachPlayer : Objects.requireNonNull(player.getServer()).getPlayerManager().getPlayerList()) {
                    addPlayerEndersouls(eachPlayer, 1);
                }
            }
        }
    }

    public void addPlayerEndersouls(ServerPlayerEntity player, int num) {
        ((StatsHolder) player).setEndersouls(getPlayerEndersouls(player) + num);
    }

    public void setPlayerEndersouls(ServerPlayerEntity player, int num) {
        ((StatsHolder) player).setEndersouls(num);
    }

    public int getPlayerEndersouls(ServerPlayerEntity player) {
        return ((StatsHolder) player).getEndersouls();
    }

    private void damageThis(ItemStack stack, LivingEntity attacker, int damage) {
        stack.damage(damage, attacker, attacker.getStackInHand(Hand.MAIN_HAND).getItem() instanceof EnderportStick ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerWorld world, Entity entity, @Nullable EquipmentSlot slot) {
        if (entity instanceof ServerPlayerEntity player) {
            if (getPlayerEndersouls(player) > EPComponents.getMaxEndersouls()) {
                setPlayerEndersouls(player, EPComponents.getMaxEndersouls());
            }

            stack.set(EPComponents.ENDERSOULS, getPlayerEndersouls(player));
        }
    }
}
