package cl.nelsonmc.indicadores;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.threeten.bp.Instant;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;
import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.inject.Inject;
import cl.nelsonmc.indicadores.di.BaseApplication;
import cl.nelsonmc.indicadores.modelos.DataIndicador;
import cl.nelsonmc.indicadores.modelos.SerieIndicador;
import cl.nelsonmc.indicadores.webServices.WebClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class MainActivityModelView extends AndroidViewModel {
    @Inject
    WebClient client;

    private MutableLiveData<List<SerieIndicador>> dolarList,euroList;
    private MutableLiveData<List<SerieIndicador>> ufList,ivpList;
    private MutableLiveData<List<SerieIndicador>> ipcList,utmList;
    private MutableLiveData<List<SerieIndicador>> imacecList,cobreList;
    private MutableLiveData<List<SerieIndicador>> desempleoList,bitcoinList;

    public MainActivityModelView(@NonNull Application application){
        super(application);
        ((BaseApplication)getApplication()).getRetrofitComponent().inject(this);
        //((BaseApplication)getApplication()).getFuncionesComponent().inject(this);
        dolarList       = new MutableLiveData<>();
        euroList        = new MutableLiveData<>();
        ufList          = new MutableLiveData<>();
        ivpList         = new MutableLiveData<>();
        ipcList         = new MutableLiveData<>();
        utmList         = new MutableLiveData<>();
        imacecList      = new MutableLiveData<>();
        cobreList       = new MutableLiveData<>();
        desempleoList   = new MutableLiveData<>();
        bitcoinList     = new MutableLiveData<>();
        loadData();
    }

    public MutableLiveData<List<SerieIndicador>> getDolarListObserver() {
        return dolarList;
    }
    public MutableLiveData<List<SerieIndicador>> getEuroListObserver() { return euroList; }
    public MutableLiveData<List<SerieIndicador>> getUFListObserver() { return ufList; }
    public MutableLiveData<List<SerieIndicador>> getIVPListObserver() { return ivpList; }
    public MutableLiveData<List<SerieIndicador>> getIPCListObserver() { return ipcList; }
    public MutableLiveData<List<SerieIndicador>> getUTMListObserver() { return utmList; }
    public MutableLiveData<List<SerieIndicador>> getIMACECListObserver() { return imacecList; }
    public MutableLiveData<List<SerieIndicador>> getCobreListObserver() { return cobreList; }
    public MutableLiveData<List<SerieIndicador>> getDesempleoListObserver() { return desempleoList; }
    public MutableLiveData<List<SerieIndicador>> getBitcoinListObserver() { return bitcoinList; }

    public void loadData(){
        String json;
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<SerieIndicador>>() {}.getType();
        json = sharedPreferences.getString("dolar", null);
        dolarList.postValue(gson.fromJson(json, type));
        json = sharedPreferences.getString("euro", null);
        euroList.postValue(gson.fromJson(json, type));
        json = sharedPreferences.getString("uf", null);
        ufList.postValue(gson.fromJson(json, type));
        json = sharedPreferences.getString("ivp", null);
        ivpList.postValue(gson.fromJson(json, type));
        json = sharedPreferences.getString("ipc", null);
        ipcList.postValue(gson.fromJson(json, type));
        json = sharedPreferences.getString("utm", null);
        utmList.postValue(gson.fromJson(json, type));
        json = sharedPreferences.getString("imacec", null);
        imacecList.postValue(gson.fromJson(json, type));
        json = sharedPreferences.getString("cobre", null);
        cobreList.postValue(gson.fromJson(json, type));
        json = sharedPreferences.getString("desempleo", null);
        desempleoList.postValue(gson.fromJson(json, type));
        json = sharedPreferences.getString("bitcoin", null);
        bitcoinList.postValue(gson.fromJson(json, type));
    }

    private void saveData(String tipoData,List<SerieIndicador> dataList){
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(dataList);
        editor.putString(tipoData, json);
        editor.apply();
    }

    public void requestDolarData() {
        Call<DataIndicador> call = client.getDataDolar();
        call.enqueue(new Callback<DataIndicador>() {
            @Override
            public void onResponse(Call<DataIndicador> call, Response<DataIndicador> response) {
                if (response!=null && response.isSuccessful() && response.body()!=null){
                    dolarList.postValue(response.body().getSerie());
                    saveData("dolar",response.body().getSerie());
                }
            }

            @Override
            public void onFailure(Call<DataIndicador> call, Throwable t) {
                Log.e("onFailure", t.getMessage());
                dolarList.postValue(null);
            }
        });
    }

    public void requestEuroData(){
        Call<DataIndicador> call = client.getDataEuro();
        call.enqueue(new Callback<DataIndicador>() {
            @Override
            public void onResponse(Call<DataIndicador> call, Response<DataIndicador> response) {
                if (response!=null && response.isSuccessful() && response.body()!=null){
                    euroList.postValue(response.body().getSerie());
                    saveData("euro",response.body().getSerie());
                }
            }

            @Override
            public void onFailure(Call<DataIndicador> call, Throwable t) {
                Log.e("onFailure", t.getMessage());
                euroList.postValue(null);
            }
        });
    }

    public void requestUFData(){
        Call<DataIndicador> call = client.getDataUF();
        call.enqueue(new Callback<DataIndicador>() {
            @Override
            public void onResponse(Call<DataIndicador> call, Response<DataIndicador> response) {
                if (response!=null && response.isSuccessful() && response.body()!=null){
                    ufList.postValue(response.body().getSerie());
                    saveData("uf",response.body().getSerie());
                }
            }

            @Override
            public void onFailure(Call<DataIndicador> call, Throwable t) {
                Log.e("onFailure", t.getMessage());
                ufList.postValue(null);
            }
        });
    }

    public void requestIVPData(){
        Call<DataIndicador> call = client.getDataIVP();
        call.enqueue(new Callback<DataIndicador>() {
            @Override
            public void onResponse(Call<DataIndicador> call, Response<DataIndicador> response) {
                if (response!=null && response.isSuccessful() && response.body()!=null){
                    ivpList.postValue(response.body().getSerie());
                    saveData("ivp",response.body().getSerie());
                }
            }

            @Override
            public void onFailure(Call<DataIndicador> call, Throwable t) {
                Log.e("onFailure", t.getMessage());
                ivpList.postValue(null);
            }
        });
    }

    public void requestIPCData(){
        Call<DataIndicador> call = client.getDataIPC();
        call.enqueue(new Callback<DataIndicador>() {
            @Override
            public void onResponse(Call<DataIndicador> call, Response<DataIndicador> response) {
                if (response!=null && response.isSuccessful() && response.body()!=null){
                    ipcList.postValue(response.body().getSerie());
                    saveData("ipc",response.body().getSerie());
                }
            }

            @Override
            public void onFailure(Call<DataIndicador> call, Throwable t) {
                Log.e("onFailure", t.getMessage());
                ipcList.postValue(null);
            }
        });
    }

    public void requestUTMData(){
        Call<DataIndicador> call = client.getDataUTM();
        call.enqueue(new Callback<DataIndicador>() {
            @Override
            public void onResponse(Call<DataIndicador> call, Response<DataIndicador> response) {
                if (response!=null && response.isSuccessful() && response.body()!=null){
                    utmList.postValue(response.body().getSerie());
                    saveData("utm",response.body().getSerie());
                }
            }

            @Override
            public void onFailure(Call<DataIndicador> call, Throwable t) {
                Log.e("onFailure", t.getMessage());
                utmList.postValue(null);
            }
        });
    }

    public void requestIMACECData(){
        Call<DataIndicador> call = client.getDataIMACEC();
        call.enqueue(new Callback<DataIndicador>() {
            @Override
            public void onResponse(Call<DataIndicador> call, Response<DataIndicador> response) {
                if (response!=null && response.isSuccessful() && response.body()!=null){
                    imacecList.postValue(response.body().getSerie());
                    saveData("imacec",response.body().getSerie());
                }
            }

            @Override
            public void onFailure(Call<DataIndicador> call, Throwable t) {
                Log.e("onFailure", t.getMessage());
                imacecList.postValue(null);
            }
        });
    }

    public void requestCobreData(){
        Call<DataIndicador> call = client.getDataLibraCobre();
        call.enqueue(new Callback<DataIndicador>() {
            @Override
            public void onResponse(Call<DataIndicador> call, Response<DataIndicador> response) {
                if (response!=null && response.isSuccessful() && response.body()!=null){
                    cobreList.postValue(response.body().getSerie());
                    saveData("cobre",response.body().getSerie());
                }
            }

            @Override
            public void onFailure(Call<DataIndicador> call, Throwable t) {
                Log.e("onFailure", t.getMessage());
                cobreList.postValue(null);
            }
        });
    }

    public void requestDesempleoData(){
        Call<DataIndicador> call = client.getDataDesempleo();
        call.enqueue(new Callback<DataIndicador>() {
            @Override
            public void onResponse(Call<DataIndicador> call, Response<DataIndicador> response) {
                if (response!=null && response.isSuccessful() && response.body()!=null){
                    desempleoList.postValue(response.body().getSerie());
                    saveData("desempleo",response.body().getSerie());
                }
            }

            @Override
            public void onFailure(Call<DataIndicador> call, Throwable t) {
                Log.e("onFailure", t.getMessage());
                desempleoList.postValue(null);
            }
        });
    }

    public void requestBitcoinData(){
        Call<DataIndicador> call = client.getDataBitcoin();
        call.enqueue(new Callback<DataIndicador>() {
            @Override
            public void onResponse(Call<DataIndicador> call, Response<DataIndicador> response) {
                if (response!=null && response.isSuccessful() && response.body()!=null){
                    bitcoinList.postValue(response.body().getSerie());
                    saveData("bitcoin",response.body().getSerie());
                }
            }

            @Override
            public void onFailure(Call<DataIndicador> call, Throwable t) {
                Log.e("onFailure", t.getMessage());
                bitcoinList.postValue(null);
            }
        });
    }

    public String decimalFormat(String valor){
        float numero = Float.parseFloat(valor);
        Locale chileLocale = new Locale("es","CL");
        NumberFormat nf = NumberFormat.getNumberInstance(chileLocale);
        String valorFormateado = nf.format(numero);
        return  valorFormateado;
    }

    public String dateUtcToString(String fecha) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        Instant instantFecha  = Instant.parse(fecha);
        return ZonedDateTime.ofInstant(instantFecha, ZoneId.of("America/Santiago")).format(dtf);
    }
}
