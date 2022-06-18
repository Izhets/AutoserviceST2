package ru.cs.vsu.ast2.api.application.part;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import ru.cs.vsu.ast2.api.application.part.dto.Part;
import ru.cs.vsu.ast2.api.application.part.dto.PartCategory;

import java.util.List;

public interface PartApi {

    @GET("/part")
    Call<List<Part>> getPart(@Header("Authorization") String token);

    @GET("/part-category")
    Call<List<PartCategory>> getPartCategory(@Header("Authorization") String token);

}