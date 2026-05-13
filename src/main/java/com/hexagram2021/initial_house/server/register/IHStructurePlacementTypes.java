package com.hexagram2021.initial_house.server.register;

import com.hexagram2021.initial_house.server.world.placements.SpawnPointOnlyPlacement;
import com.mojang.serialization.Codec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.hexagram2021.initial_house.InitialHouse.MODID;

/**
 * 自定义结构放置类型注册表喵~
 *
 * @author liudongyu
 */
public final class IHStructurePlacementTypes {
	private static final DeferredRegister<StructurePlacementType<?>> REGISTER = DeferredRegister.create(Registries.STRUCTURE_PLACEMENT, MODID);
	/**
	 * 仅在出生点区块附近生效的结构放置类型喵~
	 */
	public static final RegistryObject<StructurePlacementType<SpawnPointOnlyPlacement>> INIT_PLACEMENT = register("init_placement", SpawnPointOnlyPlacement.CODEC);

	private IHStructurePlacementTypes() {
	}

	/**
	 * 注册一个自定义结构放置类型喵~
	 *
	 * @param name 注册名喵~
	 * @param codec 编解码器喵~
	 * @param <SP> 放置类型喵~
	 * @return 注册对象喵~
	 */
	@SuppressWarnings("SameParameterValue")
	private static <SP extends StructurePlacement> RegistryObject<StructurePlacementType<SP>> register(String name, Codec<SP> codec) {
		return REGISTER.register(name, () -> () -> codec);
	}

	/**
	 * 向 Forge 事件总线注册所有结构放置类型喵~
	 *
	 * @param bus 模组事件总线喵~
	 */
	public static void init(IEventBus bus) {
		REGISTER.register(bus);
	}
}
