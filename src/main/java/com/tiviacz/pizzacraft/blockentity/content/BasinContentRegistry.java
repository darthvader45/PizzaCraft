package com.tiviacz.pizzacraft.blockentity.content;

import com.google.common.collect.Maps;

import java.util.Map;

public class BasinContentRegistry
{
    public static final BasinContentRegistry REGISTRY = new BasinContentRegistry();

    public Map<String, BasinContent> contentsRegistry = Maps.newHashMap();

    public BasinContentRegistry() {}

    public Map<String, BasinContent> getContentsRegistry()
    {
        return this.contentsRegistry;
    }

    public BasinContent register(BasinContent content)
    {
        this.contentsRegistry.putIfAbsent(content.name, content);

        if(content.contentType == BasinContentType.SAUCE)
        {
            SauceRegistry.REGISTRY.register(content);
        }

        return this.contentsRegistry.get(content.name);
    }

    public BasinContent fromString(String name)
    {
        if(contentsRegistry.containsKey(name))
        {
            return contentsRegistry.get(name);
        }
        throw new IllegalStateException("Content does not exist in registry!");
    }
}