package dev.erdragh.ponder_overrides;

import com.simibubi.create.Create;

import io.github.fabricators_of_create.porting_lib.util.EnvExecutor;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.ResourceLocation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PonderOverrides implements ClientModInitializer {
	public static final String ID = "ponder_overrides";
	public static final String NAME = "Ponder-Overrides";
	public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

	@Override
	public void onInitializeClient() {
		LOGGER.info("Create addon mod [{}] is loading alongside Create [{}]!", NAME, Create.VERSION);
	}
}
