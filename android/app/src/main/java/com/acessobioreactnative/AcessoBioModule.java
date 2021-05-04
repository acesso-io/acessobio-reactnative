package com.acessobioreactnative;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.acesso.acessobio_android.AcessoBio;
import com.acesso.acessobio_android.iAcessoBioCamera;
import com.acesso.acessobio_android.services.dto.ErrorBio;
import com.acesso.acessobio_android.services.dto.ResultCamera;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.Map;
import java.util.HashMap;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import static androidx.core.app.ActivityCompat.requestPermissions;

public class AcessoBioModule extends ReactContextBaseJavaModule implements iAcessoBioCamera {

  private static ReactApplicationContext reactContext;

  protected static final int REQUEST_CAMERA_PERMISSION = 1;

  private static final String NAME = "";
  private static final String DOCUMENT = "";

  private static final String URL_INSTANCE = "";
  private static final String APIKEY_INSTANCE = "";
  private static final String TOKEN_INSTANCE = "";
   AcessoBio acessoBio;

  Callback successCallback;

  AcessoBioModule(ReactApplicationContext context) {
    super(context);
    reactContext = context;
  }

  @Override
  public String getName() {
    return "AcessoBioModule";
  }

  @ReactMethod
  public void show(String message, Callback errorCallback, Callback successCallback) {
    //System.out.println("mensagem aqui" + message);

    successCallback.invoke(message);
    
  }

  @RequiresApi(api = Build.VERSION_CODES.M)
  @ReactMethod
  private void callCamera () {
    if(hasPermission()) {
      MainActivity mainActivity = (MainActivity) getCurrentActivity();
      mainActivity.acessoBioModule = this;
      acessoBio = new AcessoBio(mainActivity, URL_INSTANCE, APIKEY_INSTANCE, TOKEN_INSTANCE);
      acessoBio.openCamera();
      //this.successCallback = successCallback;
    }
  }


  @RequiresApi(api = Build.VERSION_CODES.M)
  private boolean hasPermission(){
    if (ContextCompat.checkSelfPermission(getCurrentActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
    {
      requestPermissions(getCurrentActivity(),new String[] {
              Manifest.permission.CAMERA
      }, REQUEST_CAMERA_PERMISSION);

      return false;
    }
    return true;

  }

  @Override
  public void onSuccessCamera(ResultCamera result) {

    sendEvent(reactContext, "onSuccessCameraJS", result.getBase64());

  }

  @Override
  public void onErrorCamera(ErrorBio errorBio) {

  }

  private void sendEvent(ReactContext reactContext,
                         String eventName,
                         String processStatus) {

    WritableMap params = Arguments.createMap();
    params.putString("eventProperty", processStatus);
    reactContext
            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
            .emit(eventName, params);

  }
}
