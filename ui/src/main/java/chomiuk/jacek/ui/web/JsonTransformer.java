package chomiuk.jacek.ui.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Component;
import spark.ResponseTransformer;

@Component
public class JsonTransformer implements ResponseTransformer {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public String render(Object o) throws Exception {
        return gson.toJson(o);
    }
}
