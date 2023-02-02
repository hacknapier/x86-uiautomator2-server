import se.vidstige.jadb.JadbConnection;
import se.vidstige.jadb.JadbException;
import se.vidstige.jadb.RemoteFile;
import se.vidstige.jadb.managers.Bash;

import java.io.File;
import java.io.IOException;

public class uiautomator2 {
    public static void main(String[] args) throws IOException, JadbException, InterruptedException {
        JadbConnection jadb = new JadbConnection();

        jadb.getAnyDevice().executeShell("pm uninstall com.github.uiautomator2.test");

        jadb.getAnyDevice().executeShell("pm uninstall com.github.uiautomator2");

        jadb.getAnyDevice().push(new File("uia2-uiautomator2-server-debug-androidTest.apk"), new RemoteFile( "/data/local/tmp/uia2-uiautomator2-server-debug-androidTest.apk"));

        jadb.getAnyDevice().executeShell("pm install -g " + Bash.quote("/data/local/tmp/uia2-uiautomator2-server-debug-androidTest.apk"));

        jadb.getAnyDevice().push(new File("uia2-uiautomator2-server-release.apk"), new RemoteFile( "/data/local/tmp/uia2-uiautomator2-server-release.apk"));

        jadb.getAnyDevice().executeShell("pm install -g " + Bash.quote("/data/local/tmp/uia2-uiautomator2-server-release.apk"));

        Thread.sleep(7 * 1000);

        jadb.getAnyDevice().executeShell("am instrument -w -m    -e debug false -e class 'com.github.uiautomator2.test.UiAutomator2Server' com.github.uiautomator2.test/androidx.test.runner.AndroidJUnitRunner");

        try {
            Object lock = new Object();
            synchronized (lock) {
                while (true) {
                    lock.wait();
                }
            }
        } catch (InterruptedException ex) {
        }
    }
}