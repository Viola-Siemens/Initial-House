package com.hexagram2021.initial_house.server;

import com.hexagram2021.initial_house.server.register.*;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.hexagram2021.initial_house.InitialHouse.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class IHContent {
	public static void modConstruct(IEventBus bus) {

	}

	@SubscribeEvent
	public static void registerStructures(RegistryEvent.Register<StructureFeature<?>> event) {
		IHStructurePlacementTypes.init();
		IHStructures.init(event);
		IHStructurePieceTypes.init();
		IHConfiguredStructures.init();
		IHStructureSets.init();
	}
}
