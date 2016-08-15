package serum.server.module.discoverer;

import java.util.List;

public interface Discoverer {
    List<ModModuleInfo> discoverModules();
}
