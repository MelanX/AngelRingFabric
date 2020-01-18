package de.melanx.angelring.items;

import de.melanx.angelring.AngelRing;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.List;

public class ItemUnstableIngot extends Item {

    public ItemUnstableIngot() {
        super(new Settings().group(ItemGroup.MISC).maxCount(1));

        addPropertyGetter(new Identifier("danger"), ((stack, world, entity) -> {
            if (stack.hasTag() && stack.getTag().getBoolean("danger"))
                return 1;
            return 0;
        }));
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity.isInvulnerable()) {
            super.inventoryTick(stack, world, entity, slot, selected);
            return;
        }

        if (!stack.hasTag()) {
            CompoundTag nbt = new CompoundTag();
            stack.setTag(nbt);
            stack.getTag().putBoolean("danger", false);
            stack.getTag().putLong("start", world.getTime());
        } else {
            CompoundTag nbt = stack.getTag();
            long start = nbt.getLong("start");
            double difference = (double) (world.getTime() - start) / 20;
            setTexture(nbt, difference);
            if (difference >= 10 && !world.isClient()) {
                AngelRing.createExplosion(world, entity, stack);
                stack.decrement(1);
            }
        }
    }

    private void setTexture(CompoundTag nbt, double difference) {
        if (difference >= 8.1)
            changeDanger(nbt, true);
        else if (difference >= 8)
            changeDanger(nbt, false);
        else if (difference >= 7.8)
            changeDanger(nbt, true);
        else if (difference >= 7.6)
            changeDanger(nbt, false);
        else if (difference >= 7.4)
            changeDanger(nbt, true);
        else if (difference >= 7.2)
            changeDanger(nbt, false);
        else if (difference >= 7)
            changeDanger(nbt, true);
        else if (difference >= 6.5)
            changeDanger(nbt, false);
        else if (difference >= 6)
            changeDanger(nbt, true);
        else if (difference >= 5.5)
            changeDanger(nbt, false);
        else if (difference >= 5)
            changeDanger(nbt, true);
    }

    private void changeDanger(CompoundTag nbt, boolean state) {
        if (nbt.getBoolean("danger") != state)
            nbt.putBoolean("danger", state);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        if (stack.hasTag()) {
            long start = stack.getTag().getLong("start");
            long timer = world.getTime();
            long remaining = 10 - (timer - start) / 20;
            tooltip.add(new TranslatableText("angelring:text.remaining", remaining));
        } else {
            for (int i = 0; i <= 3; i++)
                tooltip.add(new TranslatableText("angelring:text.default" + i));
        }
    }
}
