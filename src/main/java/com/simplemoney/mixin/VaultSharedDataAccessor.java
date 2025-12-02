package com.simplemoney.mixin;

import net.minecraft.block.vault.VaultSharedData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Set;
import java.util.UUID;

@Mixin(VaultSharedData.class)
public interface VaultSharedDataAccessor {
    // Zugriff auf das Set "connectedPlayers"
    @Accessor("connectedPlayers")
    Set<UUID> getConnectedPlayersSet();
}