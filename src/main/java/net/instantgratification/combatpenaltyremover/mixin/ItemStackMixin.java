package net.instantgratification.combatpenaltyremover.mixin;

import net.instantgratification.combatpenaltyremover.registry.CombatPenaltyRules;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.tags.ItemTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * ItemStack Mixin for Melee Combat Protection
 * Verified against: Version 26.1 Snapshot 11+
 */
@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    /**
     * @author Antigravity (Zenith Studio)
     * @reason Intercept melee combat durability loss triggers.
     *         Refined logic: Distinguishes between primary weapons and tools/axes using tags.
     */
    @Inject(method = "postHurtEnemy", at = @At("HEAD"), cancellable = true)
    private void cpr$preventMeleeDurabilityLoss(LivingEntity mob, LivingEntity attacker, CallbackInfo ci) {
        if (attacker.level() instanceof ServerLevel serverLevel) {
            ItemStack stack = (ItemStack)(Object)this;

            boolean preventWeaponDamage = serverLevel.getGameRules().get(CombatPenaltyRules.PREVENT_WEAPON_COMBAT_DAMAGE);
            boolean treatAxesAsWeapons = serverLevel.getGameRules().get(CombatPenaltyRules.TREAT_AXES_AS_WEAPONS);
            boolean preventSpamLoss = serverLevel.getGameRules().get(CombatPenaltyRules.PREVENT_SPAM_ATTACK_DURABILITY_LOSS);
            boolean preventPvPDamage = serverLevel.getGameRules().get(CombatPenaltyRules.PREVENT_WEAPON_PVP_DAMAGE);

            // Get the attack strength captured by PlayerMixin
            float capturedStrength = CombatPenaltyRules.CAPTURED_ATTACK_STRENGTH.get();

            // 1. PvP Protection
            // If the target is a player and PvP protection is active, cancel durability loss.
            if (mob instanceof Player && preventPvPDamage) {
                ci.cancel();
                return;
            }

            // 2. Spam Attack Protection (Charge < 90%)
            // When spamming, damage is heavily reduced, but durability loss remains.
            // This toggle prevents tools/weapons from breaking during rapid uncharged clicks.
            if (preventSpamLoss && capturedStrength < 0.9F) {
                ci.cancel();
                return;
            }

            boolean isPrimaryWeapon = stack.is(ItemTags.SWORDS) || stack.is(Items.TRIDENT) || stack.is(Items.MACE) || stack.is(ItemTags.SPEARS);
            boolean isToolWithWeaponProperty = stack.is(ItemTags.AXES) || stack.is(ItemTags.PICKAXES) || stack.is(ItemTags.SHOVELS) || stack.is(ItemTags.HOES);

            // 3. Primary Weapons
            if (isPrimaryWeapon && preventWeaponDamage) {
                ci.cancel();
                return;
            }

            // 3. Tools (Axes, Pickaxes, etc.)
            // In Snapshot 11, tools registered via ToolMaterial have a weapon cost of 2.
            if (isToolWithWeaponProperty) {
                if (treatAxesAsWeapons) {
                    if (preventWeaponDamage) {
                        // Fully protected (0 damage)
                        ci.cancel();
                    } else {
                        // Normalize 2 damage -> 1 damage (treated like a sword)
                        stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
                        ci.cancel();
                    }
                }
                // If treatAxesAsWeapons is OFF, vanilla proceeds with its default cost (usually 2).
            }
        }
    }
}
