package com.hexagram2021.initial_house;

import com.hexagram2021.initial_house.common.IHContent;
import com.hexagram2021.initial_house.common.config.IHCommonConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(InitialHouse.MODID)
public class InitialHouse {
	public static final String MODID = "initial_house";

	public InitialHouse() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, IHCommonConfig.getConfig());

		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		IHContent.modConstruct(bus);

		MinecraftForge.EVENT_BUS.register(this);
	}
}
