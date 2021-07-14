//
//  RNHello.m
//  pocreactnative
//
//  Created by Beatriz Monteiro Mendes de Paula on 28/04/21.
//

#import "AcessoBioModule.h"
#import <React/RCTLog.h>
#import "AcessoBioViewController.h"


@implementation AcessoBioModule

// To export a module named CalendarManager
RCT_EXPORT_MODULE();

// This would name the module AwesomeCalendarManager instead
// RCT_EXPORT_MODULE(AwesomeCalendarManager);

RCT_EXPORT_METHOD(addEvent:(NSString *)name location:(NSString *)location)
{
  RCTLogInfo(@"Pretending to create an event %@ at %@", name, location);
}

//RCT_EXPORT_METHOD(findEvents:(RCTResponseSenderBlock)callback)
//{
//
//  callback(@[@"hello from native ios"]);
//}


RCT_EXTERN_METHOD(callCamera)

- (void)callCamera {
  
  dispatch_async(dispatch_get_main_queue(), ^{
    
    AcessoBioViewController *unicoView = [AcessoBioViewController new];
    
    UIViewController *view = [UIApplication sharedApplication].delegate.window.rootViewController;
    unicoView.viewOrigin = view;
    unicoView.acessoBioModule = self;
    
    [view presentViewController:unicoView animated:YES completion:nil];
   
  });
  
}

// Will be called when this module's first listener is added.
-(void)startObserving {
    hasListeners = YES;
    // Set up any upstream listeners or background tasks as necessary
}

// Will be called when this module's last listener is removed, or on dealloc.
-(void)stopObserving {
    hasListeners = NO;
    // Remove upstream listeners, stop unnecessary background tasks
}


- (void)onSucessCameraFace: (NSString *)base64 {
  NSLog(@"base64: %@", base64);

  if(hasListeners) {
    [self sendEventWithName:@"onSuccessCameraJS" body:base64];
  }
  
}


-(void)showAlert{
  
  UIAlertController *alert = [UIAlertController new];
  alert.title = @"teste";
  alert.message = @"descriptiuon";
  
  dispatch_async(dispatch_get_main_queue(), ^{
    UIViewController *view = [UIApplication sharedApplication].delegate.window.rootViewController;
    [view presentViewController:alert animated:YES completion:nil];
    
  });
  
}



@end

