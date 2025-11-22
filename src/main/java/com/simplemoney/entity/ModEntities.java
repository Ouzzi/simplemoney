package com.simplemoney.entity;

import com.simplemoney.Simplemoney;
import com.simplemoney.entity.custom.EnhancedFireworkRocket;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder; // WICHTIG: Verwenden Sie den Fabric Builder
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModEntities {

    // 1. Deklarieren Sie den RegistryKey für die Entity-ID
    private static final RegistryKey<EntityType<?>> ENHANCED_FIREWORK_ROCKET_KEY = RegistryKey.of(
            RegistryKeys.ENTITY_TYPE,
            Identifier.of(Simplemoney.MOD_ID, "enhanced_firework_rocket")
    );

    // 2. Registrierung des EntityType unter dem Identifier
    public static final EntityType<EnhancedFireworkRocket> ENHANCED_FIREWORK_ROCKET_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,
            ENHANCED_FIREWORK_ROCKET_KEY, // Registrierung unter dem Key oder Identifier

            FabricEntityTypeBuilder.<EnhancedFireworkRocket>create(
                            SpawnGroup.MISC,
                            EnhancedFireworkRocket::new
                    )
                    .dimensions(EntityDimensions.fixed(0.25F, 0.25F))
                    .trackRangeChunks(4)
                    .disableSaving()
                    .disableSummon()

                    // 3. Übergabe des RegistryKey an die build()-Methode (erfüllt die Anforderung)
                    .build(ENHANCED_FIREWORK_ROCKET_KEY)
    );


    public static void registerModEntities() {
        Simplemoney.LOGGER.info("Registering Mod Entities for " + Simplemoney.MOD_ID);
        // Durch die statische Initialisierung ist die Entity bereits registriert.
    }
}