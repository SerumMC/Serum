package serum.server.module;

import serum.Serum;
import serum.server.module.discoverer.ClasspathDiscoverer;
import serum.server.module.discoverer.DirectoryDiscoverer;
import serum.server.module.discoverer.Discoverer;
import serum.server.module.discoverer.ModModuleInfo;

import java.util.ArrayList;
import java.util.List;

public enum ModuleParser {
    INSTANCE;

    public final List<Module> modules = new ArrayList<>();

    public void loadModules() {
        List<Discoverer> discoverers = new ArrayList<>();
        discoverers.add(new ClasspathDiscoverer());
        discoverers.add(new DirectoryDiscoverer());

        for (Discoverer discoverer : discoverers) {
            for (ModModuleInfo info : discoverer.discoverModules()) {
                if (info.version > Serum.BUILD) {
                    throw new RuntimeException("Mod " + info.modID + " requests Serum version " + info.version + ", but version " + Serum.BUILD + " is installed!");
                }

                for (String id : info.modules) {
                    boolean flag = false;

                    for (Module module : this.modules) {
                        if (module.id.equals(id)) {
                            module.mods.add(info.modID);
                            flag = true;
                            break;
                        }
                    }

                    if (flag) {
                        continue;
                    }

                    Module module = new Module(id);
                    module.mods.add(info.modID);

                    this.modules.add(module);
                }
            }
        }

        System.out.println(this.modules);
    }
}
