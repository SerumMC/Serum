package serum;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import serum.server.ServerProxy;

@Mod(modid = "serum", name = "Serum", version = Serum.VERSION)
public class Serum {
    public static final String VERSION = "1.0.0";

    @Mod.Instance("serum")
    public static Serum INSTANCE;
    @SidedProxy(serverSide = "serum.server.ServerProxy", clientSide = "serum.client.ClientProxy")
    public static ServerProxy PROXY;

    @Mod.EventHandler
    public void onPreInit() {
        Serum.PROXY.onPreInit();
    }
}
