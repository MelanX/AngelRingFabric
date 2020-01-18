package de.melanx.angelring.mixin;

import com.mojang.authlib.GameProfile;
import de.melanx.angelring.AngelRing;
import de.melanx.angelring.items.ItemUnstableIngot;
import de.melanx.angelring.items.ModItems;
import net.minecraft.container.CraftingTableContainer;
import net.minecraft.container.PlayerContainer;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {

    private int[] ids_inventory = initialise_ids_inventory();
    private int[] ids_crafting_table = initialise_ids_crafting_table();

    public ServerPlayerEntityMixin(World world, GameProfile profile) {
        super(world, profile);
    }

    @Inject(at = @At("INVOKE"), method = "dropItem", cancellable = true)
    private void dropItem(ItemStack stack, boolean bl, boolean bl2, CallbackInfoReturnable<ItemEntity> cir) {
        if (stack.getItem() == ModItems.unstable_ingot) {
            ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
            ItemEntity entity = new ItemEntity(world, player.getX(), player.getY(), player.getZ(), new ItemStack(ModItems.unstable_ingot));
            AngelRing.createExplosion(world, entity, stack);
            cir.cancel();
        }
    }

    @Inject(at = @At("INVOKE"), method = "tick")
    public void tick(CallbackInfo ci) {
        if (this.container instanceof PlayerContainer) {
            stackTick(ids_inventory);
        } else if (this.container instanceof CraftingTableContainer) {
            stackTick(ids_crafting_table);
        }
    }

    private void stackTick(int[] list) {
        for (int i = 0; i < this.container.slotList.size(); i++) {
            for (int id : list) {
                ItemStack stack = this.container.getSlot(id).getStack();
                if (stack.getItem() == ModItems.unstable_ingot) {
                    stack.inventoryTick(world, this, i, false);
                }
            }
        }
    }

    private int[] initialise_ids_inventory() {
        return ids_inventory = new int[]{1, 2, 3, 4};
    }

    private int[] initialise_ids_crafting_table() {
        return ids_crafting_table = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
    }
}
