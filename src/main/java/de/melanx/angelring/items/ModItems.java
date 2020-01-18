package de.melanx.angelring.items;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {

    public static ItemAngelRing angelring = new ItemAngelRing();
    public static ItemUnstableIngot unstable_ingot = new ItemUnstableIngot();

    public static void init() {
        Registry.register(Registry.ITEM, new Identifier("angelring", "angelring"), angelring);
        Registry.register(Registry.ITEM, new Identifier("angelring", "unstable_ingot"), unstable_ingot);
    }

}
