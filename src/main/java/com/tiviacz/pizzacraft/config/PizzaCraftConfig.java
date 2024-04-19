package com.tiviacz.pizzacraft.config;

import com.tiviacz.pizzacraft.PizzaCraft;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

@Mod.EventBusSubscriber(modid = PizzaCraft.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PizzaCraftConfig
{
    public static class Server
    {
        public final ForgeConfigSpec.BooleanValue dropOliveFromJungleLeaves;
        public final ForgeConfigSpec.BooleanValue broccoliSeedDrops;
        public final ForgeConfigSpec.BooleanValue cucumberSeedDrops;
        public final ForgeConfigSpec.BooleanValue pepperSeedDrops;
        public final ForgeConfigSpec.BooleanValue pineappleSeedDrops;
        public final ForgeConfigSpec.BooleanValue tomatoSeedDrops;
        public final ForgeConfigSpec.BooleanValue cornDrops;
        public final ForgeConfigSpec.BooleanValue onionDrops;

        Server(final ForgeConfigSpec.Builder builder)
        {
            builder.comment("Server config settings")
                    .push("server");

            dropOliveFromJungleLeaves = builder
                    .comment("Enable olive drop from jungle leaves")
                    .define("dropOliveFromJungleLeaves", false);

            broccoliSeedDrops = builder
                    .comment("Enable broccoli seed drops from tall grass")
                    .define("broccoliSeedDrops", true);

            cucumberSeedDrops = builder
                    .comment("Enable cucumber seed drops from tall grass")
                    .define("cucumberSeedDrops", true);

            pepperSeedDrops = builder
                    .comment("Enable pepper seed drops from tall grass")
                    .define("pepperSeedDrops", true);

            pineappleSeedDrops = builder
                    .comment("Enable pepper seed drops from tall grass")
                    .define("pineappleSeedDrops", true);

            tomatoSeedDrops = builder
                    .comment("Enable tomato seed drops from tall grass")
                    .define("tomatoSeedDrops", true);

            cornDrops = builder
                    .comment("Enable corn drops from tall grass")
                    .define("cornDrops", true);

            onionDrops = builder
                    .comment("Enable onion drops from tall grass")
                    .define("onionDrops", true);

            builder.pop();
        }
    }

    //SERVER
    private static final ForgeConfigSpec serverSpec;
    public static final Server SERVER;

    static {
        final Pair<Server, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Server::new);
        serverSpec = specPair.getRight();
        SERVER = specPair.getLeft();
    }

    //REGISTRY
    public static void register(final ModLoadingContext context)
    {
        context.registerConfig(ModConfig.Type.SERVER, serverSpec);
    }
}