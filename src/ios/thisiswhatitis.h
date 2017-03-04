
#import <UIKit/UIKit.h>
#import "KeychainWrapper.h"
#import <Foundation/Foundation.h>
#import <Cordova/CDV.h>

@interface thisiswhatitis : CDVPlugin {
}

- (void) get:(CDVInvokedUrlCommand*)command;
- (void) set:(CDVInvokedUrlCommand*)command;
- (void) delete:(CDVInvokedUrlCommand*)command;

@end
