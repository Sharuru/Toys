package self.sharuru;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Convert gradle-typed cache to maven local-typed cache
 * <p>
 * Example:
 * From X:\a.b.c.d\1.0.0-RELEASE\SHA_1_VALUE\something.jar/pom
 * To   X:\a\b\c\d\1.0.0-RELEASE\something.jar/pom
 * <p>
 * Usually used for offline gradle build with secret dependencies.
 */
public class Main {

    private static final String FROM_DIR = "D:\\G";
    private static final String TO_DIR = "D:\\O";

    public static void main(String[] args) {
        System.out.println("Listing file under " + FROM_DIR + "...");

        // list all flies.
        List<cpOpt> allFiles = new ArrayList<>();
        try {
            Files.walk(Paths.get(FROM_DIR))
                    .filter(Files::isRegularFile)
                    .forEach(p -> allFiles.add(new cpOpt(p.toAbsolutePath())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Get " + allFiles.size() + " files.");

        // trim the path
        for (cpOpt aFile : allFiles) {
            //System.out.println(aFile.getFrom().toString());

            // split path by '/'
            String[] origPath = aFile.getFrom().toString().split("\\\\");
            String toPath = "";

            // split package by '.'
            String[] pkgName = origPath[2].split("\\.");

            for (int i = 0; i < origPath.length; i++) {
                // ignore SHA1
                if (i != origPath.length - 2) {
                    if (i != 2) {
                        toPath = toPath + "\\" + origPath[i];
                    } else {
                        // package name append
                        for (String p : pkgName) {
                            toPath = toPath + "\\" + p;
                        }
                    }
                }
            }

            // cut final '\'
            toPath = toPath.substring(1);
            toPath = toPath.replace(FROM_DIR, TO_DIR);

            aFile.setTo(Paths.get(toPath));

            try {
                Files.createDirectories(aFile.getTo().getParent());
                Files.copy(aFile.getFrom(), aFile.getTo(), REPLACE_EXISTING);
                System.out.println("Copied: " + aFile.getFrom() + " --> " + aFile.getTo());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Finished.");
    }

    private static class cpOpt {
        Path from;
        Path to;

        private Path getFrom() {
            return from;
        }

        private void setFrom(Path from) {
            this.from = from;
        }

        private Path getTo() {
            return to;
        }

        private void setTo(Path to) {
            this.to = to;
        }

        private cpOpt(Path oP) {
            this.from = oP;
        }
    }
}
