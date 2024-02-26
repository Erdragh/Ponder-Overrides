package dev.erdragh.ponder_overrides.kube;

import dev.latvian.mods.kubejs.event.EventJS;
import dev.latvian.mods.kubejs.item.ItemStackJS;
import dev.latvian.mods.kubejs.script.ScriptType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class PonderOverrideEventJS extends EventJS {
	private static final String EVENT = "ponder.override";
	private final ItemStackJS item;
	private final String tag;
	private Optional<ResourceLocation> override = Optional.empty();

    public PonderOverrideEventJS(ItemStack item) {
		this(item, null);
    }

	public PonderOverrideEventJS(ItemStack item, String tag) {
		this.item = new ItemStackJS(item);
		this.tag = tag;
	}

    public void override(String override) {
		this.override = Optional.ofNullable((override != null) ? new ResourceLocation(override) : null);
	}
	public ItemStackJS getItem() {
		return item;
	}
	public String getTag() {
		return this.tag;
	}
	public Optional<ResourceLocation> getOverride() {
		return override;
	}

	public boolean post() {
		return this.post(ScriptType.CLIENT, EVENT);
	}
}
