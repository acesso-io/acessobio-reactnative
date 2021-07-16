/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import React, { Component } from 'react';
import {
  SafeAreaView,
  StyleSheet,
  ScrollView,
  View,
  Text,
  StatusBar,
  TouchableOpacity,
  NativeModules,NativeEventEmitter
} from 'react-native';

import {
  Header,
  LearnMoreLinks,
  Colors,
  DebugInstructions,
  ReloadInstructions,
} from 'react-native/Libraries/NewAppScreen';

export default class  App extends Component  {

  state = {
    textValue: 'O resultado do método nativo será exibido aqui'
  }

  render(){

    const eventEmitter = new NativeEventEmitter(NativeModules.AcessoBioModule);

    this.eventListener = eventEmitter.addListener('onSuccess', (resultEvent) => {
        updateText("Base64 gerado com sucesso no objeto resultEvent.objResult");
      });

      this.eventListener = eventEmitter.addListener('onError', (resultEvent) => {
        updateText("Erro: ".concat(resultEvent.objResult));
      });

      updateText = (message) => {
        this.setState({
          textValue: message
        })
     }

    return (

      <View style={styles.container}>
        <View style={styles.countContainer}>
          <Text>{this.state.textValue}</Text>
        </View>
        <TouchableOpacity
          style={styles.button}
          onPress={ ()=> {
            NativeModules.AcessoBioModule.callDefaultCamera();
          }}
        >
          <Text>Abrir câmera normal</Text>
        </TouchableOpacity>
        <TouchableOpacity
          style={styles.button}
          onPress={ ()=> {
            NativeModules.AcessoBioModule.callSmartCamera();
          }}
        >
          <Text>Abrir câmera inteligente</Text>
        </TouchableOpacity>
        <TouchableOpacity
          style={styles.button}
          onPress={ ()=> {
            NativeModules.AcessoBioModule.callDocumentCamera();
          }}
        >
          <Text>Abrir câmera documentos</Text>
        </TouchableOpacity>
      </View>
    );
  }
  
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    paddingHorizontal: 10
  },
  button: {
    alignItems: "center",
    backgroundColor: "#DDDDDD",
    padding: 10,
    marginTop:10
  },
  countContainer: {
    alignItems: "center",
    padding: 10
  }
});

