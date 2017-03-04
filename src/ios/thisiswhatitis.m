

#import <Cordova/CDV.h>
#import "thisiswhatitis.h"


@implementation thisiswhatitis

- (void) set:(CDVInvokedUrlCommand*)command
{
  CDVPluginResult* pluginResult = nil;
  NSString* key = [command.arguments objectAtIndex:0];
  NSString* value = [command.arguments objectAtIndex:1];
  @try {
    KeychainWrapper* keychain = [[KeychainWrapper alloc]initWithService:@"thisiswhatitis"];
    [keychain setString:value forKey:(__bridge id)(kSecValueData)];
   // [keychain writeToKeychain];

    [[NSUserDefaults standardUserDefaults]setBool:true forKey:key];
    [[NSUserDefaults standardUserDefaults]synchronize];

    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"Key saved to keychain successfully"];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
  }
  @catch(NSException* exception){
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString: @"Exception: saving key into keychain"];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
  }
}

- (void) get:(CDVInvokedUrlCommand*)command
{
  CDVPluginResult* pluginResult = nil;
  NSString* key = [command.arguments objectAtIndex:0];
  @try {

    BOOL hasKey = [[NSUserDefaults standardUserDefaults] boolForKey:key];
    if (hasKey) {
      KeychainWrapper* keychain = [[KeychainWrapper alloc]initWithService:@"thisiswhatitis"];
      NSString *value = [keychain stringForKey:@"v_Data"];

      pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:value];
      [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    } else {
      pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString: @"Exception: key not found in keychain"];
      [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    }

  }
  @catch(NSException* exception){
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString: @"Exception: fetching key from keychain"];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
  }
}

- (void) delete:(CDVInvokedUrlCommand*)command
{
	 	CDVPluginResult* pluginResult = nil;
    NSString* key = (NSString*)[command.arguments objectAtIndex:0];
    @try {
        [[NSUserDefaults standardUserDefaults] removeObjectForKey:key];
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"Key removed successfully"];
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    }
    @catch(NSException *exception) {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString: @"Exception: Could not delete key from keychain"];
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    }
}
@end
