//
//  AcessoBioViewController.h
//  AcessoBioReactNative
//
//  Created by Matheus Domingos on 13/07/21.
//

#import <UIKit/UIKit.h>
#import <AcessoBio/AcessoBioManager.h>
@class AcessoBioModule;
NS_ASSUME_NONNULL_BEGIN

@interface AcessoBioViewController : UIViewController <AcessoBioDelegate> {
  AcessoBioManager *acessoBioManager;
}

@property (strong, nonatomic) AcessoBioModule *acessoBioModule;

@property (strong, nonatomic) UIViewController *viewOrigin;

@end

NS_ASSUME_NONNULL_END
