package net.instantgratification.combatpenaltyremover.mixin;

import net.instantgratification.combatpenaltyremover.registry.CombatPenaltyRules;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin to handle attack strength capture and speed scaling.
 * Verified against: Version 26.1 Snapshot 11+
 */
@Mixin(Player.class)
public abstract class PlayerMixin {

    /**
     * Captures the attack strength scale at the start of an attack.
     * This allows ItemStackMixin to determine if it was a "spam attack".
     */
    @Inject(method = "attack", at = @At("HEAD"))
    private void cpr$captureAttackStrength(Entity target, CallbackInfo ci) {
        Player player = (Player) (Object) this;
        // attack() uses 0.5F for scale calculation in Player.java:951
        CombatPenaltyRules.CAPTURED_ATTACK_STRENGTH.set(player.getAttackStrengthScale(0.5F));
    }

    /**
     * Modifies the attack recharge speed based on the Attack Speed Efficiency GameRule.
     */
    @Inject(method = "getCurrentItemAttackStrengthDelay", at = @At("RETURN"), cancellable = true)
    private void cpr$applyAttackSpeedEfficiency(CallbackInfoReturnable<Float> cir) {
        Player player = (Player) (Object) this;
        // GameRules are on ServerLevel. Client will use default (100) or we need syncing.
        if (player.level() instanceof ServerLevel serverLevel) {
            int efficiency = serverLevel.getGameRules().get(CombatPenaltyRules.ATTACK_SPEED_EFFICIENCY);
            
            if (efficiency != 100 && efficiency > 0) {
                float multiplier = efficiency / 100.0F;
                // Delay is inversely proportional to speed (higher speed = lower delay)
                cir.setReturnValue(cir.getReturnValue() / multiplier);
            }
        }
    }
}
