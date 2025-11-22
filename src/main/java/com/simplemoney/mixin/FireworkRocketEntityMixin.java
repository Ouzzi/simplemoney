package com.simplemoney.mixin;

import com.simplemoney.entity.custom.EnhancedFireworkRocket;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

// Weisen Sie das Mixin der Vanilla-Klasse zu, die wir modifizieren möchten.
@Mixin(FireworkRocketEntity.class)
public abstract class FireworkRocketEntityMixin {

    // Da der Mixin die Klasse FireworkRocketEntity 'erweitert' (im Prinzip),
    // können wir auf das 'shooter' Feld zugreifen, wenn wir es hier deklarieren.
    // Wir benötigen *keinen* Access Widener für dieses spezifische Feld in diesem Mixin,
    // da wir es nur abfragen (if (this.shooter instanceof EnhancedFireworkRocket ...)).
    @Shadow(remap = false) // remap=false ist oft für Fields in Mixins nötig.
    @Nullable
    private LivingEntity shooter;

    // --- Modifikation des Velocity-Boost-Werts (Vanilla: 1.5) ---
    // Wir zielen auf die Konstante 1.5 in der Methode tick() der FireworkRocketEntity
    @ModifyConstant(
            method = "tick",
            constant = @Constant(doubleValue = 1.5) // Der Wert, den wir ersetzen möchten
    )
    private double simplemoney$modifyVelocityBoost(double original) {
        // Überprüfen Sie, ob es sich bei dieser Rakete um unsere 'EnhancedFireworkRocket' handelt.
        // Der Mixin ist auf FireworkRocketEntity angewendet, daher muss 'this' eine FireworkRocketEntity sein.
        // Die Prüfung stellt sicher, dass NUR unsere Custom-Rakete betroffen ist.
        if ((FireworkRocketEntity)(Object)this instanceof EnhancedFireworkRocket enhancedRocket) {
            // Holen Sie den Custom Speed Multiplier von Ihrer Klasse.
            double speedMultiplier = enhancedRocket.getSpeedMultiplier();
            // Geben Sie den neuen, modifizierten Wert zurück (1.5 * Multiplikator)
            return original * speedMultiplier;
        }
        // Für alle anderen (Vanilla) Raketen, geben Sie den Originalwert zurück.
        return original;
    }

    // --- Modifikation des Acceleration-Werts (Vanilla: 0.1) ---
    // Wir zielen auf die Konstante 0.1 in der Methode tick()
    @ModifyConstant(
            method = "tick",
            constant = @Constant(doubleValue = 0.1) // Der Wert, den wir ersetzen möchten
    )
    private double simplemoney$modifyAcceleration(double original) {
        // Dieselbe Logik wie oben: Nur unsere Rakete modifizieren.
        if ((FireworkRocketEntity)(Object)this instanceof EnhancedFireworkRocket enhancedRocket) {
            double speedMultiplier = enhancedRocket.getSpeedMultiplier();
            return original * speedMultiplier;
        }
        return original;
    }
}