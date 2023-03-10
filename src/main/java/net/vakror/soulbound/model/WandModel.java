package net.vakror.soulbound.model;

import java.util.EnumMap;
import java.util.Set;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;

import com.mojang.math.Transformation;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.SimpleModelState;
import net.minecraftforge.client.model.geometry.IGeometryBakingContext;
import net.minecraftforge.client.model.geometry.IUnbakedGeometry;

@SuppressWarnings("deprecation")
public class WandModel implements IUnbakedGeometry<WandModel> {
	private ResourceLocation baseMaterial;
	private ImmutableList<WandModelLoader.TypedTextures> typedTexturesList;

	public WandModel(ResourceLocation baseMaterialIn, ImmutableList<WandModelLoader.TypedTextures> typedTexturesListIn){
		this.typedTexturesList = typedTexturesListIn;
		this.baseMaterial = baseMaterialIn;
	}

	@Override
	public BakedModel bake(IGeometryBakingContext owner, ModelBakery bakery
			, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelTransform
			, ItemOverrides overrides, ResourceLocation modelLocation){

		TextureAtlasSprite particle = spriteGetter.apply(owner.getMaterial("particle"));

		ModelState transformsFromModel = new SimpleModelState(owner.getRootTransform(), modelTransform.isUvLocked());
		ImmutableMap<ItemTransforms.TransformType, ItemTransform> transformMap = transformsFromModel != null ?
				getTransforms(owner, new CompositeModelState(transformsFromModel, modelTransform)) :
				getTransforms(owner, modelTransform);

		modelTransform = transformsFromModel != null ? new CompositeModelState(transformsFromModel, modelTransform) : modelTransform;

		Transformation transform = modelTransform.getRotation();

		/* Vanillad BakedItemModel but with custom MealItemOverrideList, used in store data, it'll display nothing */
		return new WandBakedModel(this.baseMaterial, this.typedTexturesList, spriteGetter, particle, transformMap, transform, owner.useBlockLight());
	}

	@Override
	public Collection<Material> getMaterials(IGeometryBakingContext owner
			, Function<ResourceLocation, UnbakedModel> modelGetter
			, Set<Pair<String, String>> missingTextureErrors){

		return this.typedTexturesList.stream().map(
				typedTexture -> typedTexture.getTextures()
		).flatMap(
				textures -> textures.values().stream().map(
						location -> new Material(TextureAtlas.LOCATION_BLOCKS, location)
				)
		).collect(Collectors.toList());
	}

	public static ImmutableMap<ItemTransforms.TransformType, ItemTransform> getTransforms(IGeometryBakingContext owner, ModelState state)
	{
		EnumMap<ItemTransforms.TransformType, ItemTransform> map = new EnumMap<>(ItemTransforms.TransformType.class);
		for(ItemTransforms.TransformType type : ItemTransforms.TransformType.values())
		{
			ItemTransform tr = owner.getTransforms().getTransform(type);
			map.put(type, tr);
		}
		return ImmutableMap.copyOf(map);
	}
}