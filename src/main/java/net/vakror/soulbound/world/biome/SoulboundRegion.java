package net.vakror.soulbound.world.biome;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.Climate.*;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import org.checkerframework.checker.units.qual.C;
import terrablender.api.ParameterUtils;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.function.Consumer;

public class SoulboundRegion extends Region {
    public SoulboundRegion(ResourceLocation name, int weight) {
        super(name, RegionType.OVERWORLD, weight);
    }

    private final Climate.Parameter FULL_RANGE = Climate.Parameter.span(-1.0F, 1.0f);

    @Override
    @SuppressWarnings("all")
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<ParameterPoint, ResourceKey<Biome>>> mapper) {
        this.addModifiedVanillaOverworldBiomes(mapper, builder -> {
            builder.replaceBiome(Biomes.DEEP_DARK, ModBiomes.CORRUPTED_CAVE.getKey());
        });
    }

    public static void addSurfaceBiome(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper, Climate.Parameter temperature, Climate.Parameter humidity, Climate.Parameter continentalness, Climate.Parameter erosion, Climate.Parameter weirdness, float offset, ResourceKey<Biome> biome) {
        mapper.accept(Pair.of(Climate.parameters(temperature, humidity, continentalness, erosion, ParameterUtils.Depth.UNDERGROUND.parameter(), weirdness, offset), biome));
        mapper.accept(Pair.of(Climate.parameters(temperature, humidity, continentalness, erosion, Climate.Parameter.point(1.0F), weirdness, offset), biome));
    }

    public static void addUndergroundBiome(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper, Climate.Parameter temperature, Climate.Parameter humidity, Climate.Parameter continentalness, Climate.Parameter erosion, Climate.Parameter weirdness, float offset, ResourceKey<Biome> biome) {
        mapper.accept(Pair.of(Climate.parameters(temperature, humidity, continentalness, erosion, ParameterUtils.Depth.UNDERGROUND.parameter(), weirdness, offset), biome));
    }
}
