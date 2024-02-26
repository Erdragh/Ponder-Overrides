package dev.erdragh.ponder_overrides.mixin;

import dev.erdragh.ponder_overrides.kube.PonderOverrideEventJS;

import dev.erdragh.ponder_overrides.PonderOverrides;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.simibubi.create.foundation.ponder.PonderTag;
import com.simibubi.create.foundation.ponder.ui.PonderUI;

import net.minecraft.world.item.ItemStack;

@Mixin(PonderUI.class)
public class PonderUIMixin {

	@Inject(method = "of(Lnet/minecraft/world/item/ItemStack;)Lcom/simibubi/create/foundation/ponder/ui/PonderUI;", at = @At("HEAD"), cancellable = true)
	private static void nbt_ponders$of(ItemStack item, CallbackInfoReturnable<PonderUI> cir) {
		PonderOverrides.LOGGER.info("pondering {}", item);
		var event = new PonderOverrideEventJS(item);
		event.post();
		event.getOverride().map(PonderUI::of).ifPresent(cir::setReturnValue);
	}

	@Inject(method = "of(Lnet/minecraft/world/item/ItemStack;Lcom/simibubi/create/foundation/ponder/PonderTag;)Lcom/simibubi/create/foundation/ponder/ui/PonderUI;", at = @At("HEAD"), cancellable = true)
	private static void nbt_ponders$of(ItemStack item, PonderTag tag, CallbackInfoReturnable<PonderUI> cir) {
		PonderOverrides.LOGGER.info("pondering {} with tag {}", item, tag);
		var event = new PonderOverrideEventJS(item);
		event.post();
		event.getOverride().map(PonderUI::of).ifPresent(ui -> {
			((PonderUIAccessorMixin) ui).setReferredToByTag(tag);
			cir.setReturnValue(ui);
		});
	}
}
