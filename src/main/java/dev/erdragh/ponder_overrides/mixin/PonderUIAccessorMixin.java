package dev.erdragh.ponder_overrides.mixin;

import com.simibubi.create.foundation.ponder.PonderTag;
import com.simibubi.create.foundation.ponder.ui.PonderUI;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PonderUI.class)
public interface PonderUIAccessorMixin {
	@Accessor(value = "referredToByTag", remap = false)
	void setReferredToByTag(PonderTag tag);
}
