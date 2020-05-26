package taskscheduler;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.prefs.Preferences;

import static java.lang.System.setErr;
import java.util.prefs.BackingStoreException;
import static java.util.prefs.Preferences.systemRoot;

public class AdministratorChecker {
    private static Boolean IS_RUNNING_AS_ADMINISTRATOR = null;
    
    public static boolean isRunningAsAdministrator() {
        if (IS_RUNNING_AS_ADMINISTRATOR != null) return IS_RUNNING_AS_ADMINISTRATOR; //cache
        Preferences preferences = systemRoot();
        synchronized (System.err) {
            setErr(new PrintStream(new OutputStream() {
                @Override
                public void write(int b) {
                }
            }));
            try {
                preferences.put("foo", "bar"); // SecurityException on Windows
                preferences.remove("foo");
                preferences.flush(); // BackingStoreException on Linux
                IS_RUNNING_AS_ADMINISTRATOR = true;
            } catch (BackingStoreException ex) {
                IS_RUNNING_AS_ADMINISTRATOR = false;
            } finally {
                setErr(System.err);
            }
            return IS_RUNNING_AS_ADMINISTRATOR;
        }
    }
}