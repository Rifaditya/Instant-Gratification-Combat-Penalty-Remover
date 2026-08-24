package net.instantgratification.combatpenaltyremover;

import net.fabricmc.api.ModInitializer;
import net.instantgratification.combatpenaltyremover.registry.CombatPenaltyRules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Combat Penalty Remover
 * Verified against: Version 26.1 Snapshot 11+
 * Mod Purpose: Remove durability penalties for weapons, armor, and shields via GameRules.
 */
public class CombatPenaltyRemover implements ModInitializer {
    public static final String MOD_ID = "combat-penalty-remover";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Combat Penalty Remover Initializing... (MC 26.1 Snapshot 11 API)");
        CombatPenaltyRules.register();
    }
}
