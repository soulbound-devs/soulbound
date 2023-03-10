package net.vakror.soulbound.seal.seals;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.vakror.soulbound.seal.ISeal;

public class Seal implements ISeal {
    private final String id;
    private final boolean canBeActivated;

    public Seal(String id, boolean canBeActivated) {
        this.id = id;
        this.canBeActivated = canBeActivated;
    }

    public Seal(String id) {
        this.id = id;
        this.canBeActivated = false;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean canBeActivated() {
        return canBeActivated;
    }
}
