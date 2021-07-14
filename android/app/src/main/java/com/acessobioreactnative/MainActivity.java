package com.acessobioreactnative;

import com.acesso.acessobio_android.iAcessoBio;
import com.acesso.acessobio_android.iAcessoBioSelfie;
import com.acesso.acessobio_android.services.dto.ErrorBio;
import com.acesso.acessobio_android.services.dto.ResultCamera;
import com.facebook.react.ReactActivity;

public class MainActivity extends ReactActivity implements iAcessoBio, iAcessoBioSelfie {

  public AcessoBioModule acessoBioModule;

  /**
   * Returns the name of the main component registered from JavaScript. This is used to schedule
   * rendering of the component.
   */
  @Override
  protected String getMainComponentName() {
    return "AcessoBioReactNative";
  }

  @Override
  public void onSuccessSelfie(ResultCamera resultCamera) {
    acessoBioModule.onSuccessSelfie(resultCamera);
  }

  @Override
  public void onErrorSelfie(ErrorBio errorBio) {

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

}
