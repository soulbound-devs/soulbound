package net.vakror.soulbound.screen.slot;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.vakror.soulbound.items.ModItems;
import net.vakror.soulbound.items.custom.seals.SealItem;
import org.jetbrains.annotations.NotNull;

public class ModSoulSlot extends SlotItemHandler {
    public ModSoulSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return stack.getItem().equals(ModItems.SOUL.get());
    }
}
