package de.melanx.angelring;

import de.melanx.angelring.items.ModItems;
import dev.emi.trinkets.api.SlotGroups;
import dev.emi.trinkets.api.Slots;
import dev.emi.trinkets.api.TrinketSlots;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class AngelRing implements ModInitializer {
    @Override
    public void onInitialize() {
        ModItems.init();
        TrinketSlots.addSlot(SlotGroups.OFFHAND, Slots.RING, new Identifier("trinkets", "textures/item/empty_trinket_slot_ring.png"));
    }

    public static void createExplosion(World world, Entity entity, ItemStack stack) {
        if (stack.hasTag())
            world.createExplosion(entity instanceof ItemEntity ? entity : null, entity.getX(), entity.getY() + 4, entity.getZ(), 4.0F, true, Explosion.DestructionType.BREAK);
    }
}
