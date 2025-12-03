package com.simplemoney.mixin;

import com.simplemoney.Simplemoney;
import com.simplemoney.util.IVaultCooldown;
import net.minecraft.block.vault.VaultServerData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mixin(VaultServerData.class)
public class VaultServerDataMixin implements IVaultCooldown {

    @Unique
    private Map<UUID, Long> lastLootTimes = new HashMap<>();

    @Override
    public boolean hasLootedRecently(UUID playerUuid, long worldTime) {
        if (!lastLootTimes.containsKey(playerUuid)) {
            Simplemoney.LOGGER.info("Vault Check: Spieler " + playerUuid + " war noch nie hier. Erlaubt.");
            return false;
        }

        long lastLoot = lastLootTimes.get(playerUuid);
        long diff = worldTime - lastLoot;

        // Config laden mit Fallback
        long configDays = 50; // Standard 100 Tage
        try {
            if (Simplemoney.getConfig() != null) {
                configDays = Simplemoney.getConfig().vaults.vaultCooldownDays;
                Simplemoney.LOGGER.info("Geladener Vault Cooldown aus Config: " + configDays + " Tage");
            }
        } catch (Exception e) {
            Simplemoney.LOGGER.error("Fehler beim Laden der Vault Config, nutze Standard 100 Tage", e);
        }

        long cooldownTicks = configDays * 24000L;

        // Debug Ausgabe in die Konsole
        Simplemoney.LOGGER.info("Vault Check für " + playerUuid + ":");
        Simplemoney.LOGGER.info(" - Letzter Loot: " + lastLoot);
        Simplemoney.LOGGER.info(" - Jetzige Zeit: " + worldTime);
        Simplemoney.LOGGER.info(" - Differenz: " + diff + " Ticks");
        Simplemoney.LOGGER.info(" - Cooldown Zeit: " + cooldownTicks + " Ticks (" + configDays + " Tage)");

        // Wenn Zeit zurückgedreht wurde (diff negativ), erlauben wir es sicherheitshalber
        if (diff < 0) return false;

        boolean isOnCooldown = diff < cooldownTicks;
        Simplemoney.LOGGER.info(" - Noch im Cooldown? " + isOnCooldown);

        return isOnCooldown;
    }

    /*

    @Override
    public boolean hasLootedRecently(UUID playerUuid, long worldTime) {
        if (!lastLootTimes.containsKey(playerUuid)) return false;
        long lastLoot = lastLootTimes.get(playerUuid);

        // TEST: Nur 10 Sekunden Cooldown (200 Ticks)
        return (worldTime - lastLoot) < 200L;
    }
     */

    @Override
    public void markLooted(UUID playerUuid, long worldTime) {
        lastLootTimes.put(playerUuid, worldTime);
        Simplemoney.LOGGER.info("Vault: Spieler " + playerUuid + " hat gelootet bei Zeit " + worldTime);
    }

    @Override
    public Map<UUID, Long> getLootTimesMap() {
        return lastLootTimes;
    }

    @Override
    public void setLootTimesMap(Map<UUID, Long> map) {
        this.lastLootTimes = new HashMap<>(map);
    }
}