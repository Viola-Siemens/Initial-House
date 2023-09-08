package com.hexagram2021.initial_house.server.config;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

import static com.hexagram2021.initial_house.InitialHouse.MODID;

public class IHServerConfig {
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	private static final ForgeConfigSpec SPEC;

	public static final ForgeConfigSpec.ConfigValue<List<? extends String>> INITIAL_HOUSE_STRUCTURES;
	public static final ForgeConfigSpec.IntValue INITIAL_HOUSE_PIVOT_X;
	public static final ForgeConfigSpec.IntValue INITIAL_HOUSE_PIVOT_Y;
	public static final ForgeConfigSpec.IntValue INITIAL_HOUSE_PIVOT_Z;

	public static final ForgeConfigSpec.BooleanValue DISABLE_SPAWN_POINT_RANDOM_SHIFTING;
	public static final ForgeConfigSpec.IntValue SPAWN_POINT_SHIFT_X;
	public static final ForgeConfigSpec.IntValue SPAWN_POINT_SHIFT_Y;
	public static final ForgeConfigSpec.IntValue SPAWN_POINT_SHIFT_Z;

	static {
		BUILDER.push("initial_house-server-config");
			INITIAL_HOUSE_STRUCTURES = BUILDER.comment("The resource id for this mod to generate at the spawn point of the world.")
					.defineList("INITIAL_HOUSE_STRUCTURES", List.of(new ResourceLocation(MODID, "initial_house").toString()), o -> o instanceof String s && ResourceLocation.isValidResourceLocation(s));
			INITIAL_HOUSE_PIVOT_X = BUILDER.comment("X-pivot of the initial house. Recommend: x-size / 2.")
					.defineInRange("INITIAL_HOUSE_PIVOT_X", 4, -255, 255);
			INITIAL_HOUSE_PIVOT_Y = BUILDER.comment("Y-pivot of the initial house. Recommend: 0 or 1 or 2, depends on the thickness of the floor.")
					.defineInRange("INITIAL_HOUSE_PIVOT_Y", 1, -255, 255);
			INITIAL_HOUSE_PIVOT_Z = BUILDER.comment("Z-pivot of the initial house. Recommend: z-size / 2")
					.defineInRange("INITIAL_HOUSE_PIVOT_Z", 4, -255, 255);
			DISABLE_SPAWN_POINT_RANDOM_SHIFTING = BUILDER.comment("Disable random shifting when player respawn. Setting this value to true makes players spawn at an exact position instead of a random range.")
					.define("DISABLE_SPAWN_POINT_RANDOM_SHIFTING", false);
			SPAWN_POINT_SHIFT_X = BUILDER.comment("X-shift of the spawn point. Enabled only if DISABLE_SPAWN_POINT_RANDOM_SHIFTING = true.")
					.defineInRange("SPAWN_POINT_SHIFT_X", 0, -255, 255);
			SPAWN_POINT_SHIFT_Y = BUILDER.comment("Y-shift of the spawn point. Enabled only if DISABLE_SPAWN_POINT_RANDOM_SHIFTING = true.")
					.defineInRange("SPAWN_POINT_SHIFT_Y", 0, -255, 255);
			SPAWN_POINT_SHIFT_Z = BUILDER.comment("Z-shift of the spawn point. Enabled only if DISABLE_SPAWN_POINT_RANDOM_SHIFTING = true.")
					.defineInRange("SPAWN_POINT_SHIFT_Z", 0, -255, 255);
		BUILDER.pop();
		SPEC = BUILDER.build();
	}

	public static ForgeConfigSpec getConfig() {
		return SPEC;
	}
}
