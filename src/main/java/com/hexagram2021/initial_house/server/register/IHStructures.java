package com.hexagram2021.initial_house.server.register;

import com.hexagram2021.initial_house.server.world.structures.InitialHouseStructure;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.event.RegistryEvent;

import static com.hexagram2021.initial_house.InitialHouse.MODID;

public final class IHStructures {
	public static final InitialHouseStructure INITIAL_HOUSE = new InitialHouseStructure(NoneFeatureConfiguration.CODEC);

	private IHStructures() {
	}

	public static void init(RegistryEvent.Register<StructureFeature<?>> event) {
		RegistryHelper.register(event, new ResourceLocation(MODID, "initial_house"), INITIAL_HOUSE);
	}
}
