package serum.server.module.discoverer;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class DirectoryDiscoverer implements Discoverer {
    public static final FilenameFilter JAR_FILTER = (dir, name) -> name.endsWith(".jar");

    @Override
    public List<ModModuleInfo> discoverModules() {
        List<ModModuleInfo> modules = new ArrayList<>();
        List<File> files = new ArrayList<>();
        File dirMods = new File("mods");
        if (dirMods.exists() && dirMods.isDirectory()) {
            Collections.addAll(files, dirMods.listFiles(DirectoryDiscoverer.JAR_FILTER));
        }
        JsonParser parser = new JsonParser();

        for (File file : files) {
            try (JarFile jarFile = new JarFile(file)) {
                Enumeration<JarEntry> entries = jarFile.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    if (entry.getName().endsWith("serum.json")) {
                        InputStream stream = jarFile.getInputStream(entry);
                        JsonObject object = parser.parse(new InputStreamReader(stream)).getAsJsonObject();
                        stream.close();
                        modules.add(new ModModuleInfo(object, file));
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return modules;
    }
}
