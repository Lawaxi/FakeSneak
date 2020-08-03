package com.example.examplemod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MovementInputFromOptions;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import scala.Int;

@Mod(modid = ExampleMod.MODID, version = ExampleMod.VERSION)
public class ExampleMod
{
    public static final String MODID = "fakesneak";
    public static final String VERSION = "1.0";
    public SwitchKeyBiding enable = new SwitchKeyBiding("key.fakesneak.on", Keyboard.KEY_F, "key.categories.fakesneak");
    public KeyBinding addgamma = new SwitchKeyBiding("key.fakesneak.addGamma", Keyboard.KEY_N, "key.categories.fakesneak");
    public KeyBinding redgamma = new SwitchKeyBiding("key.fakesneak.redGamma", Keyboard.KEY_M, "key.categories.fakesneak");

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        if(event.getSide()== Side.CLIENT) {

            Minecraft.getMinecraft().gameSettings.keyBindSneak = new FakeKeyBiding("key.sneak", 42, "key.categories.movement");
            Minecraft.getMinecraft().gameSettings.gammaSetting = 21.0F;

            ClientRegistry.registerKeyBinding(enable);
            ClientRegistry.registerKeyBinding(addgamma);
            ClientRegistry.registerKeyBinding(redgamma);

            reloadTitle();


            MinecraftForge.EVENT_BUS.register(this);
        }
    }

    private static final double getInt(double pre){

        String p1 = String.valueOf(pre<0 ? pre-1 : pre);
        return Integer.valueOf(p1.substring(0,p1.indexOf(".")));
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {

        if(!enable.on)
            return;

        if(event.player.worldObj.isAirBlock(new BlockPos(
                getInt(event.player.posX),event.player.getPosition().getY()-1,getInt((int)event.player.posZ)
        ))){
            ((FakeKeyBiding)Minecraft.getMinecraft().gameSettings.keyBindSneak).pressed_permanent = true;
        }else
            ((FakeKeyBiding)Minecraft.getMinecraft().gameSettings.keyBindSneak).pressed_permanent = false;
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event)
    {
        if (enable.isPressed()) {
            enable.on = !enable.on;

            if(!enable.on)
                ((FakeKeyBiding)Minecraft.getMinecraft().gameSettings.keyBindSneak).pressed_permanent = false;
        }
        else if(addgamma.isPressed()){
            Minecraft.getMinecraft().gameSettings.gammaSetting++;
        }
        else if(redgamma.isPressed()){
            Minecraft.getMinecraft().gameSettings.gammaSetting--;
        }

        reloadTitle();

    }


    private static final String next = "; ";
    private final void reloadTitle(){
        Display.setTitle(
                "Fake Sneak: "+enable.on+next
                +"Gamma: "+Minecraft.getMinecraft().gameSettings.gammaSetting);
    }
}
