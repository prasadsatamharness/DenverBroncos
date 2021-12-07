package com.example.denverbroncos;

import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Map;



public class OfferDetector {

    interface OfferCallback {
        void call(final String offer);
    }


    public void getOffersForSection(final String section, final OfferCallback callback) {
                try  {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(DenverStadiumConstants.OFFER_URL)
                            .build();
                    final Call call = client.newCall(request);

                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {
                            callback.call(null);
                            return;
                        }

                        @Override
                        public void onResponse(Response response) throws IOException {
                            if (response.code() == 200) {
                                final Gson gson = new Gson();
                                final Map map = gson.fromJson(response.body().string(), Map.class);
                                if(map == null || map.size() < 2 || !section.equals(map.get("section"))) {
                                    callback.call(null);
                                    return;
                                }
                                callback.call((String) map.get("product"));
                                return;
                            }
                        }
                    });
                } catch (Exception exception) {
                    Log.d("whats the error", exception.getLocalizedMessage());
                }
    }

}
