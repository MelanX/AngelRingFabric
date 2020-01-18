package de.melanx.angelring.items;

import dev.emi.trinkets.api.ITrinket;
import dev.emi.trinkets.api.SlotGroups;
import dev.emi.trinkets.api.Slots;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ItemAngelRing extends Item implements ITrinket {

    public ItemAngelRing() {
        super(new Settings().group(ItemGroup.MISC).maxCount(1));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        return ITrinket.equipTrinket(player, hand);
    }

    @Override
    public boolean canWearInSlot(String group, String slot) {
        return group.equals(SlotGroups.OFFHAND) && slot.equals(Slots.RING);
    }

    @Override
    public void onEquip(PlayerEntity player, ItemStack stack) {
        player.abilities.allowFlying = true;
    }

    @Override
    public void onUnequip(PlayerEntity player, ItemStack stack) {
        if (!player.isCreative() && !player.isSpectator()) {
            player.abilities.flying = false;
            player.abilities.allowFlying = false;
        }
    }
}
