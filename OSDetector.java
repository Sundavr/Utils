/**
 * Operating system detector.
 * @author Johan
 */
public class OSDetector {
    private static final String OS = System.getProperty("os.name").toLowerCase();
    
    /**
     * Retourne true s'il s'agit de windows, false sinon.
     * @return true s'il s'agit de windows, false sinon
     */
    public static boolean isWindows() {
        return (OS.contains("win"));
    }
    
    /**
     * Retourne true s'il s'agit de mac, false sinon.
     * @return true s'il s'agit de mac, false sinon
     */
    public static boolean isMac() {
        return (OS.contains("mac"));
    }

    /**
     * Retourne true s'il s'agit de unix, false sinon.
     * @return true s'il s'agit de unix, false sinon
     */
    public static boolean isUnix() {
        return (0 < OS.indexOf("nix") || OS.contains("nux") || OS.indexOf("aix") > 0 );
    }
    
    /**
     * Retourne true s'il s'agit de solaris, false sinon.
     * @return true s'il s'agit de solaris, false sinon
     */
    public static boolean isSolaris() {
        return (OS.contains("sunos"));
    }
}
