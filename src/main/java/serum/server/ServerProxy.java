package serum.server;

import net.minecraftforge.common.MinecraftForge;

public class ServerProxy {
    public void onPreInit() {
        MinecraftForge.EVENT_BUS.register(ServerEventHandler.INSTANCE);
    }
}
