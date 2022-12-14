package com.myname.mymodid;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import scala.util.Random;

public class GravelEvent {

    public static boolean update(Entity aPlayer) {
        if (aPlayer instanceof EntityPlayer
                && !aPlayer.worldObj.isRemote
                && ((EntityPlayer) aPlayer).openContainer != null)
            ((EntityPlayer) aPlayer).openContainer.detectAndSendChanges();
        return true;
    }

    @SubscribeEvent
    public void onInteract(PlayerInteractEvent event) {
        if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK && !event.world.isRemote) {
            if (Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem() == null) {
                MovingObjectPosition pos = Minecraft.getMinecraft().objectMouseOver;
                Block b = event.entityPlayer.worldObj.getBlock(pos.blockX, pos.blockY, pos.blockZ);
                if (Item.getItemFromBlock(b) == Item.getItemFromBlock(Blocks.gravel)) {
                    event.world.playSoundAtEntity(event.entityPlayer, "dig.gravel", 0.45F, 1.15F);
                    if (new Random().nextInt(10) == 1) {
                        event.entityPlayer.worldObj.setBlock(pos.blockX, pos.blockY, pos.blockZ, Blocks.air, 0, 3);
                        ItemStack flint = new ItemStack(Items.flint);
                        if (event.entityPlayer.inventory.addItemStackToInventory(flint)) {
                            update(event.entityPlayer);
                        } else {
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
