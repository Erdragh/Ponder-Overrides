package com.github.erdragh.nbt_ponders.mixin;

import com.simibubi.create.foundation.ponder.PonderTag;
import com.simibubi.create.foundation.ponder.ui.PonderUI;

import net.minecraft.resources.ResourceLocation;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import net.minecraft.world.item.ItemStack;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PonderUI.class)
public class PonderUIMixin {

	@Inject(method = "of(Lnet/minecraft/world/item/ItemStack;)Lcom/simibubi/create/foundation/ponder/ui/PonderUI;", at = @At("HEAD"), cancellable = true)
	private static void nbt_ponders$of(ItemStack item, CallbackInfoReturnable<PonderUI> cir) {
		if (item.getOrCreateTag().contains("nbt_ponders_ponder_id")) {
			var ponderId = new ResourceLocation(item.getOrCreateTag().getString("nbt_ponders_ponder_id"));
			cir.setReturnValue(PonderUI.of(ponderId));
			cir.cancel();
		}
	}

	@Inject(method = "of(Lnet/minecraft/world/item/ItemStack;Lcom/simibubi/create/foundation/ponder/PonderTag;)Lcom/simibubi/create/foundation/ponder/ui/PonderUI;", at = @At("HEAD"), cancellable = true)
	private static void nbt_ponders$of(ItemStack item, PonderTag tag, CallbackInfoReturnable<PonderUI> cir) {
		if (item.getOrCreateTag().contains("nbt_ponders_ponder_id")) {
			var ponderId = new ResourceLocation(item.getOrCreateTag().getString("nbt_ponders_ponder_id"));
			var ui = PonderUI.of(ponderId);
			((PonderUIAccessorMixin) ui).setReferredToByTag(tag);
			cir.setReturnValue(ui);
			cir.cancel();
		}
	}
}
