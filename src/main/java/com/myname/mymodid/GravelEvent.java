package com.myname.mymodid;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import scala.util.Random;

public class GravelEvent {

    @SubscribeEvent
    public void onInteract(PlayerInteractEvent event) {
        if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK && !event.world.isRemote) {
            if (Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem() == null) {
                MovingObjectPosition pos = Minecraft.getMinecraft().objectMouseOver;
                Block b = event.entityPlayer.worldObj.getBlock(pos.blockX, pos.blockY, pos.blockZ);
                if (Item.getItemFromBlock(b) == Item.getItemFromBlock(Blocks.gravel)) {
                    if (new Random().nextInt(10) == 1) {
                        event.entityPlayer.worldObj.setBlock(pos.blockX, pos.blockY, pos.blockZ, Blocks.air, 0, 3);
                        ItemStack flint = new ItemStack(Items.flint);
                        if (!event.entityPlayer.inventory.addItemStackToInventory(flint)) {
                            EntityItem item = new EntityItem(
                                    event.entityPlayer.worldObj,
                                    pos.blockX,
                                    pos.blockY,
                                    pos.blockZ,
                                    new ItemStack(Items.flint));
                            event.entityPlayer.worldObj.spawnEntityInWorld(item);
                        }
                    }
                }
            }
        }
    }
}
