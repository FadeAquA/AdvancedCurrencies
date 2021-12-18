package me.aqua.currency.utils;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import tk.piggyster.api.color.ColorAPI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ItemBuilder {

    private Material material;
    private String name;
    private List<String> lore;
    private String skullBase64;
    private String skullTexture;
    private boolean enchanted;
    private int model;

    public ItemBuilder(FileConfiguration file, String path) {
        material = Material.valueOf(file.getString(path + ".material"));
        name = file.getString(path + ".name", null);
        lore = file.contains(path + ".lore") ? file.getStringList(path + ".lore") : null;
        skullBase64 = file.getString(path + ".base64", null);
        skullTexture = file.getString(path + ".texture", null);
        model = file.getInt(path + ".model", -1);
        enchanted = file.getBoolean(path + ".enchanted", false);
    }

    public ItemBuilder(Material material, String name, List<String> lore, String skullBase64, String skullTexture, boolean enchanted, int model) {
        this.material = material;
        this.name = name;
        this.lore = lore;
        this.skullBase64 = skullBase64;
        this.skullTexture = skullTexture;
        this.enchanted = enchanted;
        this.model = model;
    }

    public ItemBuilder(Material material) {
        this.material = material;
        this.model = -1;
    }

    public ItemBuilder setLore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    public ItemBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ItemBuilder setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public ItemBuilder setModel(int model) {
        this.model = model;
        return this;
    }

    public ItemBuilder setSkullBase64(String skullBase64) {
        this.skullBase64 = skullBase64;
        return this;
    }

    public ItemBuilder setSkullTexture(String skullTexture) {
        this.skullTexture = skullTexture;
        return this;
    }

    public ItemBuilder setEnchanted(boolean enchanted) {
        this.enchanted = enchanted;
        return this;
    }

    public ItemBuilder setPlaceholder(String key, String value) {
        if (name != null) {
            name = name.replace(key, value);
        }

        if (lore != null) {
            lore.replaceAll(line -> line.replace(key, value));
        }

        return this;
    }

    public ItemBuilder addLoreLine(String... lines) {
        lore = lore == null? new ArrayList<>() : lore;
        lore.addAll(Arrays.asList(lines));
        return this;
    }

    public ItemStack build() {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) {
            return itemStack;
        }
        if (name != null) {
            meta.setDisplayName(ColorAPI.process(name));
        }
        if (lore != null && !lore.isEmpty()) {
            lore = lore.stream().map(ColorAPI :: process).collect(Collectors.toList());
            meta.setLore(lore);
        }
        if (model > 0) {
            meta.setCustomModelData(model);
        }
        if (enchanted) {

        }
        itemStack.setItemMeta(meta);

        if (meta instanceof SkullMeta) {
            itemStack = SkullCreator.itemWithBase64(itemStack, skullBase64);
            itemStack = SkullCreator.itemWithBase64(itemStack, skullTexture);
        }
        return itemStack;
    }

    @Override
    public ItemBuilder clone() {
        return new ItemBuilder(material, name, Optional.ofNullable(lore).map(ArrayList::new).orElse((ArrayList<String>) lore), skullBase64, skullTexture, enchanted, model);
    }
}