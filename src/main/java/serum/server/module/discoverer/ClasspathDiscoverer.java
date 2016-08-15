package serum.server.module.discoverer;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.launchwrapper.Launch;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class ClasspathDiscoverer implements Discoverer {
    @Override
    public List<ModModuleInfo> discoverModules() {
        List<ModModuleInfo> modules = new ArrayList<>();
        JsonParser parser = new JsonParser();

        try {
            URL roots;
            Enumeration<URL> resources = Launch.classLoader.getResources("");
            while (resources.hasMoreElements()) {
                roots = resources.nextElement();
                File root = new File(roots.getPath());
                File[] files = root.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.getName().equals("serum.json")) {
                            InputStream stream = new FileInputStream(file);
                            JsonObject object = parser.parse(new InputStreamReader(stream)).getAsJsonObject();
                            stream.close();
                            modules.add(new ModModuleInfo(object, root));
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return modules;
    }
}
