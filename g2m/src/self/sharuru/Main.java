package self.sharuru;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class Main {

    public static void main(String[] args) {
        // write your code here
        //List
        //Split
        //mkdir
        //cp
        //if 40 and pom /jar
        //throw
        System.out.println("Listing file in dir...");
/*        File targetFolder = new File("D:\\G");
        File[] subFolders = targetFolder.listFiles();
        for(File folder : subFolders){
            System.out.println("Current: " + folder.getName());
            String path = "D:\\O\\";
            for(String part : folder.getName().split("\\.")){
                path = path + part + "\\";
            }
            System.out.println("Parent path: " + path);
            // SUB
            if(folder.isDirectory()){

            }
        }*/
        List<fileDict> files = new ArrayList<>();
        try {
            Files.walk(Paths.get("D:\\G"))
                    .filter(Files::isRegularFile)
                    .forEach(p -> files.add(new fileDict(p.toAbsolutePath())));

        } catch (IOException e) {
            e.printStackTrace();
        }
        for (fileDict f : files) {
            //System.out.println(f.getoP().toString());
            String op = f.getoP().toString();
            //System.out.println("O: " + op);
            String[] a = op.split("\\\\");
            String trueP = "";
            //System.out.println(a.size());
            String[] ss = a[2].split("\\.");

            for (int i = 0; i < a.length; i++) {

                if (a[i].length() != 40 && a[i].length() != 39) {
                    //System.out.println(l);
                    if (i != 2) {
                        trueP = trueP + "\\" + a[i];
                    } else {
                        for (String k : ss) {
                            trueP = trueP + "\\" + k;
                        }
                    }

                }
            }
            trueP = trueP.substring(1);
            trueP = trueP.replace("D:\\G\\", "D:\\O\\");
            System.out.println(trueP);
            f.settP(Paths.get(trueP));

            File sourceFile = f.getoP().toFile();
            Path sourcePath = sourceFile.toPath();

            File destFile = f.gettP().toFile();
            Path destPath = destFile.toPath();

            try {
                if(destPath.toString().endsWith(".jar") || destPath.toString().endsWith(".pom")){
                    //FileUtils.copyFile(f.getoP().toFile(), f.gettP().toFile());
                    Files.createDirectories(destPath.getParent());
                    Files.copy( sourcePath, destPath,REPLACE_EXISTING );
                }

            } catch (Exception e) {

            }
        }

    }

    public static class fileDict {
        Path oP;
        Path tP;

        public Path getoP() {
            return oP;
        }

        public void setoP(Path oP) {
            this.oP = oP;
        }

        public Path gettP() {
            return tP;
        }

        public void settP(Path tP) {
            this.tP = tP;
        }

        public fileDict(Path oP) {
            this.oP = oP;
        }
    }
}
