package basic.zBasic.util.machine;

/**
 *  Übernommen aus: 
 *  https://github.com/scijava/scijava-common/blob/master/src/main/java/org/scijava/util/PlatformUtils.java
 * 
 * @author Fritz Lindhauer, 06.08.2020, 09:24:40
 * 
 */
public class PlatformUtilsZZZ {
	/** Whether the operating system is Windows-based. */
	public static boolean isWindows() {
		return osName().startsWith("Win");
	}

	/** Whether the operating system is Mac-based. */
	public static boolean isMac() {
		return osName().startsWith("Mac");
	}

	/** Whether the operating system is Linux-based. */
	public static boolean isLinux() {
		return osName().startsWith("Linux");
	}

	/** Whether the operating system is POSIX compliant. */
	public static boolean isPOSIX() {
		return isMac() || isLinux();
	}

	/** Gets the name of the operating system. */
	public static String osName() {
		final String osName = System.getProperty("os.name");
		return osName == null ? "Unknown" : osName;
	}

}
