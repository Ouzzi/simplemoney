package com.simplemoney.mixin;

import net.minecraft.block.vault.VaultServerData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Set;
import java.util.UUID;

@Mixin(VaultServerData.class)
public interface VaultServerDataAccessor {
    // Wir greifen direkt auf das Feld "rewardedPlayers" zu
    @Accessor("rewardedPlayers")
    Set<UUID> getRewardedPlayersSet();
}