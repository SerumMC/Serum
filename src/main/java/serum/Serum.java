package serum;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import serum.server.ServerProxy;
import serum.server.module.ModuleParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Mod(modid = "serum", name = "Serum", version = Serum.VERSION)
public class Serum {
    public static final String VERSION = "1.0.0";

    @Mod.Instance("serum")
    public static Serum INSTANCE;
    @SidedProxy(serverSide = "serum.server.ServerProxy", clientSide = "serum.client.ClientProxy")
    public static ServerProxy PROXY;
    public static Logger LOGGER = LogManager.getLogger("Serum");
    public static int BUILD = 9999;

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        InputStream stream = Serum.class.getResourceAsStream("build");
        if (stream != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            try {
                String line = reader.readLine();
                Serum.BUILD = Integer.parseInt(line);
            } catch (IOException | NumberFormatException e) {
                e.printStackTrace();
            }
            IOUtils.closeQuietly(reader);
            IOUtils.closeQuietly(stream);
        }

        Serum.LOGGER.info("Loading Serum " + Serum.VERSION + "." + Serum.BUILD);
        Serum.PROXY.onPreInit();
        ModuleParser.INSTANCE.loadModules();
    }
}
