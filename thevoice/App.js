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
import snowboy from 'react-native-snowboy';


snowboy.initHotword()
	.then((res)=> {
		console.log(res)
	})
	.catch((err)=> {
		console.log(err)
	})

// No hotword detected
	snowboy.addEventListener("msg-vad-speech", (e) => {
		console.log('msg-vad-speech',e)
	})
	
	// No speech: silence
	snowboy.addEventListener("msg-vad-nospeech", (e) => {
		console.log('msg-vad-nospeech',e)
	})

// The hotword is decteced
	snowboy.addEventListener("msg-active", (e) => {
		console.log('msg-active',e)
	})

//RNImmediatePhoneCall.immediatePhoneCall('0542220858');

export default class App extends Component<Props> {
  render() {
    return (
      <View style={{flex: 1, alignItems: 'center', justifyContent: 'center'}}>
        <Button onPress={() => {snowboy.startRecording();}} title="Record"/>
      </View>
    );
  }
}
