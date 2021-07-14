package com.acessobioreactnative;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;


import com.acesso.acessobio_android.AcessoBio;
import com.acesso.acessobio_android.iAcessoBio;
import com.acesso.acessobio_android.iAcessoBioSelfie;
import com.acesso.acessobio_android.services.dto.ErrorBio;
import com.acesso.acessobio_android.services.dto.ResultCamera;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import static androidx.core.app.ActivityCompat.requestPermissions;

public class AcessoBioModule extends ReactContextBaseJavaModule implements iAcessoBio, iAcessoBioSelfie {

  private static ReactApplicationContext reactContext;

  protected static final int REQUEST_CAMERA_PERMISSION = 1;

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
      assert mainActivity != null;
      mainActivity.acessoBioModule = this;

      mainActivity.runOnUiThread(new Runnable() {
        @Override
        public void run() {

          acessoBio = new AcessoBio(mainActivity);
          acessoBio.openCamera(AcessoBioModule.this);

        }//public void run() {
      });


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

  private void sendEvent(ReactContext reactContext,
                         String eventName,
                         String processStatus) {

    WritableMap params = Arguments.createMap();
    params.putString("eventProperty", processStatus);
    reactContext
            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
            .emit(eventName, params);

  }

  @Override
  public void onErrorAcessoBio(ErrorBio errorBio) {

  }

  @Override
  public void userClosedCameraManually() {

  }

  @Override
  public void systemClosedCameraTimeoutSession() {

  }

  @Override
  public void systemChangedTypeCameraTimeoutFaceInference() {

  }

  @Override
  public void onSuccessSelfie(ResultCamera resultCamera) {
    sendEvent(reactContext, "onSuccessCameraJS", resultCamera.getBase64());
  }

  @Override
  public void onErrorSelfie(ErrorBio errorBio) {

  }
}
