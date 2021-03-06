//
//  AcessoBioViewController.m
//  AcessoBioReactNative
//
//  Created by Matheus Domingos on 13/07/21.
//

#import "AcessoBioViewController.h"
#import "AcessoBioModule.h"

@interface AcessoBioViewController ()

@end

@implementation AcessoBioViewController


- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
  acessoBioManager = [[AcessoBioManager alloc]initWithViewController:self url:nil apikey:nil token:nil];
  
  if([self.mode isEqualToNumber:[NSNumber numberWithInt:0]]) {
    [self performSelector:@selector(callDefaultCamera) withObject:nil afterDelay:0.5];
  }else if([self.mode isEqualToNumber:[NSNumber numberWithInt:1]]) {
    [self performSelector:@selector(callSmartCamera) withObject:nil afterDelay:0.5];
  }else {
    [self performSelector:@selector(callDocumentCamera) withObject:nil afterDelay:0.5];
  }
}

- (void)callDefaultCamera {
  [acessoBioManager disableSmartCamera];
  [acessoBioManager openCameraFace];
}

- (void)callSmartCamera {
  [acessoBioManager enableSmartCamera];
  [acessoBioManager openCameraFace];
}

- (void)callDocumentCamera {
  [acessoBioManager openCameraDocuments:DocumentCNH];
}

- (void)onErrorAcessoBioManager:(NSString *)error {
  [self.acessoBioModule onErrorAcessoBioManager:error];
  [self sair];
}

- (void)onSuccesCameraFace:(CameraFaceResult *)result {
  [self.acessoBioModule onSucessCameraFace:result.base64];
  [self sair];
}

- (void)onErrorCameraFace:(NSString *)error {
  NSLog(@"%@", error);
  [self.acessoBioModule onErrorCameraFace:error];
  [self sair];
}

- (void)systemClosedCameraTimeoutFaceInference {
  [self.acessoBioModule systemClosedCameraTimeoutSession];
  [self sair];
}

- (void)systemClosedCameraTimeoutSession {
  [self.acessoBioModule systemClosedCameraTimeoutSession];
  [self sair];
}

- (void)userClosedCameraManually {
  [self.acessoBioModule userClosedCameraManually];
  [self sair];
}

- (void)sair{
  dispatch_after(dispatch_time(DISPATCH_TIME_NOW, 0.5 * NSEC_PER_SEC), dispatch_get_main_queue(), ^{
    [self dismissViewControllerAnimated:YES completion:nil];
  });
}

@end
