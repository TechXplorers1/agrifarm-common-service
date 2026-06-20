package com.agrifarms.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import java.io.File;
import java.net.InetSocketAddress;
import java.net.Socket; 

@SpringBootApplication
@EnableCaching
@EnableScheduling
@EnableAsync
public class AgriFarmsCommonServiceApplication {

    public static void main(String[] args) {
        System.setProperty("user.timezone", "UTC");
        startDatabaseIfNecessary();
        SpringApplication.run(AgriFarmsCommonServiceApplication.class, args);
    }

    private static void startDatabaseIfNecessary() {
        if (isPortInUse("127.0.0.1", 5435)) {
            System.out.println("=== PostgreSQL private instance is already running on port 5435 ===");
            return;
        }

        System.out.println("=== PostgreSQL is not running on port 5435. Starting private instance... ===");

        String pgBin = "C:\\Program Files\\PostgreSQL\\16\\bin";
        File pgBinDir = new File(pgBin);
        if (!new File(pgBinDir, "postgres.exe").exists()) {
            File pgParent = new File("C:\\Program Files\\PostgreSQL");
            if (pgParent.exists() && pgParent.isDirectory()) {
                File[] subDirs = pgParent.listFiles();
                if (subDirs != null) {
                    for (File dir : subDirs) {
                        File binDir = new File(dir, "bin");
                        if (new File(binDir, "postgres.exe").exists()) {
                            pgBinDir = binDir;
                            break;
                        }
                    }
                }
            }
        }

        if (!new File(pgBinDir, "postgres.exe").exists()) {
            System.err.println("=== ERROR: PostgreSQL installation not found under C:\\Program Files\\PostgreSQL ===");
            return;
        }

        System.out.println("=== Found PostgreSQL binaries at: " + pgBinDir.getAbsolutePath() + " ===");

        try {
            String workingDir = System.getProperty("user.dir");
            File currentDir = new File(workingDir);
            // Handle IDE running inside nested folders or build directories
            if (currentDir.getName().equals("target") || currentDir.getName().equals("classes") || currentDir.getName().equals("bin")) {
                currentDir = currentDir.getParentFile();
            }
            if (new File(currentDir, "agrifarm-common-service").exists()) {
                currentDir = new File(currentDir, "agrifarm-common-service");
            }
            File dataDir = new File(currentDir, "pg_dev_data");

            System.out.println("=== Starting private database cluster at: " + dataDir.getAbsolutePath() + " ===");

            String pgCtl = new File(pgBinDir, "pg_ctl.exe").getAbsolutePath();
            ProcessBuilder pb = new ProcessBuilder(
                pgCtl,
                "-D", dataDir.getAbsolutePath(),
                "-o", "-p 5435",
                "start"
            );
            pb.inheritIO();
            Process process = pb.start();
            process.waitFor();

            System.out.println("=== PostgreSQL started. Waiting for connection availability... ===");
            for (int i = 0; i < 20; i++) {
                if (isPortInUse("127.0.0.1", 5435)) {
                    System.out.println("=== PostgreSQL private instance is ready to accept connections! ===");
                    return;
                }
                Thread.sleep(500);
            }
            System.err.println("=== WARNING: PostgreSQL port 5435 did not become active in time ===");
        } catch (Exception e) {
            System.err.println("=== ERROR: Failed to start PostgreSQL: " + e.getMessage() + " ===");
            e.printStackTrace();
        }
    }
    

    private static boolean isPortInUse(String host, int port) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), 200);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}