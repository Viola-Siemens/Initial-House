package com.hexagram2021.initial_house.common.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class IHCommonConfig {
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	private static final ForgeConfigSpec SPEC;

	static {
		BUILDER.push("initial_house-common-config");

		BUILDER.pop();
		SPEC = BUILDER.build();
	}

	public static ForgeConfigSpec getConfig() {
		return SPEC;
	}
}
