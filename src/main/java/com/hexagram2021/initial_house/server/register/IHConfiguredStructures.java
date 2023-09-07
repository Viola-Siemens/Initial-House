package com.hexagram2021.initial_house.server.register;

import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.common.Tags;

import static com.hexagram2021.initial_house.InitialHouse.MODID;

public final class IHConfiguredStructures {
	public static final Holder<ConfiguredStructureFeature<?, ?>> INITIAL_HOUSE = register(
			new ResourceLocation(MODID, "initial_house"),
			IHStructures.INITIAL_HOUSE.configured(NoneFeatureConfiguration.INSTANCE, Tags.Biomes.IS_OVERWORLD, true)
	);

	private IHConfiguredStructures() {
	}

	private static Holder<ConfiguredStructureFeature<?, ?>> register(ResourceLocation id, ConfiguredStructureFeature<?, ?> configuredStructureFeature) {
		return BuiltinRegistries.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, id, configuredStructureFeature);
	}

	public static void init() {
	}
}
