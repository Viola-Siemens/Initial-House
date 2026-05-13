package com.hexagram2021.initial_house.server.register;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.StructureSet;

import static com.hexagram2021.initial_house.InitialHouse.MODID;

/**
 * 自定义结构集资源键定义喵~
 *
 * @author liudongyu
 */
public final class IHStructureSetKeys {
	/**
	 * 初始房屋结构集资源键喵~
	 */
	public static final ResourceKey<StructureSet> INITIAL_HOUSE = createKey("initial_house");

	private IHStructureSetKeys() {
	}

	/**
	 * 创建结构集资源键喵~
	 *
	 * @param name 结构集名称喵~
	 * @return 结构集资源键喵~
	 */
	@SuppressWarnings("SameParameterValue")
	private static ResourceKey<StructureSet> createKey(String name) {
		return ResourceKey.create(Registries.STRUCTURE_SET, new ResourceLocation(MODID, name));
	}

	/**
	 * 触发类加载以确保静态常量完成初始化喵~
	 */
	public static void init() {
	}
}
