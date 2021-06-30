package com.jooan.latte_core.app;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;

public class Configurator {
    public static final HashMap<String,Object> LATTE_CONFIGS = new HashMap<>();
    public static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();

    private Configurator(){
        LATTE_CONFIGS.put(ConfigType.CONFIG_READY.name(),false);
    }
    public static Configurator getInstance(){
        return ConfiguratorHolder.mInstance;
    }
    /**
     * 单例模式：静态内部类（懒加载、线程安全）
     */
    private static class ConfiguratorHolder{
        private static final Configurator mInstance = new Configurator();
    }

    final HashMap<String,Object> getLatteConfigs(){
        return LATTE_CONFIGS;
    }

    public final void configure(){
        initIcons();
        LATTE_CONFIGS.put(ConfigType.CONFIG_READY.name(),true);
    }

    private static void checkConfiguration(){
        final  boolean isReady = (boolean) LATTE_CONFIGS.get(ConfigType.CONFIG_READY.name());
        if(!isReady){
            throw new RuntimeException("Configuration is not Ready");
        }
    }

    private void initIcons(){
        if(ICONS.size()>0){
            Iconify.IconifyInitializer initializer =Iconify.with(ICONS.get(0));
            for(int i=1;i<ICONS.size();i++){
                initializer.with(ICONS.get(i));
            }
        }
    }

    public final Configurator withIcon(IconFontDescriptor iconFontDescriptor){
        ICONS.add(iconFontDescriptor);
        return this;
    }

    public final Configurator withApiHost(String host){
        LATTE_CONFIGS.put(ConfigType.API_HOSE.name(),host);
        return this;

    }

    public static <T> T checkConfig(Enum<ConfigType> key){
        checkConfiguration();
        return (T)LATTE_CONFIGS.get(key);

    }
}
