package com.hexagram2021.initial_house.server;

import com.hexagram2021.initial_house.server.register.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;

import static com.hexagram2021.initial_house.InitialHouse.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class IHContent {
	public static void modConstruct(IEventBus bus) {
		IHStructurePlacementTypes.init(bus);
		IHStructurePieceTypes.init(bus);
		IHStructureTypes.init(bus);
		IHStructureSetKeys.init();
	}
}
