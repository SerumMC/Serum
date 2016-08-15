package serum.server.module.discoverer;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;

public class ModModuleInfo {
    public final File file;
    public final String modID;
    public final int version;
    public final String[] modules;

    public ModModuleInfo(JsonObject object, File file) {
        this.file = file;
        this.modID = object.get("id").getAsString();
        this.version = object.get("version").getAsInt();

        JsonArray modules = object.get("modules").getAsJsonArray();
        this.modules = new String[modules.size()];

        for (int i = 0; i < modules.size(); i++) {
            this.modules[i] = modules.get(i).getAsString();
        }
    }
}
