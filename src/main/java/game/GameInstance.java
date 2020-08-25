package game;

import API.model.elements.Box;
import API.utils.ImageLookup;
import API.event.EventBus;
import API.event.renderer.CollectModelsEvent;
import API.model.Model;
import API.utils.MathHelper;
import API.utils.StringyHashMap;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.DrawMode;
import javafx.stage.Stage;
import org.fxyz3d.geometry.Vector3D;
import org.fxyz3d.shapes.primitives.SegmentedSphereMesh;
import org.fxyz3d.utils.CameraTransformer;
import net.rgsw.ptg.noise.perlin.*;

import java.awt.Robot;
import java.io.File;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class GameInstance extends Application {
	public static final int DispalyWidth=600;
	public static final int DispalyHeight=400;
	
	public static final PerspectiveCamera camera = new PerspectiveCamera(true);
	
	private static Robot r;
	
	public static boolean mouseAvailible=true;
	
	public static Stage stage;
	
	public static String gameDir=System.getProperty("user.dir");
	
	public static final boolean devEnvro=!(gameDir.endsWith("\\bin"));
	
	public static final CameraTransformer cameraTransform = new CameraTransformer(CameraTransformer.RotateOrder.XYZ);
	
	public static final StringyHashMap<BlockPos,Model> blocks=new StringyHashMap<>();
	public static final ArrayList<Model> entities=new ArrayList<>();
	
	public static final Group group = new Group(cameraTransform);
	public static final Scene scene = new Scene(group, DispalyWidth, DispalyHeight, true, SceneAntialiasing.DISABLED);
	
	public static final CameraTransformer inverseCameraTransform = new CameraTransformer(CameraTransformer.RotateOrder.XYZ);
	private static final PerspectiveCamera inversePlayer=new PerspectiveCamera();
	
	public static final Group group2 = inverseCameraTransform;
	private static final SubScene UIScene=new SubScene(group2,scene.getWidth(),scene.getHeight(),false,SceneAntialiasing.DISABLED);
	
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
	
	private static Date lastMouseMove=new Date();
	
	public static void loop() {
		
		int numbernumber=0;
		
		if (stage.isFocused()) {
			if (new Date().getTime()-5>=lastMouseMove.getTime()) {
				long i=new Date().getTime();
				r.waitForIdle();
				centerX=(int)(stage.getX()+(stage.getWidth()/2f));
				centerY=(int)(stage.getY()+(stage.getHeight()/2f));
				r.mouseMove(centerX,centerY);
				lastMouseMove=new Date();
//				System.out.println(new Date().getTime()-i);
			}
		}
		
		TickMethods.tickClientPlayer(cameraTransform,player,keys,world);
		
		group.getChildren().clear();
		
		ArrayList<Node> allNodes=new ArrayList<>();
		
		ArrayList<Model> models=new ArrayList<>();
		
		entities.forEach((m)->m.getAllElements().forEach((m2)->allNodes.addAll(Arrays.asList(m2.getNodes()))));
		
		CollectModelsEvent.Pre eventPre=new CollectModelsEvent.Pre(models);
		bus.post(eventPre);
		AtomicInteger intdex= new AtomicInteger();
//		world.needsRefresh.values().iterator().forEachRemaining((b)->{
//			if (b!=null) {
//				Model mdl=b.getModel(world,b.pos);
//				if (mdl!=null) {
//					mdl.getAllElements().forEach((e)->{
//						for (Node nd:e.getNodes()) {
//							if (nd instanceof javafx.scene.shape.Box) {
//								Material mat=((javafx.scene.shape.Box) nd).getMaterial();
//								if (mat instanceof PhongMaterial) {
//									AtomicReference<Double> red= new AtomicReference<>((double) 0);
//									AtomicReference<Double> green= new AtomicReference<>((double) 0);
//									AtomicReference<Double> blue= new AtomicReference<>((double) 0);
//									world.lights.forEach((l)->{
//										double intense=(l.intensity*15);
//										double brightness=Math.max(0,(((Math.abs(Math.max(l.intensity,Math.abs(l.getDistanceTo(new Vector3D(b.pos.x, b.pos.y, b.pos.z))))))/intense)));
////											System.out.println(brightness);
//										red.set(Math.max(red.get(),MathHelper.Lerp(brightness, red.get(), l.r)));
//										green.set(Math.max(green.get(),MathHelper.Lerp(brightness, green.get(), l.g)));
//										blue.set(Math.max(blue.get(),MathHelper.Lerp(brightness, blue.get(), l.b)));
////											System.out.println(l.getDistanceTo(new Vector3D(b.pos.x, b.pos.y, b.pos.z)));
//									});
//									if (world.lights.size()==0) {
//										((PhongMaterial) mat).setDiffuseColor(Color.color(
//												red.get(),
//												green.get(),
//												blue.get()
//										));
//									} else {
//										((PhongMaterial) mat).setDiffuseColor(Color.color(
//												Math.max(0,Math.min(1,red.get())),
//												Math.max(0,Math.min(1,green.get())),
//												Math.max(0,Math.min(1,blue.get()))
//										));
//									}
//								}
//							}
//						}
//					});
//					if (!blocks.containsKey(b.pos)) {
//						blocks.put(b.pos,mdl);
//					} else {
//						blocks.replace(b.pos,mdl);
//					}
//				} else {
//					blocks.remove(b.pos);
//				}
//			} else {
//				blocks.remove(world.needsRefresh.keySet().toArray()[intdex.get()]);
//			}
//			intdex.getAndIncrement();
//		});
//		world.needsRefresh.forEach((cp)->{
//			blocks.put(cp.blockPos,world.chunks.get(cp).bakeModel());
//		});
//		world.needsRefresh.clear();
		for (int i=0;i<=32;i++) {
			if (world.needsRefresh.size()>=1) {
				try {
					blocks.addOrReplace(world.needsRefresh.get(0).blockPos,world.chunks.get(world.needsRefresh.get(0)).bakeModel());
				} catch (Throwable err) {}
				world.needsRefresh.remove(0);
			}
		}
		models.addAll(blocks.objects);
		models.addAll(entities);
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
		for (double i=0;i<=8;i+=0.1) {
			double xsearch=player.getX()+(xoff*(Math.abs(yoff2)))*i;
			double ysearch=player.getY()+yoff*i-0.5f;
			double zsearch=player.getZ()+(zoff*(Math.abs(yoff2)))*i;
			boolean negativeX=!(xsearch>=0);
			boolean negativeZ=!(zsearch>=0);
			xsearch+=(negativeX?-0.5f:0.5);
			zsearch+=(negativeZ?-0.5f:0.5);
			BlockPos posCheck=new BlockPos((int)xsearch,(int)-ysearch,(int)zsearch);
			if (world.getChunk(posCheck).getBlock(posCheck)!=null) {
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
		
		int skyboxSize=256;
		
		PhongMaterial skymaterial=new PhongMaterial();
		skymaterial.setDiffuseMap(ImageLookup.images.get("game:sky_day"));
		SegmentedSphereMesh sphereMesh=new SegmentedSphereMesh(64,0,0,-(skyboxSize),new org.fxyz3d.geometry.Point3D(0,0,0));
		sphereMesh.setDrawMode(DrawMode.FILL);
		sphereMesh.setBlendMode(BlendMode.OVERLAY);
		sphereMesh.setMaterial(skymaterial);
		sphereMesh.setTranslateX(player.getX()*numbernumber);
		sphereMesh.setTranslateY(player.getY()*numbernumber);
		sphereMesh.setTranslateZ(player.getZ()*numbernumber);
		sphereMesh.setRotate(180);
		sphereMesh.setEffect(new ImageInput(mat.getDiffuseMap()));
		sphereMesh.setOpacity(1);
		allNodes.add(sphereMesh);
		
		CollectModelsEvent.Post eventPost=new CollectModelsEvent.Post(models);
		bus.post(eventPost);
		
		models.forEach((m)->m.getAllElements().forEach((m2)->allNodes.addAll(Arrays.asList(m2.getNodes()))));
		
		group2.setTranslateX(player.getX());
		group2.setTranslateY(player.getY());
		group2.setTranslateZ(player.getZ());
		group2.setRotationAxis(cameraTransform.getRotationAxis());
		group2.setRotate(cameraTransform.getRotate());
		
		inverseCameraTransform.setRotateX(cameraTransform.rx.getAngle());
		inverseCameraTransform.setRotateY(cameraTransform.ry.getAngle());
		inverseCameraTransform.setRotateZ(cameraTransform.rz.getAngle());
		
		group2.getChildren().clear();
		Glow g=new Glow(1);
		for (int i=0;i<=9;i++) {
			ImageView imageView = new ImageView(ImageLookup.images.get("game:hotbar"));
			imageView.setSmooth(false);
			imageView.setEffect(g);
			imageView.setScaleX(0.1f);
			imageView.setScaleY(0.1f);
			if (hotbarSlot==i) {
				imageView.setTranslateZ(12);
				imageView.setScaleX(0.11f);
				imageView.setScaleY(0.11f);
			} else {
				imageView.setTranslateZ(12);
			}
			imageView.setTranslateX(-14+i*1.55);
			imageView.setTranslateY(2.65);
			Item item=null;
			if (blockslist.size()>i) {
				item=blockslist.get(i);
			}
			ArrayList<Node> allItemNodes=new ArrayList<>();
			if (item!=null)
			item.constructBlock(new BlockPos(0,1280,0)).getModel(null,new BlockPos(0,0,0)).getAllElements().forEach((m)->allItemNodes.addAll(Arrays.asList(m.getNodes())));
			allItemNodes.forEach((n)->{
				n.setDepthTest(DepthTest.DISABLE);
				if (n instanceof PointLight) {
					n.setDisable(true);
					((PointLight) n).setColor(Color.color(0,0,0,0));
					n.setVisible(false);
				}
				if (n instanceof javafx.scene.shape.Box) {
//					PhongMaterial material=(PhongMaterial)((javafx.scene.shape.Box)n).getMaterial();
//					material.setSpecularMap(ImageLookup.images.get("game:inventory_specular"));
//					material.setBumpMap(ImageLookup.images.get("game:inventory_specular"));
//					((javafx.scene.shape.Box)n).setMaterial(material);
				}
			});
			Group group1=new Group();
			Group group3=new Group();
			Group group4=new Group();
			imageView.setDepthTest(DepthTest.DISABLE);
			group2.getChildren().addAll(imageView);
			group1.getChildren().addAll(allItemNodes);
			group1.setTranslateZ(1);
			group1.setScaleX(0.05f);
			group1.setScaleY(0.05f);
			group1.setScaleZ(0.05f);
			group3.setRotate(22.5);
			group3.setRotationAxis(new Point3D(1,0,0));
			group3.getChildren().addAll(group1);
			group1.setRotate(45);
			group1.setRotationAxis(new Point3D(0,-1,0));
			group3.setTranslateX(-0.5+i/7.725f);
			group3.setTranslateY(0.89);
			group4.getChildren().add(group3);
			group4.setScaleZ(0.001f);
			group4.setTranslateY(1280);
			group2.getChildren().addAll(group4);
		}
		
		int timei=new Date().getSeconds()+(new Date().getMinutes()*60)+((new Date().getHours()*60)*60);
		float time=timei%360;
		
		xoff=Math.cos(Math.toRadians(((timei%180)<time)?0:180));
		double doub = Math.toRadians(2+(Math.cos(timei/360f)));
		double xoff2=Math.cos(doub);
		yoff=Math.cos(Math.toRadians(timei));
		double yoff3=Math.cos(Math.toRadians(time*2));
		yoff2=Math.sin(Math.toRadians(timei));
		double yoff4=Math.sin(Math.toRadians(time*2));
		zoff=Math.sin(Math.toRadians(0));
		double zoff2=Math.sin(doub);
		
//		int sundist=230;
//		for (int i=sundist;i<=sundist;i++) {
//			double xsearch=(player.getX()*numbernumber)+(xoff*(Math.abs(yoff2)))*i;
//			double ysearch=((player.getY()*numbernumber)+yoff*i);
//			double zsearch=(player.getZ())*numbernumber+(zoff*(Math.abs(yoff2)))*i;
//			int offset=0;
//			double xsearch2=(player.getX()*numbernumber)+(xoff*(Math.abs(yoff2)))*(i+offset);
//			double ysearch2=((player.getY()*numbernumber)+yoff*(i+offset));
//			double zsearch2=(player.getZ()*numbernumber)+(zoff*(Math.abs(yoff2)))*(i+offset);
//
//			PointLight pl=new PointLight();
//			PointLight pl3=new PointLight();
//			PointLight pl2=new PointLight();
//			pl.setColor(Color.color(1,1,0,20/255f));
//			pl2.setColor(Color.color(0.5,0.5,0.5));
//			pl3.setColor(Color.color(0.1,0.1,0.1,1));
//			pl.setTranslateX(xsearch);
//			pl3.setTranslateX(player.getX());
//			pl3.setTranslateY(player.getY()-2);
//			pl3.setTranslateZ(player.getZ());
//			pl2.setTranslateX(xsearch2);
//			pl.setTranslateY(ysearch);
//			pl2.setTranslateY(ysearch2);
//			pl.setTranslateZ(zsearch);
//			pl2.setTranslateZ(zsearch2);
//
////			pl.setEffect(new Glow(1));
//
//			group.getChildren().addAll(
////					pl
////					,
//					pl2
//					,
//					pl3
//			);
//		}
		
		group2.setDepthTest(DepthTest.DISABLE);
		group.getChildren().add(group2);
		
		try {
			group.getChildren().addAll(allNodes);
		} catch (Throwable err) {}
		group2.toFront();
	}
	
	@Override
	public void stop() throws Exception {
		super.stop();
	}
	
	public static int hotbarSlot=0;
	
	public static final ArrayList<Item> blockslist=new ArrayList<>();
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		r=new Robot();
		
		if (!devEnvro) {
			gameDir=gameDir.substring(0,(gameDir.length()-("\\bin".length())));
			System.out.println(gameDir);
		}
		
		blockslist.addAll(Arrays.asList(
				new Item(new CubeBlock(new BlockPos(0,0,0),"game:sand")),
				new Item(new CubeBlock(new BlockPos(0,0,0),"game:stone")),
				new Item(new CubeBlock(new BlockPos(0,0,0),"game:scorched_stone")),
				new Item(new CubeBlock(new BlockPos(0,0,0),"game:sandstone")),
				new Item(new CubeBlock(new BlockPos(0,0,0),"game:fine_sand")),
				new Item(new CubeBlock(new BlockPos(0,0,0),"game:stone2")),
				new Item(new GlowCubeBlock(new BlockPos(0,0,0),"game:glow_block"))
		));
		
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
		
		int width=128;
		
		Perlin2D perlin=new Perlin2D(372483274,255,255);
		Perlin2D perlin2=new Perlin2D(874234,255,255);
		
		ImageLookup.loadImages();
		for (int x=width/-2;x<=width/2;x++) {
			for (int z=width/-2;z<=width/2;z++) {
				BlockPos pos=new BlockPos(x,((int)(perlin.generate(x,z)*perlin2.generate(z,x)*420)),z);
				world.setBlock(pos,new CubeBlock(pos,"game:fine_sand"));
				for (int y=pos.y-1;y>=12800;y--) {
					BlockPos pos2=new BlockPos(x,y,z);
					if (y<=-2) {
						world.setBlock(pos2,new CubeBlock(pos2,"game:scorched_stone"));
					} else {
						world.setBlock(pos2,new CubeBlock(pos2,"game:stone"));
					}
				}
			}
		}
		
		gameLoop.start();
		
		UIScene.setCamera(inversePlayer);
		
		scene.setFill(Color.LIGHTSKYBLUE);
		scene.setCamera(camera);
		scene.setOnMouseMoved(GameInstance::handleMouseMove);
		scene.setOnMouseDragged(GameInstance::handleMouseMove);
		scene.setOnKeyPressed(GameInstance::handleKeyPressed);
		scene.setOnKeyReleased(GameInstance::handleKeyReleased);
		scene.setOnMousePressed(GameInstance::handleMousePress);
		scene.setOnScroll(GameInstance::handleScroll);
		primaryStage.setScene(scene);
		
		group.toFront();
//		group.getChildren().addAll(canvas);
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
		return i;
	}
	
	private static void handleScroll(ScrollEvent scrollEvent) {
		double dh=scrollEvent.getDeltaY()/40+scrollEvent.getDeltaX()/40;
		hotbarSlot-=dh;
		if (hotbarSlot<0) {
			hotbarSlot+=10;
		} else if (hotbarSlot>=10) {
			hotbarSlot-=10;
		}
	}
	
	private static void handleMousePress(MouseEvent mouseEvent) {
		if (mouseEvent.getPickResult().getIntersectedNode()!=null) {
			if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
				int xPos=(int)mouseEvent.getPickResult().getIntersectedNode().getTranslateX();
				int yPos=(int)mouseEvent.getPickResult().getIntersectedNode().getTranslateY();
				int zPos=(int)mouseEvent.getPickResult().getIntersectedNode().getTranslateZ();
				
//				System.out.println(mouseEvent.getPickResult().getIntersectedTexCoord());
//				System.out.println(mouseEvent.getPickResult().getIntersectedPoint());
				
				Item item=null;
				if (blockslist.size()>hotbarSlot) {
					item=blockslist.get(hotbarSlot);
				}
				
				double xoff=Math.cos(Math.toRadians(-cameraTransform.ry.getAngle()+90));
				double yoff=Math.cos(Math.toRadians(cameraTransform.rx.getAngle()+90));
				double yoff2=Math.sin(Math.toRadians(cameraTransform.rx.getAngle()+90));
				double zoff=Math.sin(Math.toRadians(-cameraTransform.ry.getAngle()+90));
				for (double i=0;i<=8;i+=0.1) {
					double xsearch=player.getX()+(xoff*(Math.abs(yoff2)))*i;
					double ysearch=(player.getY()+yoff*i)-player.getHeight()/2f+0.5f;
					double zsearch=player.getZ()+(zoff*(Math.abs(yoff2)))*i;
					boolean negativeX=!(xsearch>=0);
					boolean negativeZ=!(zsearch>=0);
					xsearch+=(negativeX?-0.5f:0.5);
					zsearch+=(negativeZ?-0.5f:0.5);
					if (world.getBlock(new BlockPos((int)xsearch,(int)-ysearch,(int)zsearch))!=null) {
						for (double b=0;b<=2;b+=0.01) {
							double xsearch2=player.getX()+(xoff*(Math.abs(yoff2)))*(i-b);
							double ysearch2=(player.getY()+yoff*(i-b))-player.getHeight()/2f+1f;
							double zsearch2=player.getZ()+(zoff*(Math.abs(yoff2)))*(i-b);
							int xpos=(int)Math.round(((xsearch2+xsearch+xPos+xsearch2)/4f));
							int ypos=(int)-Math.round(((ysearch2+ysearch+yPos+ysearch2)/4f));
							int zpos=(int)Math.round(((zsearch2+zsearch+zPos+zsearch2)/4f));
							if (world.getBlock(new BlockPos(xpos,ypos,zpos))==null) {
								BlockPos pos1=new BlockPos(xpos,ypos,zpos);
								if (item!=null)
								GameInstance.world.setBlock(pos1,item.constructBlock(pos1));
								break;
							}
						}
						break;
					}
				}
				
//				BlockPos pos1=new BlockPos((int)(xPos),-(int)(yPos),(int)(zPos));
//				GameInstance.world.setBlock(pos1,new CubeBlock(pos1,"game:sandstone"));
			} else if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
				int xPos=(int)mouseEvent.getPickResult().getIntersectedNode().getTranslateX();
				int yPos=(int)mouseEvent.getPickResult().getIntersectedNode().getTranslateY();
				int zPos=(int)mouseEvent.getPickResult().getIntersectedNode().getTranslateZ();
				BlockPos pos1=new BlockPos((int)(xPos),-(int)(yPos),(int)(zPos));
				GameInstance.world.removeBlock(pos1);
			}
		}
	}
	
	private static void handleMouseMove(MouseEvent mouseEvent) {
		if (mouseAvailible) {
			mouseAvailible=false;
			double x=mouseEvent.getScreenX()-centerX;
			double y=mouseEvent.getScreenY()-centerY;
			cameraTransform.ry.setAngle(cameraTransform.ry.getAngle()+(x/25f));
			cameraTransform.rx.setAngle(cameraTransform.rx.getAngle()+(y/-25f));
			cameraTransform.rz.setAngle(0);
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
			
			File f=new File(gameDir+"\\"+path);
			
			if (!f.getParentFile().exists()) {
				f.getParentFile().mkdirs();
				f.createNewFile();
				return null;
			}
			
			if (f.exists()) {
				Scanner reader=new Scanner(f);
				
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
						String s=reader.nextLine();
						API.utils.Color color=new API.utils.Color(Integer.parseInt(s),true);
						if (s.equals("0")) {
							color=new API.utils.Color(color.getRed(),color.getBlue(),color.getGreen(),255);
						}
						if (colors.containsKey(color)) {
							graphicsContext.getPixelWriter().setColor(y,x,colors.get(color));
						} else {
							colors.put(color,Color.color(color.getRed()/255f,color.getGreen()/255f,color.getBlue()/255f,color.getAlpha()/255f));
							graphicsContext.getPixelWriter().setColor(y,x,colors.get(color));
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
			} else {
				f.createNewFile();
			}
			return null;
		} catch (Throwable err) {
			err.printStackTrace();
		}
		return null;
	}
}
