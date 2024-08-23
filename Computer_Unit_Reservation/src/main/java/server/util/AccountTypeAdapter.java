package server.util;

import com.google.gson.*;
import shared.Account;

import java.lang.reflect.Type;

public class AccountTypeAdapter implements JsonSerializer<Account>, JsonDeserializer<Account> {

    @Override
    public Account deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String userID = jsonObject.get("userID").getAsString();
        String password = jsonObject.get("password").getAsString();
        return new Account(userID, password);
    }

    @Override
    public JsonElement serialize(Account src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("userID", src.getUserID());
        jsonObject.addProperty("password", src.getPassword());
        return jsonObject;
    }
}
