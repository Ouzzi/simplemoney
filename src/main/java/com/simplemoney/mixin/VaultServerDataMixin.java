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
    private Map<UUID, Long> lastLootTimes = new HashMap<>(); // Kein final mehr, damit wir es überschreiben können

    @Override
    public boolean hasLootedRecently(UUID playerUuid, long worldTime) {
        if (!lastLootTimes.containsKey(playerUuid)) return false;

        long lastLoot = lastLootTimes.get(playerUuid);

        // ZUGRIFF AUF CONFIG
        // 1 Tag = 24000 Ticks
        long configDays = Simplemoney.getConfig().vaults.vaultCooldownDays;
        long cooldownTicks = configDays * 24000L;

        return (worldTime - lastLoot) < cooldownTicks;
    }

    @Override
    public void markLooted(UUID playerUuid, long worldTime) {
        lastLootTimes.put(playerUuid, worldTime);
    }

    // --- NEUE METHODEN FÜR CODECS ---
    @Override
    public Map<UUID, Long> getLootTimesMap() {
        return lastLootTimes;
    }

    @Override
    public void setLootTimesMap(Map<UUID, Long> map) {
        this.lastLootTimes = new HashMap<>(map); // Map kopieren/ersetzen
    }
}