package com.contour.client.mixin;

import net.minecraft.world.scores.*;
import com.contour.client.ContourConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class ContourClientMixin {

	@Inject(method = "isCurrentlyGlowing", at = @At("RETURN"), cancellable = true)
	private void forceGlowing(CallbackInfoReturnable<Boolean> cir) {
		Entity self = (Entity) (Object) this;
		if (!(self instanceof Player) && !(self instanceof ArmorStand)) return;
		if (!ContourConfig.enabled) return;

		var mc = Minecraft.getInstance();
		var camera = mc.player;
		if (camera == null || camera == self) return;

		var level = mc.level;
		if (level == null) return;

		Vec3 from = camera.getEyePosition();
		double x = self.getX();
		double y = self.getY();
		double z = self.getZ();
		double h = self.getBbHeight();
		double w = self.getBbWidth() * 0.4;

		Vec3[] checkPoints = new Vec3[] {
				new Vec3(x, y + h * 0.1, z),
				new Vec3(x, y + h * 0.5, z),
				new Vec3(x, y + h * 0.9, z),
				new Vec3(x + w, y + h * 0.5, z),
				new Vec3(x - w, y + h * 0.5, z),
				new Vec3(x, y + h * 0.5, z + w),
				new Vec3(x, y + h * 0.5, z - w),
		};

		for (Vec3 to : checkPoints) {
			HitResult hit = level.clip(new ClipContext(
					from, to,
					ClipContext.Block.COLLIDER,
					ClipContext.Fluid.NONE,
					camera
			));
			if (hit.getType() == HitResult.Type.MISS) {
				cir.setReturnValue(true);
				return;
			}
		}
	}@Inject(method = "getTeamColor", at = @At("RETURN"), cancellable = true)
	private void contourTeamColor(CallbackInfoReturnable<Integer> cir) {
		Entity self = (Entity) (Object) this;

		if (!(self instanceof Player) && !(self instanceof ArmorStand)) return;
		if (!ContourConfig.enabled) return;

		int color = (ContourConfig.red << 16)
				| (ContourConfig.green << 8)
				| ContourConfig.blue;

		cir.setReturnValue(color);
	}
}