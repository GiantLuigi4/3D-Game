package game;

import API.model.elements.Box;
import API.model.elements.Cube;
import API.utils.ImageLookup;
import API.event.EventBus;
import API.event.entity.tick.TestCollisionEvent;
import API.event.renderer.CollectModelsEvent;
import API.model.Model;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.stage.Stage;
import org.fxyz3d.utils.CameraTransformer;

import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class GameInstance extends Application {
	public static final int DispalyWidth=600;
	public static final int DispalyHeight=400;
	
	public static final PerspectiveCamera camera = new PerspectiveCamera(true);
	
	private static Robot r;
	
	public static boolean mouseAvailible=true;
	
	public static Stage stage;
	
	public static final String gameDir=System.getProperty("user.dir");
	
	public static final CameraTransformer cameraTransform = new CameraTransformer(CameraTransformer.RotateOrder.XYZ);
	
	public static final HashMap<BlockPos,Model> blocks=new HashMap<>();
	public static final ArrayList<Model> entities=new ArrayList<>();
	
	public static final Group group = new Group(cameraTransform);
	
	public static final Scene scene = new Scene(group, DispalyWidth, DispalyHeight, true, SceneAntialiasing.DISABLED);
	
	public static final Canvas canvas = new Canvas(DispalyWidth, DispalyHeight);
	
	public static final GraphicsContext gc = canvas.getGraphicsContext2D();
	
	public static final EventBus bus=new EventBus();
	
	public static World world=new World();
	
	public static final AnimationTimer gameLoop=new AnimationTimer() {
		public void handle(long currentNanoTime) {
			loop();
		}
	};
	
	public static final PlayerClient player=new PlayerClient();
	
	public static void loop() {
		group.getChildren().clear();
		ArrayList<Node> allNodes=new ArrayList<>();
		
		ArrayList<Model> models=new ArrayList<>();
		
		entities.forEach((m)->m.getAllElements().forEach((m2)->allNodes.addAll(Arrays.asList(m2.getNodes()))));
		
		CollectModelsEvent.Pre eventPre=new CollectModelsEvent.Pre(models);
		bus.post(eventPre);
		AtomicInteger intdex= new AtomicInteger();
		world.needsRefresh.values().iterator().forEachRemaining((b)->{
			if (b!=null) {
				Model mdl=b.getModel(world,b.pos);
				if (mdl!=null) {
					if (!blocks.containsKey(b.pos)) {
						blocks.put(b.pos,mdl);
					} else {
						blocks.replace(b.pos,mdl);
					}
				} else {
					blocks.remove(b.pos);
				}
			} else {
				blocks.remove(world.needsRefresh.keySet().toArray()[intdex.get()]);
			}
			intdex.getAndIncrement();
		});
		world.needsRefresh.clear();
		models.addAll(blocks.values());
		models.addAll(entities);
		Box bx=new Box(1,1,2);
		double xoff=Math.cos(Math.toRadians(-cameraTransform.ry.getAngle()+90));
		double yoff=Math.cos(Math.toRadians(cameraTransform.rx.getAngle()+90));
		double yoff2=Math.sin(Math.toRadians(cameraTransform.rx.getAngle()+90));
		double zoff=Math.sin(Math.toRadians(-cameraTransform.ry.getAngle()+90));
//		bx.setTranslateX(player.getX()+(xoff*1));
//		bx.setTranslateY(player.getY()+1f);
//		bx.setTranslateZ(player.getZ()+(zoff*1));
//		bx.setRotate(180);
//		bx.setRotationPivot(cameraTransform.rx.getAngle(),0,-cameraTransform.ry.getAngle());
//		models.add(new Model(bx));
		PhongMaterial mat=new PhongMaterial();
		mat.setDiffuseColor(Color.BLACK);
		mat.setSpecularColor(Color.BLACK);
		for (double i=0;i<=16;i+=0.1) {
			double xsearch=player.getX()+(xoff*(Math.abs(yoff2)))*i;
			double ysearch=(player.getY()+yoff*i)-player.getHeight()/2f+0.5f;
			double zsearch=player.getZ()+(zoff*(Math.abs(yoff2)))*i;
			boolean negativeX=!(xsearch>=0);
			boolean negativeZ=!(zsearch>=0);
			xsearch+=(negativeX?-0.5f:0.5);
			zsearch+=(negativeZ?-0.5f:0.5);
			if (world.blocks.containsKey(new BlockPos((int)xsearch,(int)-ysearch,(int)zsearch))) {
				Box cb=new Box(0.01f,1,0.01f);
				Box cb2=new Box(0.01f,1,0.01f);
				Box cb3=new Box(0.01f,0.01f,1);
				Box cb4=new Box(0.01f,0.01f,1);
				Box cb5=new Box(0.01f,0.01f,1);
				Box cb6=new Box(0.01f,0.01f,1);
				Box cb7=new Box(0.01f,1,0.01f);
				Box cb8=new Box(0.01f,1,0.01f);
				Box cb9=new Box(1,0.01f,0.01f);
				Box cb10=new Box(1,0.01f,0.01f);
				Box cb11=new Box(1,0.01f,0.01f);
				Box cb12=new Box(1,0.01f,0.01f);
				cb.setMaterial(mat);
				cb2.setMaterial(mat);
				cb3.setMaterial(mat);
				cb4.setMaterial(mat);
				cb5.setMaterial(mat);
				cb6.setMaterial(mat);
				cb7.setMaterial(mat);
				cb8.setMaterial(mat);
				cb9.setMaterial(mat);
				cb10.setMaterial(mat);
				cb11.setMaterial(mat);
				cb12.setMaterial(mat);
				cb.setTranslate((int)xsearch-0.5f,(int)ysearch,(int)zsearch+0.5f);
				cb2.setTranslate((int)xsearch-0.5f,(int)ysearch,(int)zsearch-0.5f);
				cb3.setTranslate((int)xsearch-0.5f,(int)ysearch+0.5f,(int)zsearch);
				cb4.setTranslate((int)xsearch-0.5f,(int)ysearch-0.5f,(int)zsearch);
				cb5.setTranslate((int)xsearch+0.5f,(int)ysearch-0.5f,(int)zsearch);
				cb6.setTranslate((int)xsearch+0.5f,(int)ysearch+0.5f,(int)zsearch);
				cb7.setTranslate((int)xsearch+0.5f,(int)ysearch,(int)zsearch+0.5f);
				cb8.setTranslate((int)xsearch+0.5f,(int)ysearch,(int)zsearch-0.5f);
				cb9.setTranslate((int)xsearch,(int)ysearch+0.5f,(int)zsearch-0.5f);
				cb10.setTranslate((int)xsearch,(int)ysearch-0.5f,(int)zsearch-0.5f);
				cb11.setTranslate((int)xsearch,(int)ysearch-0.5f,(int)zsearch+0.5f);
				cb12.setTranslate((int)xsearch,(int)ysearch+0.5f,(int)zsearch+0.5f);
				models.add(new Model(cb,cb2,cb3,cb4,cb5,cb6,cb7,cb8,cb9,cb10,cb11,cb12));
				break;
			}
		}
		CollectModelsEvent.Post eventPost=new CollectModelsEvent.Post(models);
		bus.post(eventPost);
		
		models.forEach((m)->m.getAllElements().forEach((m2)->allNodes.addAll(Arrays.asList(m2.getNodes()))));

		boolean firstInput=true;
		
		group.getChildren().addAll(allNodes);
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
					boolean negativeZ=!(player.getZ()>=0);
					BlockPos pos = new BlockPos(
							(int) (player.getX()+(negativeX?-0.5f:0.5)+x),
							-(int) (player.getY()+y),
							(int) (player.getZ()+(negativeZ?-0.5f:0.5f)+z)
					);
					collided.set(world.blocks.getOrDefault(pos,null)!=null);
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
							event.collidedY=pos.y+y*2;
						} else {
							if (y>=-player.getHeight()/2f&&!edgeX) {
								touching=true;
							}
							event.collidedY=pos.y+y*2;
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
			player.setY(-20);
		}
		
		scene.setFill(Color.SKYBLUE);
		
		cameraTransform.setRotateX(Math.max(-90,Math.min(90,cameraTransform.rx.getAngle())));
		cameraTransform.setPivot(camera.getTranslateX(),camera.getTranslateY(),camera.getTranslateZ());
	}
	
	@Override
	public void stop() throws Exception {
		super.stop();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		r=new Robot();
		
		camera.setNearClip(0.1);
		camera.setFarClip(10000.0);
		camera.setTranslateX(0);
		camera.setTranslateZ(0);
		camera.setTranslateY(-2);
		camera.setFieldOfView(120);
		camera.setVerticalFieldOfView(false);
		
		stage=primaryStage;
		
		cameraTransform.getChildren().add(camera);
		cameraTransform.ry.setAngle(0);
		cameraTransform.rx.setAngle(-15);
		cameraTransform.rz.setAngle(0);
		cameraTransform.setPivot(0,0,0);
		
		Random random=new Random();
		HashMap<Point2D,Integer> elevated=new HashMap<>();
		int width=16;
		int maxHeight=32;
		int hills=16;
		for (int i=0;i<hills;i++) {
//			elevated.put(
//					new Point2D(
//							random.nextInt(width*2)-(width),
//							random.nextInt(width*2)-(width)
//					),
//					32
//			);
		}
		
		ImageLookup.loadImages();
		for (int x=width/-2;x<=width/2;x++) {
			for (int z=width/-2;z<=width/2;z++) {
				boolean isEdge=Math.abs(x)+2==width/2||Math.abs(z)+2==width/2;
//				BlockPos pos=new BlockPos(x,getHeight(x,z,elevated)/16,z);
				BlockPos pos=new BlockPos(x,isEdge?3:0,z);
				world.setBlock(pos,new CubeBlock(pos,"game:fine_sand"));
				for (int y=pos.y-1;y>=0;y--) {
					BlockPos pos2=new BlockPos(x,y,z);
					if (y<=-2) {
						world.setBlock(pos2,new CubeBlock(pos2,"game:scorched_stone"));
					} else {
						world.setBlock(pos2,new CubeBlock(pos2,"game:debug"));
					}
				}
			}
		}
		
		gameLoop.start();
		
		scene.setFill(Color.LIGHTSKYBLUE);
		scene.setCamera(camera);
		scene.setOnMouseMoved(GameInstance::handleMouseMove);
		scene.setOnMouseDragOver(GameInstance::handleMouseDrag);
		scene.setOnKeyPressed(GameInstance::handleKeyPressed);
		scene.setOnKeyReleased(GameInstance::handleKeyReleased);
		
		primaryStage.setScene(scene);
		
		group.toFront();
		group.getChildren().addAll(canvas);
		canvas.toFront();
		
		primaryStage.setTitle("FXyz3D Sample");
		primaryStage.show();
	}
	
	public static int getHeight(HashMap<Point2D,Integer> map,int x,int z) {
		int i=map.getOrDefault(new Point2D(x,z),0);
		int search=8;
		for (int j=x-search;j<=x+search;j++) {
			for (int k=z-search;k<=z+search;k++) {
//				i+=Math.max(i,map.getOrDefault(new Point2D(j,k),0)*(Math.sqrt((x^2)+(z^2))));
				i+=getHeight(x,z,map);
				i/=2;
			}
		}
		return i/16;
	}
	
	private static int getHeight(int x,int z,HashMap<Point2D,Integer> map) {
		int i=map.getOrDefault(new Point2D(x,z),0);
		int search=16;
		for (int j=x-search;j<=x+search;j++) {
			for (int k=z-search;k<=z+search;k++) {
				i+=Math.max(i,map.getOrDefault(new Point2D(j,k),0)*(Math.sqrt((x^2)+(z^2))));
				i/=2;
			}
		}
		return i/1;
	}
	
	private static void handleMouseDrag(MouseDragEvent mouseDragEvent) {
	}
	
	private static void handleMouseMove(MouseEvent mouseEvent) {
		if (mouseAvailible) {
			centerX=(int)(stage.getX()+(stage.getWidth()/2f));
			centerY=(int)(stage.getY()+(stage.getHeight()/2f));
			mouseAvailible=false;
			double x=mouseEvent.getScreenX()-centerX;
			double y=mouseEvent.getScreenY()-centerY;
			cameraTransform.ry.setAngle(cameraTransform.ry.getAngle()+(x/25f));
			cameraTransform.rx.setAngle(cameraTransform.rx.getAngle()+(y/-25f));
			cameraTransform.rz.setAngle(0);
			r.mouseMove(centerX,centerY);
			r.waitForIdle();
			mouseAvailible=true;
		}
	}
	
	public static final HashMap<KeyCode, Boolean> keys=new HashMap<>();
	
	private static void handleKeyPressed(KeyEvent event) {
		setPressed(event.getCode(),true);
	}
	private static void handleKeyReleased(KeyEvent event) {
		setPressed(event.getCode(),false);
	}
	
	public static void setPressed(KeyCode code,boolean pressed) {
		if (keys.containsKey(code))
			keys.replace(code,pressed);
		else
			keys.put(code,pressed);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private static int centerX=0;
	private static int centerY=0;
	
	public static HashMap<API.utils.Color,Color> colors=new HashMap<>();
	
	public static Color getOrCreateColor(API.utils.Color color) {
		if (!colors.containsKey(color)) {
			colors.put(color,Color.color(color.getRed()/255f,color.getGreen()/255f,color.getBlue()/255f,color.getAlpha()/255f));
		}
		return colors.get(color);
	}
	
	//https://stackoverflow.com/questions/48618329/how-to-add-text-to-each-face-of-a-box-javafx
	public static Image generateImage(String path) {
		try {
			GridPane grid = new GridPane();
//		grid.setAlignment(Pos.CENTER);
			
			Scanner reader=new Scanner(new File(gameDir+"\\"+path));
			
			String line1=reader.nextLine();
			
			int width=Integer.parseInt(line1.substring(0,line1.indexOf(',')));
			int height=Integer.parseInt(line1.substring(line1.indexOf(',')+1));
			Canvas canvas1=new Canvas(width,height);
			GraphicsContext graphicsContext=canvas1.getGraphicsContext2D();
			
			int x=0;
			int y=0;
			try {
//				while ((x*width)+(y*width)<=(width*height)) {
				while (reader.hasNextLine()) {
					API.utils.Color color=new API.utils.Color(Integer.parseInt(reader.nextLine()),true);
					if (colors.containsKey(color)) {
						graphicsContext.getPixelWriter().setColor(x,y,colors.get(color));
					} else {
						colors.put(color,Color.color(color.getRed()/255f,color.getGreen()/255f,color.getBlue()/255f));
						graphicsContext.getPixelWriter().setColor(x,y,colors.get(color));
					}
					x++;
					if (x>=width) {
						x=0;
						y++;
					}
				}
			} catch (Throwable err) {
				err.printStackTrace();
			}
			
			grid.add(canvas1,0,0);
			
			reader.close();
			
			return grid.snapshot(null, null);
		} catch (Throwable err) {
			err.printStackTrace();
		}
		return null;
	}
}
