/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  Platform,
  StyleSheet,
  Text,
  View,
  Button
} from 'react-native';
import RNImmediatePhoneCall from 'react-native-immediate-phone-call';

export default class App extends Component<Props> {
  render() {
    return (
      <View style={{flex: 1, alignItems: 'center', justifyContent: 'center'}}>
        <Button onPress={() => {RNImmediatePhoneCall.immediatePhoneCall('0542220858');}} title="Record"/>
      </View>
    );
  }
}
