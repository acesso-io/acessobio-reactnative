//
//  RNHello.h
//  pocreactnative
//
//  Created by Beatriz Monteiro Mendes de Paula on 28/04/21.
//

#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>

@interface AcessoBioModule : RCTEventEmitter <RCTBridgeModule> {
  bool hasListeners;
}

- (void)onSucessCameraFace: (NSString *)base64;

@end

