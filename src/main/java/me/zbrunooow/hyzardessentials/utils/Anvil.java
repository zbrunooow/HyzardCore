package me.zbrunooow.hyzardessentials.utils;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.ContainerAnvil;
import net.minecraft.server.v1_8_R3.EntityHuman;

public class Anvil extends ContainerAnvil {

    public Anvil(EntityHuman entityHuman) {
        super(entityHuman.inventory, entityHuman.world, new BlockPosition(0,0,0), entityHuman);
    }

    @Override
    public boolean a(EntityHuman entityHuman) {
        return true;
    }

}
