package com.canoo.dp.impl.platform.client.json;

import com.canoo.dp.impl.platform.client.AbstractServiceProvider;
import com.canoo.platform.client.ClientConfiguration;
import com.google.gson.Gson;
import org.apiguardian.api.API;

import static org.apiguardian.api.API.Status.INTERNAL;

@API(since = "0.x", status = INTERNAL)
public class JsonProvider extends AbstractServiceProvider<Gson> {

    public JsonProvider() {
        super(Gson.class);
    }

    @Override
    protected Gson createService(ClientConfiguration configuration) {
        return new Gson();
    }
}
