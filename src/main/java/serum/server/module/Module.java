package serum.server.module;

import java.util.ArrayList;
import java.util.List;

public class Module {
    public final String id;
    public final List<String> mods = new ArrayList<>();

    public Module(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Module[id=" + this.id + ",mods=" + this.mods + "]";
    }
}
