package game;

import API.event.entity.tick.TestCollisionEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import org.fxyz3d.utils.CameraTransformer;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import static game.GameInstance.bus;
import static game.GameInstance.scene;

public class TickMethods {
	public static void tickClientPlayer(CameraTransformer cameraTransform, PlayerClient player, HashMap<KeyCode,Boolean> keys, World world) {
		boolean firstInput=true;
		if (keys.getOrDefault(KeyCode.W,false)) {
			double xRot=cameraTransform.ry.getAngle();
			float speed=0.1f;
			player.setMotionX((Math.cos(Math.toRadians(xRot+90))*-speed));
			player.setMotionZ((Math.sin(Math.toRadians(xRot+90))*speed));
			firstInput=false;
		}
		if (keys.getOrDefault(KeyCode.S,false)) {
			double xRot=cameraTransform.ry.getAngle();
			float speed=0.1f;
			double xMot=(Math.cos(Math.toRadians(xRot+90))*speed);
			double zMot=(Math.sin(Math.toRadians(xRot+90))*-speed);
			if (firstInput) {
				player.setMotionX(xMot);
				player.setMotionZ(zMot);
			} else {
				player.push(xMot,0,zMot);
			}
			firstInput=false;
		}
		if (keys.getOrDefault(KeyCode.D,false)) {
			double xRot=cameraTransform.ry.getAngle();
			float speed=0.1f;
			double xMot=((Math.cos(Math.toRadians(xRot))*speed));
			double zMot=((Math.sin(Math.toRadians(xRot))*-speed));
			if (firstInput) {
				player.setMotionX(xMot);
				player.setMotionZ(zMot);
			} else {
				player.push(xMot,0,zMot);
			}
			firstInput=false;
		}
		if (keys.getOrDefault(KeyCode.A,false)) {
			double xRot=cameraTransform.ry.getAngle();
			float speed=0.1f;
			double xMot=((Math.cos(Math.toRadians(xRot))*-speed));
			double zMot=((Math.sin(Math.toRadians(xRot))*speed));
			if (firstInput) {
				player.setMotionX(xMot);
				player.setMotionZ(zMot);
			} else {
				player.push(xMot,0,zMot);
			}
			firstInput=false;
		}
		if (keys.getOrDefault(KeyCode.UP,false)) {
			double angle=cameraTransform.rx.getAngle();
			float speed=1;
			cameraTransform.setRotateX(angle+speed);
		}
		if (keys.getOrDefault(KeyCode.DOWN,false)) {
			double angle=cameraTransform.rx.getAngle();
			float speed=-1;
			cameraTransform.setRotateX(angle+speed);
		}
		if (keys.getOrDefault(KeyCode.RIGHT,false)) {
			double angle=cameraTransform.ry.getAngle();
			float speed=1;
			cameraTransform.setRotateY(angle+speed);
		}
		if (keys.getOrDefault(KeyCode.LEFT,false)) {
			double angle=cameraTransform.ry.getAngle();
			float speed=-1;
			cameraTransform.setRotateY(angle+speed);
		}
		player.travel();
		boolean touching=false;
		for (double x=-player.getWidth()/1;x<=player.getWidth()/1;x+=0.5f) {
			for (double y=-player.getHeight()/2f;y<=player.getHeight()/2f;y+=1) {
				for (double z=-player.getWidth()/1;z<=player.getWidth()/1;z+=0.5f) {
					AtomicBoolean collided = new AtomicBoolean(false);
					boolean negativeX=!(player.getX()>=0);
					boolean negativeY=!(player.getY()>=0);
					boolean negativeZ=!(player.getZ()>=0);
					BlockPos pos = new BlockPos(
							(int) (player.getX()+(negativeX?-0.5f:0.5)+x),
							-(int) (player.getY()+(negativeY?-0:1)+y),
							(int) (player.getZ()+(negativeZ?-0.5f:0.5f)+z)
					);
					collided.set(world.getBlock(pos)!=null);
					TestCollisionEvent event = new TestCollisionEvent(player, collided.get(),x,y,z);
					boolean xCollision = Math.abs(x) > Math.abs(z);
					boolean edgeX=
							Math.abs(x)>=player.getWidth()/2f-0.1f||
									Math.abs(z)>=player.getWidth()/2f-0.1f;
					if (collided.get()) {
						if (xCollision)
							if (x>=0) {
								event.collidedX=pos.x+x+(negativeX?-1.5:-1.5);
							} else {
								event.collidedX=pos.x+x+(negativeX?1.5f:1.5);
							}
						if (y<=0) {
							event.collidedY=pos.y-(negativeY?0.51:1)*2;
						} else {
							if (y>=-player.getHeight()/2f&&!edgeX) {
								touching=true;
							}
							event.collidedY=pos.y+(negativeY?1:1)*2;
						}
						if (!xCollision)
							if (z>=0) {
								event.collidedZ=pos.z+z+(negativeZ?-1.5:-1.5);
							} else {
								event.collidedZ=pos.z+z+(negativeZ?1.5f:1.5f);
							}
					}
					bus.post(event);
					if (!event.moddedDetection&&event.collided) {
						if (Math.abs(y)<player.getHeight()/2f&& (
								edgeX
						)) {
							if (Math.abs(x)!=Math.abs(z)) {
								if (xCollision)
									event.entity.setX(event.collidedX);
								if (!xCollision)
									event.entity.setZ(event.collidedZ);
							}
						} else if (!edgeX) {
							event.entity.setY(-event.collidedY);
							player.setMotionY(0);
						}
					}
				}
			}
		}
		
		player.onGround=touching;
		
		if (keys.getOrDefault(KeyCode.SPACE,false)&&(player.onGround)) {
			player.jump();
		}
		
		if (keys.getOrDefault(KeyCode.R,false)) {
//			player.setMotionY(0);
			player.setY(-2);
		}
		
		if (keys.getOrDefault(KeyCode.F,false)) {
			player.onGround=true;
			player.setMotionY(0);
		}
		
		scene.setFill(Color.SKYBLUE);
		
		cameraTransform.setRotateX(Math.max(-90,Math.min(90,cameraTransform.rx.getAngle())));
		cameraTransform.setPivot(player.getX(),player.getY(),player.getZ());
	}
}
