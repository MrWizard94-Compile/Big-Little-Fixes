package net.minecraftforge.fml.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Minimal stub of the Forge {@code @Mod} annotation to allow compilation of the mod skeleton
 * when a full NeoForge MDK is not available. This is a compile-time placeholder only and will
 * be replaced with the real annotation type when the official MDK is used.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Mod {
    /**
     * The mod id string (e.g. "biglittlefixes").
     *
     * @return the mod id
     */
    String value();
}
