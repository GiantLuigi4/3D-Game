package API.ClassLoader;

import game.GameInstance;
import org.apache.bcel.util.ClassPath;
import org.objectweb.asm.ClassReader;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;

//MMD Discord:https://discord.mcmoddev.com/
//Message:https://discordapp.com/channels/176780432371744769/421377435041267712/723593029583241336
//Most stuff I know about class loaders is from fabric.
public class Loader extends ClassLoader {
	public Class<?> define(String name, byte[] bytes) {
		classes.putIfAbsent(name,this.defineClass(name, bytes, 0, bytes.length));
		return classes.get(name);
	}

	HashMap<String,Class> classes=new HashMap<>();

	String path=GameInstance.gameDir.toString()+"\\mods";

	public void setPath(String path) {
		this.path=path;
		File file=new File(path);
		try {
			if (!file.exists()) file.createNewFile();
		} catch (Throwable err) {}
	}

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		Class c=null;
		try {
			c=this.findClass(name);
		} catch (Throwable err) {}
		if (!name.contains("java.lang")) {
			try {
				InputStream stream1=null;
				if (c==null) {
					stream1=
//					null;
							new ClassPath(path).getClassFile("test2").getInputStream();
				}
				try {
					if (stream1!=null) {
						byte[]bytes1=new byte[8192];
						byte[]newBytes1=new byte[stream1.read(bytes1)];
						StringBuilder bytecode=new StringBuilder("ByteCode: ");for(int i=0;i<newBytes1.length-0;i++){newBytes1[i]=bytes1[i];bytecode.append(newBytes1[i]).append(" ");};
						stream1.close();
						ClassReader reader=new ClassReader(newBytes1);
						System.out.println(reader.getSuperName());
						c=this.define(name,newBytes1);
					}
				} catch (Throwable err) {
					err.printStackTrace();
					try {
						stream1.close();
					} catch (Throwable ignored) {}
				}
			} catch (Throwable err) {
				err.printStackTrace();
			}
		}
		if (c!=null) return c;
		return this.findClass(name);
	}

	public Class trimAndDefine(byte[] bytes) {
		boolean tooLong=true;
		int offset=0;
		while (tooLong) {
			try {
				if (offset!=0)
					bytes[bytes.length-offset]=new Byte("");
				offset++;
			} catch (ClassFormatError err) {
				if (err.toString().contains(""));
			}
		}
		return null;
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		Class c=null;
		try {
			c=this.findSystemClass(name);
		} catch (Throwable ignored) {}
		if (c==null)c=this.findLoadedClass(name);
		if (c==null)c=classes.getOrDefault(name,null);
		try {
			if (c==null)c=super.findClass(name);
		} catch (Throwable ignored) {}
		try {
			if (c==null&&getParent()!=null)c=getParent().loadClass(name);
		} catch (Throwable ignored) {}
		try {
			if (c==null&&getParent()!=null)c=this.getClass().getClassLoader().loadClass(name);
		} catch (Throwable ignored) {}
		if (c==null) {
			throw new ClassNotFoundException(name);
		}
		return c;
	}

	@Override
	protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
		this.resolveClass(this.loadClass(name));
		return this.findClass(name);
	}

	public Class<?> loadClassfile(String name, boolean resolve) throws ClassNotFoundException {
		return this.loadClass(name,resolve);
	}
}