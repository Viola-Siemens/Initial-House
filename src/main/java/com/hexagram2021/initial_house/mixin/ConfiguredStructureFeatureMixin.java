package com.hexagram2021.initial_house.mixin;

import com.hexagram2021.initial_house.server.config.IHServerConfig;
import com.hexagram2021.initial_house.server.register.IHStructureSetKeys;
import com.hexagram2021.initial_house.server.register.IHStructures;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

@Mixin(ConfiguredStructureFeature.class)
public class ConfiguredStructureFeatureMixin<FC extends FeatureConfiguration, F extends StructureFeature<FC>> {
	@Shadow @Final
	public F feature;

	@Inject(method = "generate", at = @At(value = "HEAD"), cancellable = true)
	private void initial_house$preventGeneratingAroundInitialHouse(RegistryAccess registryAccess, ChunkGenerator chunkGenerator, BiomeSource biomeSource,
																   StructureManager structureManager, long seed, ChunkPos chunkPos, int references,
																   LevelHeightAccessor levelHeightAccessor, Predicate<Holder<Biome>> biomePredicate,
																   CallbackInfoReturnable<StructureStart> cir) {
		if(this.feature != IHStructures.INITIAL_HOUSE && chunkGenerator.hasFeatureChunkInRange(IHStructureSetKeys.INITIAL_HOUSE, seed, chunkPos.x, chunkPos.z, IHServerConfig.PREVENT_STRUCTURES_GENERATE_IN.get())) {
			cir.setReturnValue(StructureStart.INVALID_START);
		}
	}
}
