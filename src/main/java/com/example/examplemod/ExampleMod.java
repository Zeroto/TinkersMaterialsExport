package com.example.examplemod;

import java.util.Arrays;
import java.util.Collection;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import org.apache.logging.log4j.Logger;

import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.*;

@Mod(modid = ExampleMod.MODID, name = ExampleMod.NAME, version = ExampleMod.VERSION)
public class ExampleMod
{
    public static final String MODID = "examplemod";
    public static final String NAME = "Example Mod";
    public static final String VERSION = "1.0";

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
        logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        Collection<Material> materials = TinkerRegistry.getAllMaterials();
        String output = "{";
        for(Material mat : materials) {
            Collection<IMaterialStats> stats = mat.getAllStats();
            // logger.info("mat: {}", mat.identifier);
            output += "\"" + mat.identifier + "\": {";
            
            output += "\"colour\": " + mat.materialTextColor + ',';

            for(IMaterialStats stat : stats) {
                String statIdentifier = stat.getIdentifier();
                if (statIdentifier.equals(MaterialTypes.HANDLE)) {
                    HandleMaterialStats handle = (HandleMaterialStats)stat;
                    output += "\"handle\": {\"durability\":" + handle.durability + ", \"modifier\": " + handle.modifier + "},";
                    // logger.info("stat handle durability: {}", handle.durability);
                    // logger.info("stat handle modifier: {}", handle.modifier);
                } else if (statIdentifier.equals(MaterialTypes.BOW)) {
                    BowMaterialStats bow = (BowMaterialStats)stat;
                    output += "\"bow\": {\"drawspeed\":" + bow.drawspeed + ", \"range\": " + bow.range + ", \"bonusDamage\": " + bow.bonusDamage + "},";
                    // logger.info("stat bow drawSpeed: {}", bow.drawspeed);
                    // logger.info("stat bow range: {}", bow.range);
                    // logger.info("stat bow bonusDamage: {}", bow.bonusDamage);
                } else if (statIdentifier.equals(MaterialTypes.BOWSTRING)) {
                    BowStringMaterialStats bowString = (BowStringMaterialStats)stat;
                    output += "\"bowString\": {\"modifier\":" + bowString.modifier + "},";
                    // logger.info("stat bow string modifier: {}", bowString.modifier);
                } else if (statIdentifier.equals(MaterialTypes.EXTRA)) {
                    ExtraMaterialStats extra = (ExtraMaterialStats)stat;
                    output += "\"extra\": {\"extraDurability\":" + extra.extraDurability + "},";
                    // logger.info("stat extra extraDurability: {}", extra.extraDurability);
                } else if (statIdentifier.equals(MaterialTypes.FLETCHING)) {
                    FletchingMaterialStats fletching = (FletchingMaterialStats)stat;
                    output += "\"fletching\": {\"accuracy\":" + fletching.accuracy + ", \"modifier\": " + fletching.modifier + "},";
                    // logger.info("stat fletching.accuracy: {}", fletching.accuracy);
                    // logger.info("stat fletching.modifier: {}", fletching.modifier);
                } else if (statIdentifier.equals(MaterialTypes.HEAD)) {
                    HeadMaterialStats head = (HeadMaterialStats)stat;
                    output += "\"head\": {\"attack\":" + head.attack + ", \"durability\": " + head.durability + ", \"harvestLevel\": " + head.harvestLevel + ", \"miningspeed\": " + head.miningspeed + "},";
                    // logger.info("stat head.attack: {}", head.attack);
                    // logger.info("stat head.durability: {}", head.durability);
                    // logger.info("stat head.harvestLevel: {}", head.harvestLevel);
                    // logger.info("stat head.miningspeed: {}", head.miningspeed);
                } else if (statIdentifier.equals(MaterialTypes.PROJECTILE)) {
                    ProjectileMaterialStats _ = (ProjectileMaterialStats)stat;
                    // this is a dummy stat
                } else if (statIdentifier.equals(MaterialTypes.SHAFT)) {
                    ArrowShaftMaterialStats shaft = (ArrowShaftMaterialStats)stat;
                    output += "\"shaft\": {\"bonusAmmo\":" + shaft.bonusAmmo + ", \"modifier\": " + shaft.modifier + "},";
                    // logger.info("stat shaft.bonusAmmo: {}", shaft.bonusAmmo);
                    // logger.info("stat shaft.modifier: {}", shaft.modifier);
                } else {
                    logger.info("unknown stat: {}", stat.getIdentifier());
                }
            }
            output += "},";
        }

        output += "}";

        // logger.info("output: {}", output);
        Path file = Paths.get("tinkerMaterials.txt");
        try {
            Files.write(file, Arrays.asList(output), StandardCharsets.UTF_8);
        }
        catch (IOException e) {
            logger.info("failed to write material stats");
        }
    }
}
