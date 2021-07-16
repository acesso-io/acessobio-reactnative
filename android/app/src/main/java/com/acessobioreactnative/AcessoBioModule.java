package com.acessobioreactnative;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import com.acesso.acessobio_android.AcessoBio;
import com.acesso.acessobio_android.iAcessoBio;
import com.acesso.acessobio_android.iAcessoBioDocument;
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

public class AcessoBioModule extends ReactContextBaseJavaModule implements iAcessoBio, iAcessoBioSelfie, iAcessoBioDocument {

  private static ReactApplicationContext reactContext;

  protected static final int REQUEST_CAMERA_PERMISSION = 1;

  public enum CameraMode {
    SMART,
    DEFAULT,
    DOCUMENT
  }

  AcessoBio acessoBio;

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
    successCallback.invoke(message);
  }

  @RequiresApi(api = Build.VERSION_CODES.M)
  @ReactMethod
  private void callSmartCamera () {
      this.openCamera(CameraMode.SMART);
  }

  @RequiresApi(api = Build.VERSION_CODES.M)
  @ReactMethod
  private void callDefaultCamera () {
    this.openCamera(CameraMode.DEFAULT);
  }

  @RequiresApi(api = Build.VERSION_CODES.M)
  @ReactMethod
  private void callDocumentCamera () {
    this.openCamera(CameraMode.DOCUMENT);
  }

  @RequiresApi(api = Build.VERSION_CODES.M)
  private void openCamera(CameraMode mode) {
    if(hasPermission()) {

      getCurrentActivity().runOnUiThread(new Runnable() {
        @Override
        public void run() {
          acessoBio = new AcessoBio(getCurrentActivity(), AcessoBioModule.this);
          if(mode == CameraMode.SMART) {
            acessoBio.smartFrame(true);
            acessoBio.openCamera(AcessoBioModule.this);
          }else if(mode == CameraMode.DEFAULT) {
            acessoBio.smartFrame(false);
            acessoBio.openCamera(AcessoBioModule.this);
          }else {
            acessoBio.openCameraDocument(AcessoBio.CNH, AcessoBioModule.this);
          }
        }
      });

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
    params.putString("objResult", processStatus);
    reactContext
            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
            .emit(eventName, params);

  }

  @Override
  public void onErrorAcessoBio(ErrorBio errorBio) {
    sendEvent(reactContext, "onError", errorBio.getDescription());
  }

  @Override
  public void userClosedCameraManually() {
    sendEvent(reactContext, "onError", "Usuário fechou a câmera manualmente");

  }

  @Override
  public void systemClosedCameraTimeoutSession() {
    sendEvent(reactContext, "onError", "Timeout de sessão excedido");

  }

  @Override
  public void systemChangedTypeCameraTimeoutFaceInference() {
    sendEvent(reactContext, "onError", "Timeout de inferencia inteligente de face excedido.");
  }

  @Override
  public void onSuccessSelfie(ResultCamera resultCamera) {
    sendEvent(reactContext, "onSuccess", resultCamera.getBase64());
  }

  @Override
  public void onErrorSelfie(ErrorBio errorBio) {
    sendEvent(reactContext, "onError", errorBio.getDescription());
  }
  @Override
  public void onSuccesstDocument(String s) {
    sendEvent(reactContext, "onSuccess", s);
  }

  @Override
  public void onErrorDocument(String s) {
    sendEvent(reactContext, "onError", s);
  }


}
