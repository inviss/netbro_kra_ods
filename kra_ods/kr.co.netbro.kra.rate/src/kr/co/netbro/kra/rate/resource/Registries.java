package kr.co.netbro.kra.rate.resource;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.osgi.framework.Bundle;

public class Registries {
	
	protected static ColorRegistry colorReg;
	protected static FontRegistry fontReg;
	protected static ImageRegistry imgReg;
	
	private static Bundle bundle;
	private static Registries instance;
	
	private Registries() {
		//colorReg = new ColorRegistry();
		//fontReg = new FontRegistry();
		//imgReg = new ImageRegistry();
		colorReg = JFaceResources.getColorRegistry();
		fontReg = JFaceResources.getFontRegistry();
		imgReg = JFaceResources.getImageRegistry();
	}
	
	/**
	 * Is used to determine the root folder of the application. Get the bundle
	 * from <code>Activator.getDefault().getBundle()</code>
	 */
	public static Registries getInstance(Bundle bundle) {
		Registries.bundle = bundle;
		return getInstance();
	}
	
	public static Registries getInstance() {
		if (instance == null) {
			instance = new Registries();
		}
		return instance;
	}

	public Registries init(IRegistriesConfiguration configurator) {
		configurator.configure(this);
		return this;
	}
	
	/**
	 * @param path
	 *for example icons/background.jpg
	 */
	public void putImage(String key, String path) {
		imgReg.put(key, ImageDescriptor.createFromURL(FileLocator.find(bundle,
				new Path(path), null)));
	}
	
	public void putImage(String key, Image image) {
		imgReg.put(key, image);
	}
	
	public Image getImage(String key) {
		return imgReg.get(key);
	}
	
	public ImageDescriptor getImageDescriptor(String key) {
		return imgReg.getDescriptor(key);
	}
	
	public void putColor(String symbolicName, int red, int green, int blue) {
		colorReg.put(symbolicName, new RGB(red, green, blue));
	}
	
	public void putColor(String symbolicName, RGB colorData) {
		colorReg.put(symbolicName, colorData);
	}
	
	/**
	 * @param hexColorStr
	 *"00FF00"
	 */
	public void putColor(String symbolicName, String hexColorStr) {
		Integer colorValue = Integer.parseInt(hexColorStr, 16);
		int red = (colorValue & 0xFF0000) >> 16;
		int green = (colorValue & 0x00FF00) >> 8;
		int blue = (colorValue & 0x0000FF);
		putColor(symbolicName, new RGB(red, green, blue));
	}
	
	public Color getColor(String key) {
		return colorReg.get(key);
	}
	
	/**
	 * Looks up the registry if there is a color for the key. If not, a new
	 * color is instanciated from the hex-representation.
	 */
	public Color getColor(String key, String hexColorStr) {
		Color color = colorReg.get(key);
		if (color == null) {
			putColor(key, hexColorStr);
			color = colorReg.get(key);
		}
		return color;
	}
	
	public String getColorAsHex(String key) {
		String hexColorStr = null;
		Color color = getColor(key);
		if (color != null) {
			hexColorStr = toHex(color.getRGB());
		}
		return hexColorStr;
	}
	
	/**
	 * Converts the color of an <code>RGB</code> object into his hex values. <br>
	 * RGB(0,255,0) -> "00FF00"
	 */
	public String toHex(RGB rgb) {
		String hexColorStr = Integer.toHexString((rgb.red << 16)
				+ (rgb.green << 8) + rgb.blue);
		if (hexColorStr.length() == 4) {
			hexColorStr = "00" + hexColorStr;
		} else if (hexColorStr.length() == 2) {
			hexColorStr = "0000" + hexColorStr;
		}
		return hexColorStr;
	}
	
	public void putFont(String symbolicName, FontData fontData) {
		fontReg.put(symbolicName, new FontData[] {fontData});
	}
	
	public void putFont(String symbolicName, FontData fontData[]) {
		fontReg.put(symbolicName, fontData);
	}
	
	public Font getFont(String key) {
		return fontReg.get(key);
	}
	
}
