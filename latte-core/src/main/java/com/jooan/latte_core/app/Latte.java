package com.jooan.latte_core.app;

import android.content.Context;

import java.util.HashMap;

/**
 *
 */
public final class Latte {
    public static Configurator init(Context context){
        getConfigurations().put(ConfigType.APPLICATION_CONTEXT.name(),context.getApplicationContext());
        return Configurator.getInstance();
    }

    public static HashMap<Object,Object> getConfigurations(){
        return Configurator.getInstance().getLatteConfigs();
    }

    public static Context getApplicationContext(){
        return (Context) getConfigurations().get(ConfigType.APPLICATION_CONTEXT.name());
    }
}
