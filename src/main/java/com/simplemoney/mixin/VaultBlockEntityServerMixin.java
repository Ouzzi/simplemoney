package com.simplemoney.mixin;

import com.simplemoney.util.IVaultCooldown;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.VaultBlockEntity;
import net.minecraft.block.vault.VaultConfig;
import net.minecraft.block.vault.VaultServerData;
import net.minecraft.block.vault.VaultSharedData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;
import java.util.UUID;

@Mixin(VaultBlockEntity.Server.class)
public class VaultBlockEntityServerMixin {

    @Inject(method = "tryUnlock", at = @At("HEAD"))
    private static void checkCooldown(ServerWorld world, BlockPos pos, BlockState state, VaultConfig config, VaultServerData serverData, VaultSharedData sharedData, PlayerEntity player, ItemStack stack, CallbackInfo ci) {
        if (serverData instanceof IVaultCooldown cooldownData) {
            UUID uuid = player.getUuid();
            long now = world.getTime();

            // 1. Zugriff auf Server-Liste (Verhinderte Spieler) via Accessor
            Set<UUID> rewardedPlayers = ((VaultServerDataAccessor) serverData).getRewardedPlayersSet();

            // Wenn der Spieler schon in der Liste ist...
            if (rewardedPlayers.contains(uuid)) {
                // ...aber der Cooldown ist abgelaufen...
                if (!cooldownData.hasLootedRecently(uuid, now)) {

                    // -> Spieler aus der Server-Liste löschen (damit er looten darf)
                    rewardedPlayers.remove(uuid);

                    // 2. Zugriff auf Client-Sync-Liste (Partikel) via Accessor
                    // Damit auch die Partikel-Effekte für den Spieler zurückgesetzt werden
                    Set<UUID> connectedPlayers = ((VaultSharedDataAccessor) sharedData).getConnectedPlayersSet();
                    connectedPlayers.remove(uuid);
                }
            }
        }
    }

    @Inject(method = "tryUnlock", at = @At("TAIL"))
    private static void saveCooldown(ServerWorld world, BlockPos pos, BlockState state, VaultConfig config, VaultServerData serverData, VaultSharedData sharedData, PlayerEntity player, ItemStack stack, CallbackInfo ci) {
        // Zugriff via Accessor prüfen, ob Loot erfolgreich war
        Set<UUID> rewardedPlayers = ((VaultServerDataAccessor) serverData).getRewardedPlayersSet();

        if (rewardedPlayers.contains(player.getUuid())) {
            if (serverData instanceof IVaultCooldown cooldownData) {
                cooldownData.markLooted(player.getUuid(), world.getTime());
            }
        }
    }
}