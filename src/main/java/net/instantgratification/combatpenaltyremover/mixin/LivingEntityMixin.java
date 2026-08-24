package net.instantgratification.combatpenaltyremover.mixin;

import net.instantgratification.combatpenaltyremover.registry.CombatPenaltyRules;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * LivingEntity Mixin for Armor/Equipment Protection
 * Verified against: Version 26.1 Snapshot 11+
 * Hook point: doHurtEquipment (provides DamageSource context)
 */
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    /**
     * @author Antigravity (Zenith Studio)
     * @reason Intercept armor/equipment damage with full context.
     */
    @Inject(method = "doHurtEquipment", at = @At("HEAD"), cancellable = true)
    private void cpr$conditionalArmorDamage(DamageSource source, float amount, EquipmentSlot[] slots, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        
        // Target players only for this mod.
        if (!(entity instanceof Player player)) {
            return;
        }

        if (entity.level() instanceof ServerLevel serverLevel) {
            // "Combat" generally means damage FROM an entity.
            boolean isPvP = source.getEntity() instanceof Player || source.getDirectEntity() instanceof Player;
            boolean isCombat = source.getEntity() != null || source.getDirectEntity() != null;
            
            if (isPvP) {
                if (serverLevel.getGameRules().get(CombatPenaltyRules.PREVENT_ARMOR_PVP_DAMAGE)) {
                    ci.cancel();
                    return;
                }
            }

            if (isCombat) {
                if (serverLevel.getGameRules().get(CombatPenaltyRules.PREVENT_ARMOR_COMBAT_DAMAGE)) {
                    ci.cancel();
                }
            } else {
                // Environmental (Fire, Fall, etc.)
                if (serverLevel.getGameRules().get(CombatPenaltyRules.PREVENT_ARMOR_ENVIRONMENTAL_DAMAGE)) {
                    ci.cancel();
                }
            }
        }
    }
}
