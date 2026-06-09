package com.mrwizard94.biglittlefixes.proxy;

/** Client-side proxy; extends CommonProxy for future client-only logic. */
public final class ClientProxy extends CommonProxy {
    @Override
    public void init() {
        super.init();
        // Client-only initialization will go here in feature implementations.
    }
}
